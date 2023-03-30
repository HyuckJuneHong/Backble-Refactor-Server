package kr.co.popool.bblmember.service.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Rank {

    GOLD("골드"),
    SILVER("실버"),
    BRONZE("브론즈"),
    WHITE("등급 없음");

    private String rank;

    public static Rank of(String rank) {
        return Arrays.stream(Rank.values())
                .filter(r -> r.toString().equalsIgnoreCase(rank))
                .findAny().orElseThrow(() -> new RuntimeException("해당 등급 항목을 찾을 수 없습니다."));
    }
}



