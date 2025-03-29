package com.guigon.api_cartoes.infrastructure.config

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.ClienteApi
import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.application.usecases.SolicitacaoCartaoUseCaseImpl
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaJovemAdultoSP
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaJovemHandler
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaResidenteSPHandler
import com.guigon.api_cartoes.infrastructure.client.ClienteApiImp
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
class BeansConfiguration {

    @Bean
    fun cartaoParaJovemHandler(): CartaoParaJovemHandler {
        return CartaoParaJovemHandler()
    }

    @Bean
    fun cartaoParaJovemAdultoHandler(): CartaoParaJovemAdultoSP {
        return CartaoParaJovemAdultoSP()
    }

    @Bean
    fun cartaoParaResidenteSPHandler(): CartaoParaResidenteSPHandler {
        return CartaoParaResidenteSPHandler()
    }

    @Bean
    fun idadeProperties(
        jovemProperties: JovemProperties,
        jovemAdultoProperties: JovemAdultoProperties
    ): IdadeProperties {
        return IdadeProperties(jovemProperties, jovemAdultoProperties)
    }

    @Bean
    fun jovemProperties(): JovemProperties {
        return JovemProperties()
    }

    @Bean
    fun jovemAdultoProperties(): JovemAdultoProperties {
        return JovemAdultoProperties()
    }

    @Bean
    fun cartaoExigibilidadeList(
        cartaoExigibilidadeList: List<CartaoExigibilidadeHandler>
    ): List<CartaoExigibilidadeHandler> {
        return cartaoExigibilidadeList
    }

    @Bean
    fun webClient(): WebClient {
        return WebClient.create()
    }

    @Bean
    fun clienteApi(
        webClient: WebClient,
        @Value("\${cliente.api.url}") apiUrl: String,
        meterRegistry: MeterRegistry
        ): ClienteApi {
        return ClienteApiImp(webClient, apiUrl, meterRegistry)
    }


    @Bean
    fun circuitBreakerRegistry(): CircuitBreakerRegistry {
        val circuitBreakerConfig = CircuitBreakerConfig.custom()
//            .failureRateThreshold(50.0f)
//            .waitDurationInOpenState(Duration.ofSeconds(10))
//            .slidingWindowSize(5)
//            .permittedNumberOfCallsInHalfOpenState(3)
//            .minimumNumberOfCalls(5)
            .build()

        return CircuitBreakerRegistry.of(circuitBreakerConfig)
    }


    @Bean
    fun clienteApiCircuitBreaker(circuitBreakerRegistry: CircuitBreakerRegistry) =
        circuitBreakerRegistry.circuitBreaker("clienteApiCircuitBreaker")

    @Bean
    fun solicitarCartaoUseCase(
        cartaoExigibilidadeList: List<CartaoExigibilidadeHandler>,
        clienteApi: ClienteApi
    ): SolicitarCartaoUseCase {
        return SolicitacaoCartaoUseCaseImpl(cartaoExigibilidadeList, clienteApi)
    }
}