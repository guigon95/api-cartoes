package com.guigon.api_cartoes.domain

import java.math.BigDecimal
import java.time.LocalDate

class Cliente(
    val nome: String,
    val cpf: String,
    val idade: Int,
    val dataNascimento: LocalDate,
    val uf: String,
    val rendaMensal: BigDecimal,
    val email: String,
    val telefoneWhatsapp: String
)