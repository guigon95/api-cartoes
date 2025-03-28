package com.guigon.api_cartoes

import com.guigon.api_cartoes.infrastructure.config.IdadeProperties
import com.guigon.api_cartoes.infrastructure.config.JovemAdultoProperties
import com.guigon.api_cartoes.infrastructure.config.JovemProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(*[IdadeProperties::class, JovemProperties::class, JovemAdultoProperties::class])
@SpringBootApplication
class ApiCartoesApplication

fun main(args: Array<String>) {
	runApplication<ApiCartoesApplication>(*args)
}
