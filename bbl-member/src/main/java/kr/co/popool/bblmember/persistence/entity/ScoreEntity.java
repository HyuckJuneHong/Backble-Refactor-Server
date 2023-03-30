package kr.co.popool.bblmember.persistence.entity;

import kr.co.popool.bblmember.persistence.BaseEntity;
import kr.co.popool.bblmember.service.model.dtos.ScoreDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tbl_score")
@AttributeOverride(
        name = "id",
        column = @Column(name = "score_id")
)
public class ScoreEntity extends BaseEntity {
    @Column(nullable = false)
    private double attend;

    @Column(nullable = false)
    private double since;

    @Column(nullable = false)
    private double positive;

    @Column(nullable = false)
    private double cooperative;

    @Column(nullable = false)
    private double technic;

    @Column(nullable = false)
    private String otherMemberIdentity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Builder
    public ScoreEntity(double attend,
                       double since,
                       double positive,
                       double cooperative,
                       double technic,
                       String otherMemberIdentity,
                       MemberEntity memberEntity) {
        this.attend = attend;
        this.since = since;
        this.positive = positive;
        this.cooperative = cooperative;
        this.technic = technic;
        this.otherMemberIdentity = otherMemberIdentity;
        this.memberEntity = memberEntity;
    }

    public static ScoreEntity toScoreEntity(ScoreDto.CREATE create,
                                            MemberEntity memberEntity) {
        return ScoreEntity.builder()
                .attend(create.getAttend())
                .since(create.getSince())
                .positive(create.getPositive())
                .cooperative(create.getCooperative())
                .technic(create.getTechnic())
                .otherMemberIdentity(create.getOtherMemberIdentity())
                .memberEntity(memberEntity)
                .build();
    }

    public static ScoreDto.READ toReadDto(ScoreEntity scoreEntity){
        return ScoreDto.READ.builder()
                .otherMemberIdentity(scoreEntity.getOtherMemberIdentity())
                .attend(scoreEntity.getAttend())
                .since(scoreEntity.getSince())
                .positive(scoreEntity.getPositive())
                .cooperative(scoreEntity.getCooperative())
                .technic(scoreEntity.getTechnic())
                .build();
    }
}
