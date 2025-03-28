package com.guigon.api_cartoes.domain.exceptions

import java.lang.RuntimeException

open class RegrasDeNegocioException(mensagem: String) : RuntimeException(mensagem)