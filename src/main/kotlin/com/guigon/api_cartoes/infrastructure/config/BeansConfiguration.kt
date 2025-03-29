package com.guigon.api_cartoes.infrastructure.config

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.ClienteApi
import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.application.usecases.SolicitacaoCartaoUseCaseImpl
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaJovemAdultoSP
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaJovemHandler
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaResidenteSPHandler
import com.guigon.api_cartoes.infrastructure.client.ClienteApiImp
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

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
    fun clienteApi(webClient: WebClient): ClienteApi {
        return ClienteApiImp(webClient)
    }

    @Bean
    fun solicitarCartaoUseCase(
        cartaoExigibilidadeList: List<CartaoExigibilidadeHandler>,
        clienteApi: ClienteApi
    ): SolicitarCartaoUseCase {
        return SolicitacaoCartaoUseCaseImpl(cartaoExigibilidadeList, clienteApi)
    }
}