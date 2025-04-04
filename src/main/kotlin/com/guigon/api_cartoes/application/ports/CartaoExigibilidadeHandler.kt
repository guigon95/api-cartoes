package com.guigon.api_cartoes.application.ports

import com.guigon.api_cartoes.domain.Solicitacao

fun interface CartaoExigibilidadeHandler {
    suspend fun handle(solicitacao: Solicitacao): OfertaCartaoStrategy?
}