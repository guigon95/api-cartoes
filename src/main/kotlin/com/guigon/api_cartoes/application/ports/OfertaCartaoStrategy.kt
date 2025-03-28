package com.guigon.api_cartoes.application.ports

import com.guigon.api_cartoes.domain.Cartao
import com.guigon.api_cartoes.domain.Solicitacao

fun interface OfertaCartaoStrategy {
    suspend fun obter(solicitacao: Solicitacao): List<Cartao>
}