package com.guigon.api_cartoes.interfaceadapters.controller

import com.guigon.api_cartoes.domain.exceptions.NenhumCriterioAceitoException
import com.guigon.api_cartoes.domain.exceptions.RegrasDeNegocioException
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.net.ConnectException
import java.util.concurrent.TimeoutException

@RestControllerAdvice
class ExceptionHandler(
    @Value("\${spring.application.name}") private val nomeApp: String,
) {

    @ExceptionHandler(NenhumCriterioAceitoException::class)
    fun handleNenhumCriterioAceitoException(ex: NenhumCriterioAceitoException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            codigo = "400",
            mensagem = "Nenhum critério aceito",
            detalheErro = DetalheErro(
                app = nomeApp,
                tipoErro = "CRITERIO_NAO_ACEITO",
                mensagemInterna = ex.message!!
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.NO_CONTENT)
    }

    @ExceptionHandler(RegrasDeNegocioException::class)
    fun handleRegrasDeNegocioException(ex: RegrasDeNegocioException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            codigo = "400",
            mensagem = ex.message ?: "Regras de negócio não atendidas",
            detalheErro = DetalheErro(
                app = nomeApp,
                tipoErro = "REGRA_DE_NEGOCIO",
                mensagemInterna = ex.message!!
            )
        )

        return ResponseEntity(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(CallNotPermittedException::class)
    fun handleCallNotPermittedException(ex: CallNotPermittedException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            codigo = "503",
            mensagem = "Serviço indisponível",
            detalheErro = DetalheErro(
                app = nomeApp,
                tipoErro = ex.stackTrace.firstOrNull()?.className ?: "SERVICO_INDISPONIVEL",
                mensagemInterna = ex.message!!
            )
        )

        return ResponseEntity(errorResponse, HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(TimeoutException::class)
    fun handleTimeoutException(ex: TimeoutException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            codigo = "504",
            mensagem = "Tempo de resposta excedido",
            detalheErro = DetalheErro(
                app = nomeApp,
                tipoErro = "TIMEOUT",
                mensagemInterna = ex.message!!
            )
        )

        return ResponseEntity(errorResponse, HttpStatus.GATEWAY_TIMEOUT)
    }

    @ExceptionHandler(ConnectException::class)
    fun handleConnectException(ex: ConnectException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            codigo = "503",
            mensagem = "Serviço indisponível",
            detalheErro = DetalheErro(
                app = nomeApp,
                tipoErro = "SERVICO_INDISPONIVEL",
                mensagemInterna = ex.message!!
            )
        )

        return ResponseEntity(errorResponse, HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            codigo = "500",
            mensagem = "Tivemos um problema, mas fique tranquilo que o nosso time ja foi avisado.",
            detalheErro = DetalheErro(
                app = nomeApp,
                tipoErro = "ERRO_INTERNO",
                mensagemInterna = ex.message!!
            )
        )

        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}