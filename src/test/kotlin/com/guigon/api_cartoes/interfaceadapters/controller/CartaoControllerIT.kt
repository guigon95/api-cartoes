package com.guigon.api_cartoes.interfaceadapters.controller

import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_SEM_ANUIDADE
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_DE_PARCEIROS
import com.guigon.api_cartoes.interfaceadapters.dto.CartaoDto
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO
import com.guigon.api_cartoes.interfaceadapters.dto.response.SolicitacaoResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import java.math.BigDecimal
import java.time.LocalDate


@SpringBootTest
@AutoConfigureWebTestClient
@Testcontainers
class CartaoControllerIT {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val objectMapper = ObjectMapper()

    @Container
    private val apiClientesContainer = GenericContainer("guigon95/api-clientes:latest")
        .withExposedPorts(8082)

    private val solicitacao = """{ 
            "cliente" : {
            "nome": "Fulano",
            "cpf": "123.456.789-00",
            "idade": 24,
            "data_nascimento": "2000-01-01",
            "uf": "SP",
            "renda_mensal": 1000.00,
            "email": "fulano@email.com",
            "telefone_whatsapp": "11999999999"
                    }
            }""".trimMargin()

    private val cliente = ClienteDTO(
        nome = "Fulano",
        cpf = "123.456.789-00",
        idade = 24,
        dataNascimento = LocalDate.of(2000, 1, 1),
        uf = "SP",
        rendaMensal = BigDecimal("1000.00"),
        email = "fulano@email.com",
        telefoneWhatsapp = "11999999999"
    )

    @AfterEach
    fun tearDown() {
        apiClientesContainer.stop()
    }

    @Test
    fun `'solicitarCartao' deve retornar status 200 quando a solicitação for válida`() {
        webTestClient.post()
            .uri("/cartoes")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(solicitacao)
            .exchange()
            .expectStatus().isOk
            .expectBody(SolicitacaoResponse::class.java)
            .consumeWith { response ->
                val solicitacaoResponse = response.responseBody
                assertThat(solicitacaoResponse).isNotNull
                assertThat(solicitacaoResponse?.numeroSolicitacao).isNotNull()
                assertThat(solicitacaoResponse?.dataSolicitacao).isNotNull()
                assertThat(solicitacaoResponse?.cliente).isNotNull()
                assertThat(solicitacaoResponse?.cliente).isEqualTo(cliente)
                assertThat(solicitacaoResponse?.cartoesOfertados).isNotNull()
                assertThat(solicitacaoResponse?.cartoesOfertados?.size).isEqualTo(1)
                assertThat(solicitacaoResponse?.cartoesOfertados?.get(0)).isEqualTo(CartaoDto.fromCartao(CARTAO_SEM_ANUIDADE.criarCartao()))
            }
    }

    @Test
    fun `'solicitarCartao' deve retornar status 200 quando e dois cartoes para idade 25 e renda 3500`() {
        webTestClient.post()
            .uri("/cartoes")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("""{ 
                    "cliente" : {
                    "nome": "Fulano",
                    "cpf": "123.456.789-00",
                    "idade": 25,
                    "data_nascimento": "1999-01-01",
                    "uf": "SP",
                    "renda_mensal": 3500.00,
                    "email": "fulano@email.com",
                    "telefone_whatsapp": "11999999999"
                   }
            }""".trimMargin())
            .exchange()
            .expectStatus().isOk
            .expectBody(SolicitacaoResponse::class.java)
            .consumeWith { response ->
                val solicitacaoResponse = response.responseBody
                assertThat(solicitacaoResponse).isNotNull
                assertThat(solicitacaoResponse?.numeroSolicitacao).isNotNull()
                assertThat(solicitacaoResponse?.dataSolicitacao).isNotNull()
                assertThat(solicitacaoResponse?.cliente).isNotNull()
                assertThat(solicitacaoResponse?.cliente).isEqualTo(ClienteDTO(
                    nome = "Fulano",
                    cpf = "123.456.789-00",
                    idade = 25,
                    dataNascimento = LocalDate.of(1999, 1, 1),
                    uf = "SP",
                    rendaMensal = BigDecimal("3500.00"),
                    email = "fulano@email.com",
                    telefoneWhatsapp = "11999999999"
                ))
                assertThat(solicitacaoResponse?.cartoesOfertados).isNotNull()
                assertThat(solicitacaoResponse?.cartoesOfertados?.size).isEqualTo(2)
                assertThat(solicitacaoResponse?.cartoesOfertados).containsExactlyInAnyOrder(
                    CartaoDto.fromCartao(CARTAO_SEM_ANUIDADE.criarCartao()),
                    CartaoDto.fromCartao(CARTAO_DE_PARCEIROS.criarCartao())
                )
            }
    }

    @Test
    fun `'solicitarCartao' deve retornar 422interface quando cliente for menor de idade`() {
        webTestClient.post()
            .uri("/cartoes")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("""{ 
                    "cliente" : {
                    "nome": "Fulano",
                    "cpf": "123.456.789-00",
                    "idade": 17,
                    "data_nascimento": "1999-01-01",
                    "uf": "SP",
                    "renda_mensal": 1000.00,
                    "email": "fulano@email.com",
                    "telefone_whatsapp": "11999999999"
                   }
            }""".trimMargin())
            .exchange()
            .expectStatus().isEqualTo(422)
    }

}