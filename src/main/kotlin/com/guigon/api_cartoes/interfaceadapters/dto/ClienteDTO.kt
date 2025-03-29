package com.guigon.api_cartoes.interfaceadapters.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.guigon.api_cartoes.domain.Cliente
import com.guigon.api_cartoes.interfaceadapters.exceptions.IdadeException
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Period

data class ClienteDTO(
    @Schema(description = "Nome do cliente", example = "João da Silva", required = true)
    @NotBlank(message = "Nome é obrigatório")
    val nome: String,

    @Schema(description = "CPF do cliente", example = "123.456.789-00", required = true)
    @NotBlank(message = "CPF é obrigatório")
    val cpf: String,

    @Schema(description = "Idade do cliente", example = "30", required = true)
    @NotEmpty(message = "Idade é obrigatória")
    val idade: Int,

    @Schema(description = "Data de nascimento do cliente", example = "1990-01-01", required = true)
    @NotBlank(message = "Data de nascimento é obrigatória")
    @JsonProperty("data_nascimento")
    val dataNascimento: LocalDate,

    @Schema(description = "UF do cliente", example = "SP", required = true)
    @NotBlank(message = "UF é obrigatória")
    val uf: String,

    @Schema(description = "Renda mensal do cliente", example = "1000.00", required = true)
    @DecimalMin(value = "0.0", inclusive = true, message = "Renda mensal não deve ser negativa")
    @JsonProperty("renda_mensal")
    val rendaMensal: BigDecimal,

    @Schema(description = "Email do cliente", example = "joao@email.com", required = true)
    @NotBlank(message = "Email é obrigatório")
    val email: String,

    @Schema(description = "Telefone do cliente", example = "11999999999", required = true)
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
            val idadeCalculada = Period.between(clienteDTO.dataNascimento, LocalDate.now()).years
            if (idadeCalculada < 18){
                throw IdadeException()
            }
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
