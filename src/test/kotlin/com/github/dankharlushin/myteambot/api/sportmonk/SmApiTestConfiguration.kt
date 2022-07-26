package com.github.dankharlushin.myteambot.api.sportmonk

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

@TestConfiguration
class SmApiTestConfiguration {

    @Bean
    fun restClient(@Value("\${api.url}") baseUrl: String): RestClient {
        val objectMapper = ObjectMapper()
        objectMapper.findAndRegisterModules()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.registerKotlinModule()

        return RestClient(restTemplate =  RestTemplate(), objectMapper =  objectMapper,
            entity = HttpEntity<String>(HttpHeaders()), baseApiUrl = baseUrl)
    }

    @Bean
    fun servletWebServerFactory(): ServletWebServerFactory? {
        return TomcatServletWebServerFactory()
    }
}