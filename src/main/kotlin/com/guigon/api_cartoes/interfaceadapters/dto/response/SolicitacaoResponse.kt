package com.guigon.api_cartoes.interfaceadapters.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.interfaceadapters.dto.CartaoDto
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

class SolicitacaoResponse(
    @Schema(description = "Número da solicitação", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @JsonProperty("numero_solicitacao")
    val numeroSolicitacao: UUID,
    @Schema(description = "Data da solicitação", example = "2025-03-28T21:48:32.896", required = true)
    @JsonProperty("data_solicitacao")
    val dataSolicitacao: LocalDateTime,
    @Schema(description = "Dados do cliente", required = true)
    val cliente: ClienteDTO,
    @Schema(description = "Cartões ofertados", required = true)
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