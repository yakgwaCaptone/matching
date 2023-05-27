package com.yakgwa.matching;

import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@Component
public class MatchingListener {

    /**
     * 채팅 서비스 측에서 매칭 결과를 받는 리스너
     *
     * MatchingMessage, Member 클래스 가져가서 데이터 받으면 됨
     */
//
//    @RabbitListener(queues = "matching.queue")
//    public void receiveMessage(MatchingMessage message) {
//        System.out.println("message = " + message);
//    }

}
