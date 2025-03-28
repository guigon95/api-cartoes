package com.guigon.api_cartoes.interfaceadapters.controller

import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.infrastructure.config.IdadeProperties
import com.guigon.api_cartoes.interfaceadapters.dto.request.SolicitacaoRequest
import com.guigon.api_cartoes.interfaceadapters.dto.response.SolicitacaoResponse
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
    @PostMapping
    fun solicitarCartao(@Valid @RequestBody request: SolicitacaoRequest): SolicitacaoResponse {
        val solicitacao = solicitacaoCartaoUseCase.solicitar(request.toSolicitacao(idadeProperties))
        return SolicitacaoResponse.fromSolicitacao(solicitacao)
    }

}