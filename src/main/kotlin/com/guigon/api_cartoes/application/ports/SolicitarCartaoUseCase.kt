package com.guigon.api_cartoes.application.ports

import com.guigon.api_cartoes.domain.Solicitacao

fun interface SolicitarCartaoUseCase {
    fun solicitar(solicitacao: Solicitacao): Solicitacao
}