package kr.co.popool.bblmember.service.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


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
    public static class READ {
        private String phoneNumber;
        private String email;
        private String name;
        private String period;
        private String context;
        private String filePath;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {
        private Long careerId;

        @NotBlank(message = "재직 기간을 입력해주세요")
        private String period;

        @NotBlank(message = "간단한 자기 소개를 입력해주세요")
        private String context;

        private String filePath;
    }
}

