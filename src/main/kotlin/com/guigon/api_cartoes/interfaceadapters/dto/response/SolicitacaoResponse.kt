package com.guigon.api_cartoes.interfaceadapters.dto.response

import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.interfaceadapters.dto.CartaoDto
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO
import java.time.LocalDateTime
import java.util.UUID

class SolicitacaoResponse(
    val numeroSolicitacao: UUID,
    val dataSolicitacao: LocalDateTime,
    val cliente: ClienteDTO,
    val cartoesOfertados: List<CartaoDto>?
) {
    fun fromSolicitacao(solicitacao: Solicitacao) {
        SolicitacaoResponse(
            numeroSolicitacao = solicitacao.numeroSolicitacao,
            dataSolicitacao = solicitacao.dataSolicitacao,
            cliente = ClienteDTO.fromCliente(solicitacao.cliente),
            cartoesOfertados = solicitacao.cartoesOfertados?.map { CartaoDto.fromCartao(it) }
        )
    }

    companion object {
        fun fromSolicitacao(solicitacao: Solicitacao): SolicitacaoResponse {
            return SolicitacaoResponse(
                numeroSolicitacao = solicitacao.numeroSolicitacao,
                dataSolicitacao = solicitacao.dataSolicitacao,
                cliente = ClienteDTO.fromCliente(solicitacao.cliente),
                cartoesOfertados = solicitacao.cartoesOfertados?.map { CartaoDto.fromCartao(it) }
            )
        }
    }
}