package com.guigon.api_cartoes.domain.exceptions

import java.lang.RuntimeException

class NenhumCriterioAceitoException(mensagem: String = "Nenhum cartão de crédito disponível") : RuntimeException(mensagem)
