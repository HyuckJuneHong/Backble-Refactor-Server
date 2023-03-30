package kr.co.popool.bblmember.service.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import kr.co.popool.bblmember.service.model.enums.Rank;
import lombok.*;

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

    private double totalAvg;

    private Rank rank;

    private String name;

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

    private MemberEntity memberEntity;
    private Rank rank;

    @ApiModelProperty(example = "총 평가 인원")
    private long memberCount;
  }
}

