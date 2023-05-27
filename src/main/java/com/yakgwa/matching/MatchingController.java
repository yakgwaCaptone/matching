package com.yakgwa.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;

    /**
     * 매칭 취소
     */
    @MessageMapping("/matching/cancel")
    public void CancelMatching(RequestDto requestDto) {
        Long id = requestDto.getId();
        String strGender = requestDto.getGender();
        System.out.println("매칭 취소 member id = " + id);

        if (strGender.equals("W"))
            matchingService.cancelMatching(id, Gender.W);
        if (strGender.equals("M"))
            matchingService.cancelMatching(id, Gender.M);
    }

    /**
     * 매칭
     */
    @MessageMapping("/matching")
    public void Matching(RequestDto requestDto) {
        Long id = requestDto.getId();
        String strGender = requestDto.getGender();
        System.out.println("매칭 요청 member id = " + id);

        if (strGender.equals("W"))
            matchingService.startMatching(id, Gender.W);
        if (strGender.equals("M"))
            matchingService.startMatching(id, Gender.M);
    }

}
