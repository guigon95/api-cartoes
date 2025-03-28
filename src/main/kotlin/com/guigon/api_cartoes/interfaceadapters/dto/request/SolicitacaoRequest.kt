package com.guigon.api_cartoes.interfaceadapters.dto.request

import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.infrastructure.config.IdadeProperties
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO
import jakarta.validation.Valid

data class SolicitacaoRequest(
    @Valid
    val cliente: ClienteDTO,
) {
    fun toSolicitacao(idadeProperties: IdadeProperties): Solicitacao {
            return Solicitacao(
                cliente = ClienteDTO.toCliente(cliente),
                idadeProperties = idadeProperties
            )
    }
}
