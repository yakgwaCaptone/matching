package com.yakgwa.matching;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchingMemberDto {
    private Long id;
    private Gender gender;

    public MatchingMemberDto(Long id, Gender gender) {
        this.id = id;
        this.gender = gender;
    }

}
