package com.guigon.api_cartoes.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "idade")
class IdadeProperties(
    var jovem: JovemProperties,
    var jovemAdulto: JovemAdultoProperties
)

@ConfigurationProperties(prefix = "jovem")
class JovemProperties(
    var minima: Int = 0,
    var maxima: Int = 0
)

@ConfigurationProperties(prefix = "jovem-adulto")
class JovemAdultoProperties(
    var minima: Int = 0,
    var maxima: Int = 0
)