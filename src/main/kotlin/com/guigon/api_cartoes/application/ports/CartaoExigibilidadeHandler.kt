package com.guigon.api_cartoes.application.ports

import com.guigon.api_cartoes.domain.Solicitacao

interface CartaoExigibilidadeHandler {
    fun handle(solicitacao: Solicitacao): OfertaCartaoStrategy?
}