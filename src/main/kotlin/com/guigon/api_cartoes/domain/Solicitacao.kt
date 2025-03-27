package com.guigon.api_cartoes.domain

import java.time.LocalDateTime
import java.util.UUID

class Solicitacao(
    val numeroSolicitacao: UUID,
    val dataSolicitacao: LocalDateTime,
    val cliente: Cliente,
    var cartoesOfertados: List<Cartao>?
) {
    constructor(cliente: Cliente) : this(
        UUID.randomUUID(),
        LocalDateTime.now(),
        cliente,
        null
    )

    fun ehResidenteSP(): Boolean {
        return cliente.uf == "SP"
    }

    fun ehClientePrimeiraFaixaSalarial(): Boolean {
        return cliente.rendaMensal >= 1000.toBigDecimal() && cliente.rendaMensal < 3000.toBigDecimal()
    }

    fun ehClienteSegundaFaixaSalarial(): Boolean {
        return cliente.rendaMensal >= 3000.toBigDecimal() && cliente.rendaMensal < 5000.toBigDecimal()
    }

    fun ehClienteTerceiraFaixaSalarial(): Boolean {
        return cliente.rendaMensal >= 5000.toBigDecimal()
    }
}