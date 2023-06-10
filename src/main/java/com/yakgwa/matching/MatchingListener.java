package com.yakgwa.matching;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@RequiredArgsConstructor
public class MatchingListener {

    private final RabbitTemplate rabbitTemplate;
    private final MatchingService matchingService;
    private final MatchingController matchingController;
    /**
     * 채팅 서비스 측에서 매칭 결과를 받는 리스너
     *
     * MatchingMessage, Member 클래스 가져가서 데이터 받으면 됨
     */

    @RabbitListener(queues = "groupRoom.queue")
    public void receiveMessage(MatchingResultDto message) throws JsonProcessingException {
        System.out.println("MatchingListener.receiveMessage");

        rabbitTemplate.convertAndSend("amq.topic","matchingResult",message);
    }

}
