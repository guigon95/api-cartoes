package com.guigon.api_cartoes.application.usecases

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.ClienteApi
import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.domain.exceptions.NenhumCriterioAceitoException
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory

class SolicitacaoCartaoUseCaseImpl(
    private val handlers: List<CartaoExigibilidadeHandler>,
    private val clienteApi: ClienteApi
): SolicitarCartaoUseCase {

    private val logger = LoggerFactory.getLogger(SolicitacaoCartaoUseCaseImpl::class.java)

    override suspend fun solicitar(solicitacao: Solicitacao): Solicitacao = coroutineScope {
        val solicitacaoDeferred = async {
            for (handler in handlers) {
                val oferta = handler.handle(solicitacao)
                if (oferta != null) {
                    logger.info("Oferta encontrada para a solicitacao: ${solicitacao.numeroSolicitacao} - ${oferta.javaClass.simpleName}")
                    solicitacao.cartoesOfertados = oferta.obter(solicitacao)
                    return@async solicitacao
                }
            }

            throw NenhumCriterioAceitoException("Nenhuma oferta disponível")
        }

        val requisicaoClienteDeferred = async { clienteApi.requisicaoExterna(solicitacao.cliente) }

        solicitacaoDeferred.await()
        requisicaoClienteDeferred.await()

        return@coroutineScope solicitacao
    }
}