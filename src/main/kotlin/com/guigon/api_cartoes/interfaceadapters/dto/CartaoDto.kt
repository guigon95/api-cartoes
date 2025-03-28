package com.guigon.api_cartoes.interfaceadapters.dto

import com.guigon.api_cartoes.domain.Cartao
import java.math.BigDecimal

data class CartaoDto(
    val tipoCartao: String,
    val valorAnuidadeMensal: BigDecimal,
    val valorLimiteDisponivel: BigDecimal,
    val status: String
) {
    companion object {
        fun fromCartao(cartao: Cartao): CartaoDto {
            return CartaoDto(
                tipoCartao = cartao.tipoCartao.toString(),
                valorAnuidadeMensal = cartao.valorAnuidadeMensal,
                valorLimiteDisponivel = cartao.valorLimiteDisponivel,
                status = cartao.status.toString()
            )
        }
    }
}
