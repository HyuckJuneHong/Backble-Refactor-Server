package kr.co.popool.persistence.entity;

import kr.co.popool.persistence.BaseEntity;
import kr.co.popool.service.model.dto.CareerDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_career")
@AttributeOverride(
        name = "id",
        column = @Column(name = "career_id")
)
public class CareerEntity extends BaseEntity {

    @Column(name = "member_identity", nullable = false)
    private String memberIdentity;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "period", nullable = false)
    private String period;

    @Column(name = "context")
    private String context;

    @Column(name = "file_path")
    private String filePath;

    @Builder
    public CareerEntity(String memberIdentity,
                        String name,
                        String period,
                        String context,
                        String filePath) {
        this.memberIdentity = memberIdentity;
        this.name = name;
        this.period = period;
        this.context = context;
        this.filePath = filePath;
    }

    public static CareerEntity toCareerEntity(CareerDto.CREATE create) {
        return CareerEntity.builder()
            .memberIdentity(create.getMemberIdentity())
            .name(create.getName())
            .context(create.getContext())
            .period(create.getPeriod())
            .filePath(create.getFilePath())
            .build();
    }

    public static CareerDto.READ toReadDto(CareerEntity careerEntity) {
        return CareerDto.READ.builder()
                .memberIdentity(careerEntity.getMemberIdentity())
                .name(careerEntity.getName())
                .period(careerEntity.getPeriod())
                .context(careerEntity.getContext())
                .filePath(careerEntity.getFilePath())
                .build();
    }

    public void updateCareer(CareerDto.UPDATE update) {
        this.name = update.getName();
        this.period = update.getPeriod();
        this.context = update.getContext();
        this.filePath = update.getFilePath();
    }
}