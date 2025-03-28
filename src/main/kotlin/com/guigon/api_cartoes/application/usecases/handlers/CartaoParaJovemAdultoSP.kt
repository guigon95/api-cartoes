package com.guigon.api_cartoes.application.usecases.handlers

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.OfertaCartaoStrategy
import com.guigon.api_cartoes.application.usecases.oferta.OfertaCartaoJovemAdultoResidenteSP
import com.guigon.api_cartoes.domain.Solicitacao

class CartaoParaJovemAdultoSP : CartaoExigibilidadeHandler {
    override fun handle(solicitacao: Solicitacao): OfertaCartaoStrategy? {
        if (solicitacao.ehJovemAdulto() && solicitacao.ehResidenteSP()) {
            return OfertaCartaoJovemAdultoResidenteSP()
        }
        return null
    }
}