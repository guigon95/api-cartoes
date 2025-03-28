package com.guigon.api_cartoes.application.usecases.oferta

import com.guigon.api_cartoes.application.ports.OfertaCartaoStrategy
import com.guigon.api_cartoes.domain.Cartao
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_SEM_ANUIDADE
import com.guigon.api_cartoes.domain.exceptions.CriteriosJovemException

class OfertaCartaoJovem : OfertaCartaoStrategy {
    override suspend fun obter(solicitacao: Solicitacao): List<Cartao> {
        if(solicitacao.ehClientePrimeiraFaixaSalarial()) {
            return listOf(CARTAO_SEM_ANUIDADE.criarCartao())
        }

        throw CriteriosJovemException()
    }
}