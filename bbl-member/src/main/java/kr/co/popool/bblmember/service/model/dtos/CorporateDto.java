package kr.co.popool.bblmember.service.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popool.bblmember.service.model.Address;
import kr.co.popool.bblmember.service.model.PhoneNumber;
import kr.co.popool.bblmember.service.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CorporateDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE_CORPORATE{
        @ApiModelProperty(example = "대표 성함")
        @NotBlank(message = "기업 대표 성함을 입력해주세요.")
        private String ceoName;

        @ApiModelProperty(example = "사업자번호")
        @NotBlank(message = "111-1111-1111")
        private String businessNumber;

        @ApiModelProperty(example = "사업자명")
        @NotBlank(message = "사업자명을 입력해주세요.")
        private String businessName;

        @ApiModelProperty(example = "사용할 아이디")
        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 5, max = 16, message = "ID는 5 ~ 16자를 입력해주세요")
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
        private String phone;

        @ApiModelProperty(example = "MALE or FEMALE")
        @NotBlank(message = "성별을 입력해주세요.")
        private String gender;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE_CORPORATE{
        @ApiModelProperty(example = "변경할 대표 성함")
        @NotBlank(message = "기업 대표 성함을 입력해주세요.")
        private String ceoName;

        @ApiModelProperty(example = "변경할 사업자번호")
        @NotBlank(message = "사업자 번호를 입력해주세요.")
        private String businessNumber;

        @ApiModelProperty(example = "변경할 사업자명")
        @NotBlank(message = "사업자명을 입력해주세요.")
        private String businessName;

        @ApiModelProperty(example = "example@email.com")
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;

        @ApiModelProperty(example = "홍길동")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @ApiModelProperty(example = "12345")
        @NotBlank(message = "우편번호를 입력해주세요")
        private String zipCode;

        @ApiModelProperty(example = "서울특별시 강남구 선릉로 627")
        @NotBlank(message = "기본 주소를 입력해주세요")
        private String address1;

        @ApiModelProperty(example = "101호")
        @NotBlank(message = "상세 주소를 입력해주세요")
        private String address2;

        @ApiModelProperty(example = "010-XXXX-XXXX")
        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        private String phoneNumber;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ_CORPORATE{
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

        @ApiModelProperty(example = "변경할 대표 성함")
        private String ceoName;

        @ApiModelProperty(example = "변경할 사업자번호")
        private String businessNumber;

        @ApiModelProperty(example = "변경할 사업자명")
        private String businessName;
    }

}
