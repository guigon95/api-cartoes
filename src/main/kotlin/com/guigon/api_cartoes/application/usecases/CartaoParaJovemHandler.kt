package com.guigon.api_cartoes.application.usecases

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.OfertaCartaoStrategy
import com.guigon.api_cartoes.application.usecases.oferta.OfertaCartaoJovem
import com.guigon.api_cartoes.domain.Solicitacao
import org.springframework.beans.factory.annotation.Value

class CartaoParaJovemHandler(
    @Value("\${jovem.idade.minima}") private val idadeMinima: Int,
    @Value("\${jovem.idade.maxima}") private val idadeMaxima: Int
) : CartaoExigibilidadeHandler{
    override fun handle(solicitacao: Solicitacao): OfertaCartaoStrategy? {
        if (solicitacao.cliente.idade in idadeMinima..<idadeMaxima) {
            return OfertaCartaoJovem()
        }
        return  null
    }
}