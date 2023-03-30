package kr.co.popool.bblmember.persistence.entity;

import kr.co.popool.bblmember.persistence.BaseEntity;
import kr.co.popool.bblmember.service.model.dtos.CareerDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_career")
@AttributeOverride(
        name = "id",
        column = @Column(name = "career_id")
)
public class CareerEntity extends BaseEntity {
    @Column(name = "period", nullable = false)
    private String period;

    @Column(name = "context")
    private String context;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Builder
    public CareerEntity(String period,
                        String context,
                        String filePath,
                        MemberEntity memberEntity) {
        this.period = period;
        this.context = context;
        this.filePath = filePath;
        this.memberEntity = memberEntity;
    }

    public static CareerEntity toCareerEntity(CareerDto.CREATE create,
                                              MemberEntity memberEntity) {
        return CareerEntity.builder()
            .context(create.getContext())
            .period(create.getPeriod())
            .filePath(create.getFilePath())
            .memberEntity(memberEntity)
            .build();
    }

    public static CareerDto.READ toReadDto(CareerEntity careerEntity) {
        return CareerDto.READ.builder()
                .phoneNumber(careerEntity.getMemberEntity().getPhoneNumber().toString())
                .email(careerEntity.getMemberEntity().getEmail())
                .name(careerEntity.getMemberEntity().getName())
                .period(careerEntity.getPeriod())
                .context(careerEntity.getContext())
                .filePath(careerEntity.getFilePath())
                .build();
    }

    public void updateCareer(CareerDto.UPDATE update) {
        this.period = update.getPeriod();
        this.context = update.getContext();
        this.filePath = update.getFilePath();
    }
}