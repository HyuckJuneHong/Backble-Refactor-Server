package kr.co.popool.persistence.entity;

import kr.co.popool.service.model.dto.CareerDto;
import kr.co.popool.service.model.BaseEntity;
import kr.co.popool.service.model.enums.ScoreGrade;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"member_identity", "history_id"})},
        name = "tbl_career"
)
@Getter
@Entity
@AttributeOverride(
        name = "id",
        column = @Column(name = "career_id")
)
public class CareerEntity extends BaseEntity {

    @Column(name = "member_identity", nullable = false, length = 100)
    private String memberIdentity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade")
    private GradeEntity gradeEntity;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "period", nullable = false, length = 100)
    private String period;

    @Column(name = "context", nullable = true, length = 500)
    private String context;

    @Column(name = "history_id", nullable = true, length = 100)
    private String historyId;

    @Column(name = "file_path", nullable = true, length = 100)
    private String filePath;

    @Builder
    public CareerEntity(String memberIdentity,
                        GradeEntity gradeEntity,
                        String name,
                        String period,
                        String context,
                        String historyId,
                        String filePath) {
      this.memberIdentity = memberIdentity;
      this.gradeEntity = gradeEntity;
      this.name = name;
      this.period = period;
      this.historyId = historyId;
      this.context = context;
      this.filePath = filePath;
    }

    public static CareerEntity toCareerEntity(CareerDto.CREATE newCareer) {
        return CareerEntity.builder()
            .memberIdentity(newCareer.getMemberIdentity())
            .name(newCareer.getName())
            .context(newCareer.getContext())
            .period(newCareer.getPeriod())
            .historyId(newCareer.getHistoryId())
            .build();
    }

    public static CareerDto.CAREERINFO toCareerInfoDto(CareerEntity careerEntity) {
        return CareerDto.CAREERINFO.builder()
                .memberIdentity(careerEntity.getMemberIdentity())
                .name(careerEntity.getName())
                .grade(careerEntity.getGradeEntity().getGrade())
                .period(careerEntity.getPeriod())
                .context(careerEntity.getContext())
                .historyId(careerEntity.getHistoryId())
                .build();
    }

    public static CareerDto.CAREERINFO toNoneGradeDto(CareerEntity careerEntity) {
        return CareerDto.CAREERINFO.builder()
                .memberIdentity(careerEntity.getMemberIdentity())
                .name(careerEntity.getName())
                .grade(ScoreGrade.WHITE)
                .period(careerEntity.getPeriod())
                .context(careerEntity.getContext())
                .historyId(careerEntity.getHistoryId())
                .build();

    }

    public void updateCareer(CareerDto.UPDATE careerUpdate) {
        this.name = careerUpdate.getName();
        this.period = careerUpdate.getPeriod();
        this.context = careerUpdate.getContext();
        this.historyId = careerUpdate.getHistoryId();
    }

    public void createGrade(GradeEntity gradeEntity) {
      this.gradeEntity = gradeEntity;
    }
}