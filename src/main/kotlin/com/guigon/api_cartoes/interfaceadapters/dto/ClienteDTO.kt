package com.guigon.api_cartoes.interfaceadapters.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.guigon.api_cartoes.domain.Cliente
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.time.LocalDate

data class ClienteDTO(
    @NotBlank(message = "Nome é obrigatório")
    val nome: String,

    @NotBlank(message = "CPF é obrigatório")
    val cpf: String,

    @NotBlank(message = "Idade é obrigatória")
    val idade: Int,

    @NotBlank(message = "Data de nascimento é obrigatória")
    @JsonProperty("data_nascimento")
    val dataNascimento: LocalDate,

    @NotBlank(message = "UF é obrigatória")
    val uf: String,

    @NotBlank(message = "Renda mensal é obrigatória")
    @JsonProperty("renda_mensal")
    val rendaMensal: BigDecimal,

    @NotBlank(message = "Email é obrigatório")
    val email: String,

    @NotBlank(message = "Telefone é obrigatório")
    @JsonProperty("telefone_whatsapp")
    val telefoneWhatsapp: String
) {
    companion object {
        fun fromCliente(cliente: Cliente): ClienteDTO {
            return ClienteDTO(
                nome = cliente.nome,
                cpf = cliente.cpf,
                idade = cliente.idade,
                dataNascimento = cliente.dataNascimento,
                uf = cliente.uf,
                rendaMensal = cliente.rendaMensal,
                email = cliente.email,
                telefoneWhatsapp = cliente.telefoneWhatsapp
            )
        }

        fun toCliente(clienteDTO: ClienteDTO): Cliente {
            return Cliente(
                nome = clienteDTO.nome,
                cpf = clienteDTO.cpf,
                idade = clienteDTO.idade,
                dataNascimento = clienteDTO.dataNascimento,
                uf = clienteDTO.uf,
                rendaMensal = clienteDTO.rendaMensal,
                email = clienteDTO.email,
                telefoneWhatsapp = clienteDTO.telefoneWhatsapp
            )
        }
    }

}
