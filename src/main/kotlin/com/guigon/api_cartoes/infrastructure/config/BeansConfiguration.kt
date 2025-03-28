package com.guigon.api_cartoes.infrastructure.config

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaJovemHandler
import com.guigon.api_cartoes.application.usecases.SolicitacaoCartaoUseCaseImpl
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaJovemAdultoSP
import com.guigon.api_cartoes.application.usecases.handlers.CartaoParaResidenteSPHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
    fun solicitarCartaoUseCase(
        cartaoExigibilidadeList: List<CartaoExigibilidadeHandler>
    ): SolicitarCartaoUseCase {
        return SolicitacaoCartaoUseCaseImpl(cartaoExigibilidadeList)
    }
}