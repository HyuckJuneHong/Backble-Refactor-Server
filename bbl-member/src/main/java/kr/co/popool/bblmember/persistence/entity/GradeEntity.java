package kr.co.popool.bblmember.persistence.entity;

import kr.co.popool.bblmember.persistence.BaseEntity;
import kr.co.popool.bblmember.service.model.dtos.GradeDto;
import kr.co.popool.bblmember.service.model.enums.Rank;
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

    @Column(name = "ranking")
    @Enumerated(value = EnumType.STRING)
    private Rank rank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Builder
    public GradeEntity(double attendSum,
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
                       Rank rank,
                       MemberEntity memberEntity) {
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
        this.memberEntity = memberEntity;
    }

    public static GradeEntity toFirstGradeEntity(MemberEntity memberEntity){
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
                .rank(Rank.WHITE)
                .memberCount(0)
                .memberEntity(memberEntity)
                .build();
    }

    public static GradeEntity toGradeEntity(GradeDto.CREATE create,
                                            MemberEntity memberEntity,
                                            Rank rank) {
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
                .memberEntity(memberEntity)
                .rank(rank)
                .memberCount(create.getMemberCount())
                .build();
    }

    public static GradeDto.READ toReadDto(GradeEntity gradeEntity,
                                          double totalAvg) {
        return GradeDto.READ.builder()
                .attendAvg(gradeEntity.getAttendAvg())
                .sinceAvg(gradeEntity.getSinceAvg())
                .positiveAvg(gradeEntity.getPositiveAvg())
                .cooperativeAvg(gradeEntity.getCooperativeAvg())
                .technicAvg(gradeEntity.getTechnicAvg())
                .totalAvg(totalAvg)
                .name(gradeEntity.getMemberEntity().getName())
                .rank(gradeEntity.getRank())
                .memberCount(gradeEntity.getMemberCount())
                .create(gradeEntity.createdAt)
                .update(gradeEntity.updatedAt)
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
        this.memberCount = update.getMemberCount();
    }
}


