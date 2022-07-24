package com.github.dankharlushin.myteambot.api.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.dankharlushin.myteambot.api.exception.RestClientException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

class RestClient(
    val restTemplate: RestTemplate,
    val objectMapper: ObjectMapper,
    val entity: HttpEntity<String>,
    val baseApiUrl: String
) {

    companion object {
        private const val DATA_MAPPING = "data"
    }

    fun <T> getEntity(endpoint: String, clazz: Class<T>): T {
        val body = restTemplate.exchange(baseApiUrl + endpoint, HttpMethod.GET, entity, Map::class.java).body!!
        return objectMapper.convertValue(body[DATA_MAPPING], clazz)
    }

    fun <T> getListEntity(endpoint: String, clazz: Class<T>): List<T> {
        val body = restTemplate.exchange(baseApiUrl + endpoint, HttpMethod.GET, entity, Map::class.java)
            .body!![DATA_MAPPING]
        return when (body) {
            is List<*> -> body.map { objectMapper.convertValue(it, clazz)!! }
            is Map<*, *> -> body.values.map { objectMapper.convertValue(it, clazz)!! }
            else -> throw RestClientException("Unknown response format")
        }
    }
}
