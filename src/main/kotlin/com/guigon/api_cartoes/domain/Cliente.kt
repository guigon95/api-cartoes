package com.guigon.api_cartoes.domain

class Cliente(
    val nome: String,
    val cpf: String,
    val idade: Int,
    val dataNascimento: String,
    val uf: String,
    val rendaMensal: Double,
    val email: String,
    val telefoneWhatsapp: String
) {
}