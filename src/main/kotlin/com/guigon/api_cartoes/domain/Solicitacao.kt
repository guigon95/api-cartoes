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
}