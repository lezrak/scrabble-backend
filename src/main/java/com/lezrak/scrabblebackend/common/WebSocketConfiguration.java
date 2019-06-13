package com.lezrak.scrabblebackend.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
//        te.setPoolSize(1);
//        te.setThreadNamePrefix("wss-heartbeat-thread-");
//        te.initialize();
//
//        config.enableSimpleBroker("/lobby")
//                .setHeartbeatValue(new long[]{0, 1000})
//                .setTaskScheduler(te);
//    }
}
