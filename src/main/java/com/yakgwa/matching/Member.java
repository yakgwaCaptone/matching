package com.yakgwa.matching;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
    private Long id;
    private Gender gender;

    public Member(Long id, Gender gender) {
        this.id = id;
        this.gender = gender;
    }

}
