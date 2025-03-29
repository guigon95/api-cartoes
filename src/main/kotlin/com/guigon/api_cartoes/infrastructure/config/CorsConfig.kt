package com.guigon.api_cartoes.infrastructure.config

import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

class CorsConfig : WebFluxConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // Permite CORS para todos os endpoints
            .allowedOrigins("http://localhost:3000") // URL do seu frontend (ajuste conforme necessário)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Métodos permitidos
            .allowedHeaders("*") // Permite todos os cabeçalhos
            .allowCredentials(true) // Permite o envio de cookies, se necessário
            .maxAge(3600) // Tempo de cache para as permissões de CORS (em segundos)
    }
}