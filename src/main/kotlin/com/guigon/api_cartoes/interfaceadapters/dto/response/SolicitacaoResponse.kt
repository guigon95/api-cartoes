package com.guigon.api_cartoes.interfaceadapters.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.interfaceadapters.dto.CartaoDto
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO
import java.time.LocalDateTime
import java.util.UUID

class SolicitacaoResponse(
    @JsonProperty("numero_solicitacao")
    val numeroSolicitacao: UUID,
    @JsonProperty("data_solicitacao")
    val dataSolicitacao: LocalDateTime,
    val cliente: ClienteDTO,
    @JsonProperty("cartoes_ofertados")
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