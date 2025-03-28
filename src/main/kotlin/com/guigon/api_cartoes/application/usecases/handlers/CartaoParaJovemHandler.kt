package com.guigon.api_cartoes.application.usecases.handlers

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.OfertaCartaoStrategy
import com.guigon.api_cartoes.application.usecases.oferta.OfertaCartaoJovem
import com.guigon.api_cartoes.domain.Solicitacao

class CartaoParaJovemHandler : CartaoExigibilidadeHandler{
    override fun handle(solicitacao: Solicitacao): OfertaCartaoStrategy? {
        if (solicitacao.ehJovem()) {
            return OfertaCartaoJovem()
        }
        return  null
    }
}