package kr.co.popool.bblmember.service.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popool.bblmember.service.model.Address;
import kr.co.popool.bblmember.service.model.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


public class CareerDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {
        @NotBlank(message = "재직 기간을 입력해주세요")
        private String period;

        @NotBlank(message = "간단한 자기 소개를 입력해주세요")
        private String context;

        @ApiModelProperty(example = "첨부할 파일이 있으면 경로를 작성해주세요.")
        private String filePath;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ_CAREER_CORPORATE {
        private String name;
        private String gender;
        private String birth;
        private String identity;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {
        private String name;
        private String gender;
        private String birth;
        private String email;
        private PhoneNumber phoneNumber;
        private Address address;
        private String period;
        private String filePath;
        private String context;
        private LocalDate createAt;
        private LocalDate updateAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {
        @NotBlank(message = "재직 기간을 입력해주세요")
        private String period;

        @NotBlank(message = "간단한 자기 소개를 입력해주세요")
        private String context;

        private String filePath;
    }
}

