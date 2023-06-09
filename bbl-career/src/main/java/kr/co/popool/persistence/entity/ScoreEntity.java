package kr.co.popool.persistence.entity;

import kr.co.popool.persistence.BaseEntity;
import kr.co.popool.service.model.dto.ScoreDto;
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
    private String memberIdentity;

    @Column(nullable = false)
    private String evaluatorIdentity;

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

    @Builder
    public ScoreEntity(String memberIdentity,
                       String evaluatorIdentity,
                       double attend,
                       double since,
                       double positive,
                       double cooperative,
                       double technic) {
        this.memberIdentity = memberIdentity;
        this.evaluatorIdentity = evaluatorIdentity;
        this.attend = attend;
        this.since = since;
        this.positive = positive;
        this.cooperative = cooperative;
        this.technic = technic;
    }

    public static ScoreEntity toScoreEntity(ScoreDto.CREATE create) {
        return ScoreEntity.builder()
                .memberIdentity(create.getMemberIdentity())
                .evaluatorIdentity(create.getEvaluatorIdentity())
                .attend(create.getAttend())
                .since(create.getSince())
                .positive(create.getPositive())
                .cooperative(create.getCooperative())
                .technic(create.getTechnic())
                .build();
    }

    public static ScoreDto.READ toReadDto(ScoreEntity scoreEntity){
        return ScoreDto.READ.builder()
                .memberIdentity(scoreEntity.getMemberIdentity())
                .evaluatorIdentity(scoreEntity.getEvaluatorIdentity())
                .attend(scoreEntity.getAttend())
                .since(scoreEntity.getSince())
                .positive(scoreEntity.getPositive())
                .cooperative(scoreEntity.getCooperative())
                .technic(scoreEntity.getTechnic())
                .build();
    }

    public void updateScore(ScoreDto.UPDATE update) {
        this.memberIdentity = update.getMemberIdentity();
        this.evaluatorIdentity = update.getEvaluatorIdentity();
        this.attend = update.getAttend();
        this.since = update.getSince();
        this.positive = update.getPositive();
        this.cooperative = update.getCooperative();
        this.technic = update.getTechnic();
    }
}
