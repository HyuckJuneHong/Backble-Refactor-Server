package kr.co.popool.service.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Data
public class GradeDto {

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CREATE {
    private double attendSum;
    private double attendAvg;

    private double sinceSum;
    private double sinceAvg;

    private double positiveSum;
    private double positiveAvg;

    private double cooperativeSum;
    private double cooperativeAvg;

    private double technicSum;
    private double technicAvg;

    private String memberIdentity;

    @ApiModelProperty(example = "총 평가 인원")
    private long memberCount;
  }

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class READ {
    private double attendAvg;

    private double sinceAvg;

    private double positiveAvg;

    private double cooperativeAvg;

    private double technicAvg;

    private String memberIdentity;

    private long memberCount;
  }

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UPDATE {
    private double attendSum;
    private double attendAvg;

    private double sinceSum;
    private double sinceAvg;

    private double positiveSum;
    private double positiveAvg;

    private double cooperativeSum;
    private double cooperativeAvg;

    private double technicSum;
    private double technicAvg;

    private String memberIdentity;

    @ApiModelProperty(example = "총 평가 인원")
    private long memberCount;
  }

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DELETE {
    private String memberIdentity;

    @ApiModelProperty(example = "총 평가 인원")
    private long memberCount;
  }
}

