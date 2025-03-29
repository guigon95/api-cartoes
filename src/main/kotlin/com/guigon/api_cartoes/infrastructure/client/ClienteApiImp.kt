package com.guigon.api_cartoes.infrastructure.client

import com.guigon.api_cartoes.application.ports.ClienteApi
import com.guigon.api_cartoes.domain.ClienteApiResponse
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
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
    private val meterRegistry: MeterRegistry
) : ClienteApi {
    private val logger = LoggerFactory.getLogger(ClienteApiImp::class.java)

    @CircuitBreaker(name = "clienteapicircuitcreaker", fallbackMethod = "requisicaoExternaFallback")
    override suspend fun requisicaoExterna(): ClienteApiResponse {
        logger.info("Iniciando requisição externa para a API: $apiUrl")
        meterRegistry.counter("api.clientes.chamadas.counter").increment()
        val timer = Timer.start(meterRegistry)
        val result: ClienteApiResponse
        try {
            result = webClient.get()
                .uri(apiUrl).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError) {
                    it.toEntity<String>().map { error ->
                        throw RuntimeException("Erro ao buscar dados externos: ${it.statusCode()} - $error")
                    }
                }
                .bodyToMono(ClienteApiResponse::class.java)
                .timeout(Duration.ofSeconds(1))
                .awaitSingle()
        }
        finally {
            timer.stop(meterRegistry.timer("api.clientes.chamadas.timer"))
        }
        return result
    }

    open fun requisicaoExternaFallback(throwable: Throwable): ClienteApiResponse {
        logger.error("Erro ao buscar dados externos: ${throwable.message}")
        throw RuntimeException("Erro ao buscar dados externos: ${throwable.message}")
    }
}