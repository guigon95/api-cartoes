package com.guigon.api_cartoes.application.usecases

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaJovemAdultoSP
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaJovemHandler
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaResidenteSPHandler
import com.guigon.api_cartoes.domain.Cliente
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_DE_PARCEIROS
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_SEM_ANUIDADE
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_COM_CASHBACK
import com.guigon.api_cartoes.domain.exceptions.CriteriosJovemException
import com.guigon.api_cartoes.infrastructure.config.IdadeProperties
import com.guigon.api_cartoes.infrastructure.config.JovemAdultoProperties
import com.guigon.api_cartoes.infrastructure.config.JovemProperties
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class SolicitacaoCartaoParaJovemAdultoSPUseCaseImplTest {

    private lateinit var handler: List<CartaoExigibilidadeHandler>
    private lateinit var useCase: SolicitacaoCartaoUseCaseImpl

    @BeforeEach
    fun setUp() {
        handler = listOf(
            CartaoParaJovemHandler(),
            CartaoParaJovemAdultoSP(),
            CartaoParaResidenteSPHandler()
        )

        useCase = SolicitacaoCartaoUseCaseImpl(handler)
    }

    @Test
    fun `'solicitar' deve retornar CARTAO_SEM_ANIDADE quando criterios de salario e idade forem aceitos`() {
        val solicitacao = getSolicitacao(BigDecimal(1000), 20)
        val result = useCase.solicitar(solicitacao)

        assertThat(result.cartoesOfertados?.size).isEqualTo(1)
        assertThat(result.cartoesOfertados).contains(CARTAO_SEM_ANUIDADE.criarCartao())
    }

    @Test
    fun `'solicitar' deve lancar excessao quando renda mensal de jovem for menor que os criterios de salario`() {
        val solicitacao = getSolicitacao(BigDecimal(999), 20)

        assertThatThrownBy {
            useCase.solicitar(solicitacao)
        }.isInstanceOf(CriteriosJovemException::class.java)
    }

    @Test
    fun `'solicitar' deve lancar excessao quando renda mensal de jovem for maior que os criterios de salario`() {
        val solicitacao = getSolicitacao(BigDecimal(3000), 20)

        assertThatThrownBy {
            useCase.solicitar(solicitacao)
        }.isInstanceOf(CriteriosJovemException::class.java)

    }

    @Test
    fun `'solicitar' deve retornar CARTAO_SEM_ANUIDADE e CARTAO_DE_PARCEIROS quando cliente residente em SP,COM 25 ANOS e segunda faixa salarial`() {
        val solicitacao = getSolicitacao(BigDecimal(4500), 25)

        val result = useCase.solicitar(solicitacao)

        assertThat(result.cartoesOfertados?.size).isEqualTo(2)
        assertThat(result.cartoesOfertados).contains(CARTAO_SEM_ANUIDADE.criarCartao(), CARTAO_DE_PARCEIROS.criarCartao())
    }

    @Test
    fun `'solicitar' deve retornar CARTA_SEM_ANUIDADE quando cliente residente SP, maior de 25 anos e for primeira faixa salarial`() {
        val solicitacao = getSolicitacao(BigDecimal(2000), 25)
        val result = useCase.solicitar(solicitacao)

        assertThat(result.cartoesOfertados?.size).isEqualTo(1)
        assertThat(result.cartoesOfertados).contains(CARTAO_SEM_ANUIDADE.criarCartao())
    }

    @Test
    fun `'solicitar' deve retornar CARTA_SEM_ANUIDADE e CARTAO_DE_PARCEIROS quando cliente residente SP, com idade 30 anos e for terceira faixa salarial`() {
        val solicitacao = getSolicitacao(BigDecimal(6000), 30)

        val result = useCase.solicitar(solicitacao)

        assertThat(result.cartoesOfertados?.size).isEqualTo(2)
        assertThat(result.cartoesOfertados).contains(CARTAO_SEM_ANUIDADE.criarCartao(), CARTAO_COM_CASHBACK.criarCartao())
    }

    @Test
    fun `'solicitar' deve retornar CARTA_SEM_ANUIDADE, CARTAO_DE_PARCEIROS e CARTAO_COM_CASHBACK quando cliente residente SP, com idade 26 anos e for terceira faixa salarial`() {
        val solicitacao = getSolicitacao(BigDecimal(7000), 26)

        val result = useCase.solicitar(solicitacao)

        assertThat(result.cartoesOfertados?.size).isEqualTo(3)
        assertThat(result.cartoesOfertados).contains(CARTAO_SEM_ANUIDADE.criarCartao(), CARTAO_DE_PARCEIROS.criarCartao(), CARTAO_COM_CASHBACK.criarCartao())
    }

    @Test
    fun `'solicitar' deve retornar CARTAO_SEM_ANUIDADE e CARTAO_DE_PARCEIROS quando cliente residente SP, com idade maior que 30 anos e for segunda faixa salarial`() {
        val solicitacao = getSolicitacao(BigDecimal(4500), 29)

        val result = useCase.solicitar(solicitacao)

        assertThat(result.cartoesOfertados?.size).isEqualTo(2)
        assertThat(result.cartoesOfertados).contains(CARTAO_SEM_ANUIDADE.criarCartao(), CARTAO_DE_PARCEIROS.criarCartao())
    }

    @Test
    fun `'solicitar' deve retornar CARTAO_SEM_ANUIDADE quando cliente residente SP, com idade 29 anos e for primeira faixa salarial`() {
        val solicitacao = getSolicitacao(BigDecimal(2000), 29)

        val result = useCase.solicitar(solicitacao)

        assertThat(result.cartoesOfertados?.size).isEqualTo(1)
        assertThat(result.cartoesOfertados).contains(CARTAO_SEM_ANUIDADE.criarCartao())
    }



    private fun getSolicitacao(rendaMensal: BigDecimal, idade: Int) = Solicitacao(
        cliente = Cliente(
            nome = "Guilherme",
            cpf = "12345678900",
            dataNascimento = LocalDate.of(2000, 1, 1),
            idade = idade,
            uf = "SP",
            rendaMensal = rendaMensal,
            email = "email@email.com",
            telefoneWhatsapp = "11999999999"
        ),
        idadeProperties = IdadeProperties(
            jovem = JovemProperties(18, 24),
            jovemAdulto = JovemAdultoProperties(25, 29)
        )
    )


}