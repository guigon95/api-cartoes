package com.guigon.api_cartoes.application.usecases.handlers

import com.guigon.api_cartoes.application.ports.CartaoExigibilidadeHandler
import com.guigon.api_cartoes.application.ports.OfertaCartaoStrategy
import com.guigon.api_cartoes.application.usecases.oferta.OfertaCartaoJovem
import com.guigon.api_cartoes.domain.Solicitacao
import com.guigon.api_cartoes.domain.exceptions.CriteriosJovemException

class CartaoParaJovemHandler : CartaoExigibilidadeHandler{
    override suspend fun handle(solicitacao: Solicitacao): OfertaCartaoStrategy? {
        if (solicitacao.ehJovem()) {
            return OfertaCartaoJovem()
        }

        if (solicitacao.ehMenorDeIdade()) {
            throw CriteriosJovemException("Cliente é menor de idade.")
        }
        return  null
    }
}