package com.guigon.api_cartoes.interfaceadapters.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.guigon.api_cartoes.domain.Cartao
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class CartaoDto(
    @Schema(description = "Tipo do cartão", example = "CARTAO_SEM_ANUIDADE", required = true)
    @JsonProperty("tipo_cartao")
    val tipoCartao: String,
    @Schema(description = "Valor da anuidade mensal", example = "0.00", required = true)
    @JsonProperty("valor_anuidade_mensal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#0.00")
    val valorAnuidadeMensal: BigDecimal,
    @Schema(description = "Valor do limite disponível", example = "1000.00", required = true)
    @JsonProperty("valor_limite_disponivel")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#0.00")
    val valorLimiteDisponivel: BigDecimal,
    @Schema(description = "Status do cartão", example = "APROVADO", required = true)
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
