package com.guigon.api_cartoes.application.ports

import com.guigon.api_cartoes.domain.Cartao
import com.guigon.api_cartoes.domain.Cliente

interface OfertaCartaoStrategy {
    fun obter(cliente: Cliente): List<Cartao>
}