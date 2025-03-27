package com.guigon.api_cartoes.interfaceadapters.dto.request

import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO

data class SolicitacaoRequest(
    val cliente: ClienteDTO
) {
    fun toSolicitacao(): Solicitacao {
            return Solicitacao(
                cliente = ClienteDTO.toCliente(cliente)
            )
    }
}
