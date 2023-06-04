package com.yakgwa.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final RabbitTemplate rabbitTemplate;
    Queue<Long> men = new ConcurrentLinkedQueue<>(); // 멀티쓰레드 환경에서 동기화용 큐
    Queue<Long> women = new ConcurrentLinkedQueue<>();
    Random random = new Random();
    private static final int PARTIAL_MINIMUM_SIZE = 2;
    private static final int TOTAL_MINIMUM_SIZE = 6;
    List<MatchingMemberDto> tmp = new ArrayList<>();


    /**
     * 매칭 취소
     * @param id
     * @param gender
     */
    public void cancelMatching(Long id, Gender gender) {
        System.out.println("MatchingService.cancelMatching");
        System.out.println("id = " + id + " 의 매칭을 취소합니다 " + gender.toString());

        // 매칭 취소 (같은 값이 여러개 있을 경우 전부 삭제)
        if (gender == Gender.M) {
            for (; men.remove(id); ) ;
        }
        else if (gender == Gender.W) {
            for (; women.remove(id);) ;
        }
    }

    /**
     * 매칭 시작
     * @param id
     * @param gender
     */
    public void startMatching(Long id, Gender gender) {
        System.out.println("MatchingService.startMatching");


        // 매칭 대기 큐에 추가
        if (gender == Gender.M) {
            for (; men.remove(id); ) ; // 만약 기존에 매칭 신청했으면 삭제 후 다시 추가
            men.add(id);
        }
        else if (gender == Gender.W) {
            for (; women.remove(id);) ; // 만약 기존에 매칭 신청했으면 삭제 후 다시 추가
            women.add(id);
        }

        List<MatchingMemberDto> matchingMembers = matching();
        if (matchingMembers != null) {
            System.out.println("매칭 완료");
            System.out.println("RabbitMQ로 매칭된 정보 보내기");
            sendMatchingMessage(new MatchingMessage(matchingMembers));
            for (int i = 0; i < matchingMembers.size(); i++) {
                System.out.println("matchingMembers.get(i) = " + matchingMembers.get(i));
            }
        }
    }

    private boolean validate() {
        int menSize = men.size();
        int womenSize = women.size();
        System.out.println("womenSize = " + womenSize);
        System.out.println("menSize = " + menSize);

        // 남자
        if (menSize < PARTIAL_MINIMUM_SIZE)
            return false;
        // 여자
        if (womenSize < PARTIAL_MINIMUM_SIZE)
            return false;
        // 총 인원수
        if (menSize + womenSize < TOTAL_MINIMUM_SIZE)
            return false;
        return true;
    }

    private List<MatchingMemberDto> matching() {
        System.out.println("매칭 서비스 - 매칭(내부 함수) 시작");

        List<MatchingMemberDto> matchingMembers = new ArrayList<>();

        // 조건 검사
        if (!validate()) {
            return null;
        }

        // 남자 2명
        for (int m = 0; m < 2; m++) {
            matchingMembers.add(new MatchingMemberDto(men.remove(), Gender.M));
        }

        // 여자 2명
        for (int w = 0; w < 2; w++) {
            matchingMembers.add(new MatchingMemberDto(women.remove(), Gender.W));
        }

        // 나머지 인원 추가
        while (matchingMembers.size() < TOTAL_MINIMUM_SIZE) {
            int number = random.nextInt(2);
            // 남자
            if (number == 0 && men.size() > 0) {
                matchingMembers.add(new MatchingMemberDto(men.remove(), Gender.M));
            }
            // 여자
            else if (number == 1 && women.size() > 0) {
                matchingMembers.add(new MatchingMemberDto(women.remove(), Gender.W));
            }
        }

        return matchingMembers;
    }

    public void sendMatchingMessage(MatchingMessage matchingMessage) {
        System.out.println("MatchingService.sendMatchingMessage");
        rabbitTemplate.convertAndSend("matching.exchange", "matching.key", matchingMessage);
    }

}
