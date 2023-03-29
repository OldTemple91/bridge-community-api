package com.bridge.community.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@EnableWebSocketMessageBroker
@Configuration
class StompWebSocketConfig(
    @Value("\${spring.rabbitmq.username}")
    private val username: String,
    @Value("\${spring.rabbitmq.password}")
    private val password: String,
    @Value("\${spring.rabbitmq.host}")
    private val host: String,
    @Value("\${spring.rabbitmq.port}")
    private val port: Int
): WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/bridge/chat")
            .setAllowedOriginPatterns("http://192.168.50.26:3001","http://192.168.50.26", "http://localhost:3001", "http://localhost:8080", "*")
            .setAllowedOrigins("GET")
            .withSockJS()
    }

    //rabbitmq
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/pub")
            .enableStompBrokerRelay("/topic", "/queue", "/exchange", "/amq/queue")
            .setRelayHost(host)
            .setVirtualHost("/")
            .setRelayPort(port)
            .setClientLogin(username)
            .setClientPasscode(password)
    }

    //stomp
//    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
//        registry.setApplicationDestinationPrefixes("/pub")
//        registry.enableSimpleBroker("/sub")
//    }
}
