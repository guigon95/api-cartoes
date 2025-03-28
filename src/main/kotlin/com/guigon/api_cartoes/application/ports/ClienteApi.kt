package com.guigon.api_cartoes.application.ports

import com.guigon.api_cartoes.domain.ClienteApiResponse

fun interface ClienteApi {
    suspend fun getDadosExternos(): ClienteApiResponse
}