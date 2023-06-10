package com.yakgwa.matching.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 참고 블로그
 * https://velog.io/@jkijki12/채팅-STOMP-JWT
 */
@Configuration
@EnableWebSocketMessageBroker // STOMP 사용을 위함
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/pub"); // /pub 으로 시작하는 도착지 처리 -> 컨트롤러에서 받음

        config.enableStompBrokerRelay("/topic")
                .setRelayHost("13.209.204.63")
                .setRelayPort(61613)
                .setVirtualHost("/")
                .setClientLogin("guest")
                .setClientPasscode("guest");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // ex) ws://localhost:8080/ws
        // 크롬 확장 apic 통해서 ws -> stomp 통해서 이용 가능
        // 연결 url 정보 ws://localhost:8080/ws   이런 형식
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }
}
