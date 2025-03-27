package com.guigon.api_cartoes.application.usecases

import com.guigon.api_cartoes.application.ports.OfertaCartaoStrategy
import com.guigon.api_cartoes.domain.Cartao
import com.guigon.api_cartoes.domain.Cliente
import com.guigon.api_cartoes.domain.TipoCartaoEnum.CARTAO_SEM_ANUIDADE
import com.guigon.api_cartoes.domain.exceptions.CriteriosJovemException

class OfertaCartaoJovem : OfertaCartaoStrategy {
    override fun obter(cliente: Cliente): List<Cartao> {
        if(cliente.rendaMensal > 1000){
            return listOf(CARTAO_SEM_ANUIDADE.criarCartao())
        }

        throw CriteriosJovemException()
    }
}