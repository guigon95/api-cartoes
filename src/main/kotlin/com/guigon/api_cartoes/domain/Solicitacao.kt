package com.guigon.api_cartoes.domain

import com.guigon.api_cartoes.infrastructure.config.IdadeProperties
import java.time.LocalDateTime
import java.util.UUID

class Solicitacao(
    val numeroSolicitacao: UUID,
    val dataSolicitacao: LocalDateTime,
    val cliente: Cliente,
    var cartoesOfertados: List<Cartao>?,
    val idadeProperties: IdadeProperties
) {
    constructor(cliente: Cliente, idadeProperties: IdadeProperties) : this(
        UUID.randomUUID(),
        LocalDateTime.now(),
        cliente,
        null,
        idadeProperties
    )

    fun ehResidenteSP(): Boolean {
        return cliente.uf == "SP"
    }

    fun ehJovem(): Boolean {
        return cliente.idade in idadeProperties.jovem.minima..idadeProperties.jovem.maxima
    }

    fun ehMenorDeIdade(): Boolean {
        return cliente.idade < idadeProperties.jovem.minima
    }

    fun ehJovemAdulto(): Boolean {
        return cliente.idade in idadeProperties.jovemAdulto.minima..idadeProperties.jovemAdulto.maxima
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