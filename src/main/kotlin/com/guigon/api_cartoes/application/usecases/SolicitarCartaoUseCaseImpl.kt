package com.guigon.api_cartoes.application.usecases

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.domain.exceptions.NenhumCriterioAceitoException
import org.slf4j.LoggerFactory

class SolicitacaoCartaoUseCaseImpl(
    private val handlers: List<CartaoExigibilidadeHandler>
): SolicitarCartaoUseCase {

    private val logger = LoggerFactory.getLogger(SolicitacaoCartaoUseCaseImpl::class.java)

    override suspend fun solicitar(solicitacao: Solicitacao): Solicitacao {
        for (handler in handlers) {
            val oferta = handler.handle(solicitacao)
            if (oferta != null) {
                logger.info("Oferta encontrada para a solicitacao: ${solicitacao.numeroSolicitacao} - ${oferta.javaClass.simpleName}")
                solicitacao.cartoesOfertados = oferta.obter(solicitacao)
                return solicitacao
            }
        }
        throw NenhumCriterioAceitoException("Nenhuma oferta disponível")
    }
}