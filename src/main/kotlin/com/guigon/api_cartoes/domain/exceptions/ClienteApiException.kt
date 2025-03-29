package com.guigon.api_cartoes.domain.exceptions

data class ClienteApiException(val mensagem: String) : RuntimeException(mensagem)
