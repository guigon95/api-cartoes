package com.guigon.api_cartoes.domain

import java.math.BigDecimal

data class Cartao(
    val tipoCartao: TipoCartaoEnum,
    val valorAnuidadeMensal: BigDecimal,
    val valorLimiteDisponivel: BigDecimal,
    val status: Status
)