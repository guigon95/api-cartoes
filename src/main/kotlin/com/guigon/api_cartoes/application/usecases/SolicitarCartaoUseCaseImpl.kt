package com.guigon.api_cartoes.application.usecases

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.domain.exceptions.CriteriosNaoAceitosException

class SolicitacaoCartaoUseCaseImpl(
    private val handlers: List<CartaoExigibilidadeHandler>
): SolicitarCartaoUseCase {
    override fun solicitar(solicitacao: Solicitacao): Solicitacao {

        for (handler in handlers) {
            val oferta = handler.handle(solicitacao.cliente)
            if (oferta != null) {
                solicitacao.cartoesOfertados = oferta.obter(solicitacao.cliente)
                return solicitacao
            }
        }
        throw CriteriosNaoAceitosException("Nenhuma oferta disponível")
    }
}