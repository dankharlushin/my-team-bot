package com.github.dankharlushin.myteambot.api.sportdata

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.dankharlushin.myteambot.api.client.RestClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate

@Deprecated("bug in sportdata api")
@TestConfiguration
class SdApiTestConfiguration {

    @Bean
    fun restClient(@Value("\${api.key}") apiKey: String,
                   @Value("\${api.url}") baseUrl: String): RestClient {
        val objectMapper = ObjectMapper()
        objectMapper.findAndRegisterModules()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.registerKotlinModule()

        val headers = HttpHeaders()
        headers.set("apikey", apiKey)

        return RestClient(restTemplate =  RestTemplate(), objectMapper =  objectMapper,
            entity = HttpEntity<String>(headers), baseApiUrl = baseUrl)
    }

    @Bean
    fun servletWebServerFactory(): ServletWebServerFactory? {
        return TomcatServletWebServerFactory()
    }
}