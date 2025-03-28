package com.guigon.api_cartoes.interfaceadapters.controller

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    val codigo: String,
    val mensagem: String,
    @JsonProperty("detalhe_erro")
    val detalheErro: DetalheErro
)

data class DetalheErro(
    val app: String,
    @JsonProperty("tipo_erro")
    val tipoErro: String,
    @JsonProperty("mensagem_interna")
    val mensagemInterna: String
)
