package com.bridge.community.utils

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class MessageClient(
    @Value("\${app.agent.message.baseurl}")
    private val baseUrl: String,
    @Value("\${app.agent.message.appKey}")
    private val apiKey: String,
) {

    companion object {
        private val log = LoggerFactory.getLogger(MessageClient::class.java)
    }

    private val webClient: WebClient = WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader("x-api-key", apiKey)
        .build()


    fun sendMessage(phoneNumber: String, content: String) {
        try {

            val resp = webClient.post()
                .uri("/send/sms")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(
                    mapOf(
                        "domain" to "member",
                        "phone" to phoneNumber,
                        "body" to content,
                    )
                )
                .retrieve()
                .bodyToMono(String::class.java)
                .doOnError { log.error("error", it) }
                .block()
            log.info(resp)
        } catch (ignored: Exception) {
            log.error("SendMessageFail::", ignored)
        }
    }

}