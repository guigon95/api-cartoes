package com.guigon.api_cartoes.interfaceadapters.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.guigon.api_cartoes.interfaceadapters.dto.ClienteDTO
import com.guigon.api_cartoes.interfaceadapters.dto.request.SolicitacaoRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@AutoConfigureWebTestClient
class CartaoControllerIT {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `'cartoes' deve retornar status 200 quando a solicitação for válida`() {

        val solicitacao = """{ 
            "cliente" : {
            "nome": "Fulano",
            "cpf": "123.456.789-00",
            "idade": 25,
            "data_nascimento": "1996-01-01",
            "uf": "SP",
            "renda_mensal": 1000.00,
            "email": "fulano@email.com",
            "telefone_whatsapp": "11999999999"
                    }
            }""".trimMargin()


        webTestClient.post()
            .uri("/cartoes")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(solicitacao)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo("APROVADO")
    }
}