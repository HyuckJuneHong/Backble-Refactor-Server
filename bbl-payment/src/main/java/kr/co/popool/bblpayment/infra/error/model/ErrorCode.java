package kr.co.popool.bblpayment.infra.error.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //success
    SUCCESS_NULL("실행이 성공했고, 응답 데이터는 없습니다.", 2000),

    //fail
    FAIL_NULL("응답이 실패했고, 응답 데이터는 없습니다.", 4000),
    FAIL_EXPIRE("응답이 실패했고, 원인은 토큰 만료입니다.", 4003);

    private String message;
    private int status;
}
