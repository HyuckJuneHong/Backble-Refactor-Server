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

    @OneToOne(fetch = FetchType.LAZY)
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

    public static CareerDto.READ toReadDto(CareerEntity careerEntity,
                                           MemberEntity memberEntity) {
        return CareerDto.READ.builder()
                .name(memberEntity.getName())
                .gender(memberEntity.getGender().toString())
                .birth(memberEntity.getBirth())
                .email(memberEntity.getEmail())
                .phoneNumber(memberEntity.getPhoneNumber())
                .address(memberEntity.getAddress())
                .period(careerEntity.getPeriod())
                .filePath(careerEntity.getFilePath())
                .context(careerEntity.getContext())
                .createAt(careerEntity.getCreatedAt())
                .updateAt(careerEntity.getUpdatedAt())
                .build();
    }

    public static CareerDto.READ_CAREER_CORPORATE toReadCorporateDto(CareerEntity careerEntity) {
        return CareerDto.READ_CAREER_CORPORATE.builder()
                .name(careerEntity.getMemberEntity().getName())
                .gender(careerEntity.getMemberEntity().getGender().toString())
                .birth(careerEntity.getMemberEntity().getBirth())
                .identity(careerEntity.getMemberEntity().getIdentity())
                .build();
    }

    public void updateCareer(CareerDto.UPDATE update) {
        this.period = update.getPeriod();
        this.context = update.getContext();
        this.filePath = update.getFilePath();
    }
}