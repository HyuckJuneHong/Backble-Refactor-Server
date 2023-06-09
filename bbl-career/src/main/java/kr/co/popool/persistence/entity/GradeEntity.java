package kr.co.popool.persistence.entity;

import kr.co.popool.persistence.BaseEntity;
import kr.co.popool.service.model.dto.GradeDto;
import kr.co.popool.service.model.enums.Rank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_grade")
@Getter
@Entity
@AttributeOverride(
        name = "id",
        column = @Column(name = "grade_id")
)
public class GradeEntity extends BaseEntity {
    @Column(name = "member_identity", nullable = false)
    private String memberIdentity;

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

    private long memberCount;

    @Enumerated(value = EnumType.STRING)
    private Rank rank;

    @Builder
    public GradeEntity(String memberIdentity,
                       double attendSum,
                       double attendAvg,
                       double sinceSum,
                       double sinceAvg,
                       double positiveSum,
                       double positiveAvg,
                       double cooperativeSum,
                       double cooperativeAvg,
                       double technicSum,
                       double technicAvg,
                       long memberCount,
                       Rank rank) {
        this.memberIdentity = memberIdentity;
        this.attendSum = attendSum;
        this.attendAvg = attendAvg;
        this.sinceSum = sinceSum;
        this.sinceAvg = sinceAvg;
        this.positiveSum = positiveSum;
        this.positiveAvg = positiveAvg;
        this.cooperativeSum = cooperativeSum;
        this.cooperativeAvg = cooperativeAvg;
        this.technicSum = technicSum;
        this.technicAvg = technicAvg;
        this.memberCount = memberCount;
        this.rank = rank;
    }

    public static GradeEntity toFirstGradeEntity(String identity){
        return GradeEntity.builder()
                .attendSum(0)
                .attendAvg(0)
                .sinceSum(0)
                .sinceAvg(0)
                .positiveSum(0)
                .positiveAvg(0)
                .cooperativeSum(0)
                .cooperativeAvg(0)
                .technicSum(0)
                .technicAvg(0)
                .memberCount(0)
                .memberIdentity(identity)
                .build();
    }

    public static GradeEntity toGradeEntity(GradeDto.CREATE create) {
        return GradeEntity.builder()
                .attendSum(create.getAttendSum())
                .attendAvg(create.getAttendAvg())
                .sinceSum(create.getSinceSum())
                .sinceAvg(create.getSinceAvg())
                .positiveSum(create.getPositiveSum())
                .positiveAvg(create.getPositiveAvg())
                .cooperativeSum(create.getCooperativeSum())
                .cooperativeAvg(create.getCooperativeAvg())
                .technicSum(create.getTechnicSum())
                .technicAvg(create.getTechnicAvg())
                .memberIdentity(create.getMemberIdentity())
                .memberCount(create.getMemberCount())
                .build();
    }

    public static GradeDto.READ toReadDto(GradeEntity gradeEntity) {
        return GradeDto.READ.builder()
                .attendAvg(gradeEntity.getAttendAvg())
                .sinceAvg(gradeEntity.getSinceAvg())
                .positiveAvg(gradeEntity.getPositiveAvg())
                .cooperativeAvg(gradeEntity.getCooperativeAvg())
                .technicAvg(gradeEntity.getTechnicAvg())
                .memberIdentity(gradeEntity.getMemberIdentity())
                .memberCount(gradeEntity.getMemberCount())
                .build();
    }

    public void updateGrade(GradeDto.UPDATE update) {
        this.attendSum = update.getAttendSum();
        this.attendAvg = update.getAttendAvg();
        this.sinceSum = update.getSinceSum();
        this.sinceAvg = update.getSinceAvg();
        this.positiveSum = update.getPositiveSum();
        this.positiveAvg = update.getPositiveAvg();
        this.cooperativeSum = update.getCooperativeSum();
        this.cooperativeAvg = update.getCooperativeAvg();
        this.technicSum = update.getTechnicSum();
        this.technicAvg = update.getTechnicAvg();
        this.memberIdentity = update.getMemberIdentity();
        this.memberCount = update.getMemberCount();
    }
}


