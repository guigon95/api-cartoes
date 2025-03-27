package com.guigon.api_cartoes.infrastructure.config

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.SolicitarCartaoUseCase
import com.guigon.api_cartoes.application.usecases.CartaoParaJovemHandler
import com.guigon.api_cartoes.application.usecases.OfertaCartaoJovem
import com.guigon.api_cartoes.application.usecases.SolicitacaoCartaoUseCaseImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeansConfiguration {

    @Bean
    fun cartaoParaJovemHandler(
        @Value("\${jovem.idade.minima}") idadeMinima: Int,
        @Value("\${jovem.idade.maxima}") idadeMaxima: Int
    ): CartaoParaJovemHandler {
        return CartaoParaJovemHandler(idadeMinima, idadeMaxima)
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