package com.guigon.api_cartoes.interfaceadapters.controller

import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.infrastructure.config.IdadeProperties
import com.guigon.api_cartoes.interfaceadapters.dto.request.SolicitacaoRequest
import com.guigon.api_cartoes.interfaceadapters.dto.response.SolicitacaoResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cartoes")
class CartaoController(
    private val solicitacaoCartaoUseCase: SolicitarCartaoUseCase,
    private val idadeProperties: IdadeProperties
) {
    @Operation(summary = "Solicitar cartão")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Cartão com status aprovado",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = SolicitacaoResponse::class))]),
        ApiResponse(responseCode = "204", description = "Cliente não possui nenhum cartã aprovado", content = [Content()]),
        ApiResponse(responseCode = "400", description = "Requisição inválida", content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))])
    ])
    @PostMapping
    suspend fun solicitarCartao(@Valid @RequestBody request: SolicitacaoRequest): SolicitacaoResponse {
        val solicitacao = solicitacaoCartaoUseCase.solicitar(request.toSolicitacao(idadeProperties))
        return SolicitacaoResponse.fromSolicitacao(solicitacao)
    }

}