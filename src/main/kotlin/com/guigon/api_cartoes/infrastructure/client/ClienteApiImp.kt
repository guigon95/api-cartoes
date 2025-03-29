package com.guigon.api_cartoes.infrastructure.client

import com.guigon.api_cartoes.application.ports.ClienteApi
import com.guigon.api_cartoes.domain.ClienteApiResponse
import com.guigon.api_cartoes.domain.exceptions.ClienteApiException
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.util.retry.Retry
import java.time.Duration

class ClienteApiImp(
    private val webClient: WebClient
) : ClienteApi {
    override suspend fun requisicaoExterna(): ClienteApiResponse {
        val result = webClient.get()
            .uri("https://67e731de6530dbd31112a54d.mockapi.io/api/v1/clientes").accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError) {
                it.toEntity<String>().map { error ->
                    throw ClienteApiException("Erro ao buscar dados externos: ${it.statusCode()} - $error")
                }
            }
            .bodyToMono(ClienteApiResponse::class.java)
            .timeout(Duration.ofMillis(100))
//            .retryWhen(
//                Retry.backoff(3, Duration.ofMillis(100))
//                    .maxBackoff(Duration.ofMillis(500))
//            )
            .awaitSingle()

        return result
    }
}