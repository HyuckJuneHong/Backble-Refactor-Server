package kr.co.popool.bblmember.service.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ScoreDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE{
        @NotBlank(message = "평가 대상 아이디를 입력하세요.")
        private String otherMemberIdentity;
        @ApiModelProperty(example = "근태")
        private double attend;
        @ApiModelProperty(example = "성실성")
        private double since;
        @ApiModelProperty(example = "적극성")
        private double positive;
        @ApiModelProperty(example = "협업능력")
        private double cooperative;
        @ApiModelProperty(example = "기술스킬")
        private double technic;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {
        private double attend;
        private double since;
        private double positive;
        private double cooperative;
        private double technic;

        @NotBlank(message = "평가대상")
        private String otherMemberIdentity;

        private LocalDate create;

        private LocalDate update;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DELETE {
        private String otherMemberIdentity;
    }
}


