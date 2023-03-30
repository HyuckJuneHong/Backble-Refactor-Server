package kr.co.popool.service.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ScoreDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE{
        @NotBlank(message = "평가 대상 아이디를 입력하세요.")
        private String memberIdentity;
        @NotBlank(message = "본인 아이디를 입력하세요.")
        private String evaluatorIdentity;
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
        @NotBlank(message = "평가대상")
        private String memberIdentity;
        @NotBlank(message = "평가자")
        private String evaluatorIdentity;
        private double attend;
        private double since;
        private double positive;
        private double cooperative;
        private double technic;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {
        @NotBlank(message = "평가 대상 아이디를 입력하세요.")
        private String memberIdentity;
        @NotBlank(message = "본인 아이디를 입력하세요.")
        private String evaluatorIdentity;
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
    public static class DELETE {
        private String memberIdentity;
        private String evaluatorIdentity;
    }
}


