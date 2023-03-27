package kr.co.popool.bblmember.service.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MemberRole {

    ROLE_GUEST("일반"),
    ROLE_ADMIN("관리자");

    private String memberRole;

    public static MemberRole of(String role) {
        return Arrays.stream(MemberRole.values())
                .filter(r -> r.toString().equalsIgnoreCase(role))
                .findAny().orElseThrow(() -> new RuntimeException("해당 Role 항목을 찾을 수 없습니다."));
    }
}
