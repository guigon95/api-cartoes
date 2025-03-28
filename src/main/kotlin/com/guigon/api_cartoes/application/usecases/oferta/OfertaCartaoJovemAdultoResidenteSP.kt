package com.guigon.api_cartoes.application.usecases.oferta

import com.guigon.api_cartoes.application.ports.OfertaCartaoStrategy
import com.guigon.api_cartoes.domain.Cartao
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_SEM_ANUIDADE
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_DE_PARCEIROS
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_COM_CASHBACK
import com.guigon.api_cartoes.domain.exceptions.CriteriosResidenteSPException


class OfertaCartaoJovemAdultoResidenteSP : OfertaCartaoStrategy {
    override fun obter(solicitacao: Solicitacao): List<Cartao> {
        return when {
            solicitacao.ehClientePrimeiraFaixaSalarial() -> listOf(CARTAO_SEM_ANUIDADE.criarCartao())
            solicitacao.ehClienteSegundaFaixaSalarial() -> listOf(CARTAO_SEM_ANUIDADE.criarCartao(), CARTAO_DE_PARCEIROS.criarCartao())
            solicitacao.ehClienteTerceiraFaixaSalarial() -> listOf(CARTAO_SEM_ANUIDADE.criarCartao(), CARTAO_DE_PARCEIROS.criarCartao(), CARTAO_COM_CASHBACK.criarCartao())
            else -> throw CriteriosResidenteSPException()
        }
    }
}