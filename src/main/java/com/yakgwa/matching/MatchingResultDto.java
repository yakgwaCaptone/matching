package com.yakgwa.matching;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MatchingResultDto {
    //회원 정보
    private Long mid1;
    private Long mid2;
    private Long mid3;
    private Long mid4;
    private Long mid5;
    private Long mid6;

        //제리 정보
    private Long jerry_id;

    //room id
    private Long id;

    //생성시간
    private LocalDateTime created_at;

    private boolean status;

    public MatchingResultDto(Long mid1, Long mid2, Long mid3, Long mid4, Long mid5, Long mid6, Long jerry_id, Long id, LocalDateTime created_at, boolean status) {
        this.mid1 = mid1;
        this.mid2 = mid2;
        this.mid3 = mid3;
        this.mid4 = mid4;
        this.mid5 = mid5;
        this.mid6 = mid6;
        this.jerry_id = jerry_id;
        this.id = id;
        this.created_at = created_at;
        this.status = status;
    }
}
