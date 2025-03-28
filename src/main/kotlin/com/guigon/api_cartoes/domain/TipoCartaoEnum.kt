package com.guigon.api_cartoes.domain

import java.math.BigDecimal

enum class TipoCartaoEnum(private val valorAnuidadeMensal: BigDecimal, private val valorLimiteDisponivel: BigDecimal) {
    CARTAO_SEM_ANUIDADE(BigDecimal("0.00"), BigDecimal("1000.00")),
    CARTAO_COM_CASHBACK(BigDecimal("30.00"), BigDecimal("5000.00")),
    CARTAO_DE_PARCEIROS(BigDecimal("20.00"), BigDecimal("3000.00"));

    fun criarCartao(): Cartao {
        return Cartao(this, valorAnuidadeMensal, valorLimiteDisponivel, Status.APROVADO)
    }
}