package com.guigon.api_cartoes.application.usecases.handlers

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.OfertaCartaoStrategy
import com.guigon.api_cartoes.application.usecases.oferta.OfertaCartaoResidenteSP
import com.guigon.api_cartoes.domain.Solicitacao

class CartaoParaResidenteSPHandler : CartaoExigibilidadeHandler {
    override suspend fun handle(solicitacao: Solicitacao): OfertaCartaoStrategy? {
        if (solicitacao.ehResidenteSP()) {
            return OfertaCartaoResidenteSP()
        }
        return null
    }
}