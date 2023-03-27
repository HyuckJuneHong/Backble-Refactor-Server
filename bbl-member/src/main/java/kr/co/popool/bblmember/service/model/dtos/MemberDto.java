package kr.co.popool.bblmember.service.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popool.bblmember.service.model.Address;
import kr.co.popool.bblmember.service.model.PhoneNumber;
import kr.co.popool.bblmember.service.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class MemberDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LOGIN{
        @ApiModelProperty(example = "사용자 아이디")
        @NotBlank(message = "아이디를 입력해주세요")
        private String identity;

        @ApiModelProperty(example = "사용자 비밀번호")
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TOKEN{
        @ApiModelProperty(example = "사용자 인증을 위한 accessToken")
        private String accessToken;
        @ApiModelProperty(example = "자동 로그인을 위한 refreshToken")
        private String refreshToken;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE_MEMBER{
        @ApiModelProperty(example = "사용할 아이디")
        @NotBlank(message = "아이디를 입력해주세요.")
        private String identity;

        @ApiModelProperty(example = "사용할 비밀번호")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        @ApiModelProperty(example = "사용할 비밀번호 확인")
        @NotBlank(message = "확인 비밀번호를 입력해주세요.")
        private String checkPassword;

        @ApiModelProperty(example = "홍길동")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @ApiModelProperty(example = "YYYYmmDD")
        @NotBlank(message = "생년월일을 입력해주세요.")
        private String birth;

        @ApiModelProperty(example = "010-XXXX-XXXX")
        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        private String phoneNumber;

        @ApiModelProperty(example = "MALE or FEMALE")
        @NotBlank(message = "성별을 입력해주세요.")
        private String gender;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE{
        @ApiModelProperty(example = "example@email.com")
        @Email
        private String email;

        @ApiModelProperty(example = "홍길동")
        private String name;

        @ApiModelProperty(example = "12345")
        private String zipCode;

        @ApiModelProperty(example = "서울특별시 강남구 선릉로 627")
        private String address1;

        @ApiModelProperty(example = "101호")
        private String address2;

        @ApiModelProperty(example = "010-XXXX-XXXX")
        private String phoneNumber;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE_PASSWORD {
        @ApiModelProperty(example = "현재 비밀번호")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        private String originalPassword;

        @ApiModelProperty(example = "변경할 비밀번호")
        @NotBlank(message = "변경할 비밀번호를 입력해주세요")
        private String newPassword;

        @ApiModelProperty(example = "변경 비밀번호 확인")
        @NotBlank(message = "확인 비밀번호를 입력해주세요")
        private String newCheckPassword;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class READ {
        @ApiModelProperty(example = "사용자 아이디")
        private String identity;

        @ApiModelProperty(example = "홍길동")
        private String name;

        private Address address;

        private PhoneNumber phoneNumber;

        @ApiModelProperty(example = "example@email.com")
        private String email;

        @ApiModelProperty(example = "MALE or FEMALE")
        private Gender gender;

        @ApiModelProperty(example = "YYmmDD")
        private String birth;

        @ApiModelProperty(example = "2022-01-01")
        private LocalDateTime create_at;
    }
}
