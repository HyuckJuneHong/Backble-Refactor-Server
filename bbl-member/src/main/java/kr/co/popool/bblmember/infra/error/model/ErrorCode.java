package kr.co.popool.bblmember.infra.error.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS_NULL("실행이 성공했고, 응답 데이터는 없습니다.", 2000),
    SUCCESS_VALUE("실행이 성공했고, 응답 데이터가 있습니다.", 2001),

    //fail
    FAIL_NULL("응답이 실패했고, 응답 데이터는 없습니다.", 4000),
    FAIL_EXPIRE("응답이 실패했고, 원인은 토큰 만료입니다.", 4003),

    UNAUTHORIZED_USER("권한이 없습니다.", 403),
    RE_LOGIN("로그인을 다시 해주세요.", 403),

    WRONG_PASSWORD("비밀번호를 다시 확인해주세요", 400),
    WRONG_PHONE("전화번호를 다시 확인해주세요", 400),
    WRONG_CORPORATE("기업 회원이 아닙니다.", 400),
    DUPLICATED_ID("중복된 아이디를 사용할 수 없습니다.", 400),
    DUPLICATED_PHONE("중복된 전화번호를 사용할 수 없습니다.", 400),
    DUPLICATED_EMAIL("중복된 이메일을 사용할 수 없습니다.", 400);

    private String message;
    private int status;
}
