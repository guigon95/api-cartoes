package com.guigon.api_cartoes.interfaceadapters.controller

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class ErrorResponse(
    @Schema(description = "Código do erro", example = "500")
    val codigo: String,
    @Schema(description = "Mensagem de erro", example = "Um erro inesperado ocorreu")
    val mensagem: String,
    @Schema(description = "Detalhe do erro", required = true)
    @JsonProperty("detalhe_erro")
    val detalheErro: DetalheErro
)

data class DetalheErro(
    @Schema(description = "Aplicação que gerou o erro", example = "api-cartoes")
    val app: String,
    @Schema(description = "Tipo do erro", example = "SERVICO_INDISPONIVEL")
    @JsonProperty("tipo_erro")
    val tipoErro: String,
    @Schema(description = "Mensagem interna do erro", example = "Erro ao acessar o serviço de cartões")
    @JsonProperty("mensagem_interna")
    val mensagemInterna: String
)
