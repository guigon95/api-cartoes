package com.guigon.api_cartoes.interfaceadapters.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.guigon.api_cartoes.domain.Cartao
import java.math.BigDecimal

data class CartaoDto(
    @JsonProperty("tipo_cartao")
    val tipoCartao: String,
    @JsonProperty("valor_anuidade_mensal")
    val valorAnuidadeMensal: BigDecimal,
    @JsonProperty("valor_limite_disponivel")
    val valorLimiteDisponivel: BigDecimal,
    @JsonProperty("status")
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
