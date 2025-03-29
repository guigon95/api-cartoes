package com.guigon.api_cartoes.infrastructure.client

import com.guigon.api_cartoes.application.ports.ClienteApi
import com.guigon.api_cartoes.domain.ClienteApiResponse
import com.guigon.api_cartoes.domain.exceptions.ClienteApiException
import io.micrometer.core.annotation.Timed
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.util.retry.Retry
import java.time.Duration

class ClienteApiImp(
    private val webClient: WebClient,
    private val apiUrl: String,
    private val meterRegistry: MeterRegistry
) : ClienteApi {
    private val logger = LoggerFactory.getLogger(ClienteApiImp::class.java)

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
                        throw ClienteApiException("Erro ao buscar dados externos: ${it.statusCode()} - $error")
                    }
                }
                .bodyToMono(ClienteApiResponse::class.java)
                .timeout(Duration.ofSeconds(1))
                .retry(3)
                .awaitSingle()
        } finally {
            timer.stop(meterRegistry.timer("api.clientes.chamadas.timer"))
        }
        return result
    }
}