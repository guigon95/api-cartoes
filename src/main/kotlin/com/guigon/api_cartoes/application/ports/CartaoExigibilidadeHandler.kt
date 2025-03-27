package com.guigon.api_cartoes.application.ports

import com.guigon.api_cartoes.domain.Cliente

interface CartaoExigibilidadeHandler {
    fun handle(cliente: Cliente): OfertaCartaoStrategy?
}