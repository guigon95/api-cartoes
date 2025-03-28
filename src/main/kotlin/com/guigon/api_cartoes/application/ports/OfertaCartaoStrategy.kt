package com.guigon.api_cartoes.application.ports

import com.guigon.api_cartoes.domain.Cartao
import com.guigon.api_cartoes.domain.Solicitacao

interface OfertaCartaoStrategy {
    fun obter(solicitacao: Solicitacao): List<Cartao>
}