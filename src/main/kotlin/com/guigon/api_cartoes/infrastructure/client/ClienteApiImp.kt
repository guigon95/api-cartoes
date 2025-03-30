package com.guigon.api_cartoes.infrastructure.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.guigon.api_cartoes.application.ports.ClienteApi
import com.guigon.api_cartoes.domain.Cliente
import com.guigon.api_cartoes.domain.ClienteApiResponse
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO
import com.guigon.api_cartoes.interfaceadapters.dto.response.ClienteApiResponseDTO
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import java.time.Duration

open class ClienteApiImp(
    private val webClient: WebClient,
    private val apiUrl: String,
    private val meterRegistry: MeterRegistry,
    private val circuitBreaker: CircuitBreaker,
    private val objectMapper: ObjectMapper
) : ClienteApi {
    private val logger = LoggerFactory.getLogger(ClienteApiImp::class.java)

    override suspend fun requisicaoExterna(cliente: Cliente): ClienteApiResponse {
        logger.info("Iniciando requisição externa para a API: $apiUrl")
        meterRegistry.counter("api.clientes.chamadas.counter").increment()
        val timer = Timer.start(meterRegistry)
        val result: ClienteApiResponseDTO
        val clienteJson = objectMapper.writeValueAsString(ClienteDTO.fromCliente(cliente))
        logger.info("ClienteDTO.fromCliente(cliente): ${ClienteDTO.fromCliente(cliente)}")
        try {
            result = circuitBreaker.executeSuspendFunction {
                webClient.post()
                    .uri(apiUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(clienteJson)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError) {
                        it.toEntity<String>().map { error ->
                            logger.error("Erro ao buscar dados externos: ${it.statusCode()} - $error")
                            throw RuntimeException("Erro ao buscar dados externos: ${it.statusCode()} - $error")
                        }
                    }
                    .bodyToMono(ClienteApiResponseDTO::class.java)
                    .timeout(Duration.ofSeconds(1))
                    .awaitSingle()
            }
        }
        finally {
            circuitBreaker.eventPublisher.onEvent { event: CircuitBreakerEvent ->
                logger.info("CircuitBreaker [{}] mudou de estado: {}", circuitBreaker.name, event.eventType)
            }
            timer.stop(meterRegistry.timer("api.clientes.chamadas.timer"))
        }
        return ClienteApiResponse(result.id)
    }

    open fun requisicaoExternaFallback(throwable: Throwable): ClienteApiResponse {
        logger.error("Erro ao buscar dados externos: ${throwable.message}")
        throw RuntimeException("Erro ao buscar dados externos: ${throwable.message}")
    }
}