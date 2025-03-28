package com.guigon.api_cartoes.domain

import java.math.BigDecimal

enum class TipoCartaoEnum(val limite: BigDecimal, val rendaMensal: BigDecimal) {
    CARTAO_SEM_ANUIDADE(BigDecimal(1000.00), BigDecimal(3500.00)),
    CARTAO_COM_CASHBACK(BigDecimal(3000.00), BigDecimal(5500.00)),
    CARTAO_DE_PARCEIROS(BigDecimal(5000.00), BigDecimal(7500.00));

    fun criarCartao(): Cartao {
        return Cartao(this, limite, rendaMensal, Status.APROVADO)
    }
}