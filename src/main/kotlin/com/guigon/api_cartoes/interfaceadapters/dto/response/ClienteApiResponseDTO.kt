package com.guigon.api_cartoes.interfaceadapters.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class ClienteApiResponseDTO @JsonCreator constructor (
    @JsonProperty("id")
    val id: UUID
)