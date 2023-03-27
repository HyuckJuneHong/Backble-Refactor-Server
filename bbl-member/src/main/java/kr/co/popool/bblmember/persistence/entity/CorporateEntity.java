package kr.co.popool.bblmember.persistence.entity;

import kr.co.popool.bblmember.service.model.Address;
import kr.co.popool.bblmember.service.model.BaseEntity;
import kr.co.popool.bblmember.service.model.PhoneNumber;
import kr.co.popool.bblmember.service.model.dtos.CorporateDto;
import kr.co.popool.bblmember.service.model.enums.Gender;
import kr.co.popool.bblmember.service.model.enums.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tbl_corporate")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "corporate_id")),
        @AttributeOverride(name = "email", column = @Column(name = "email", nullable = false)),
        @AttributeOverride(name = "phone", column = @Column(name = "phone_number", nullable = false)),
        @AttributeOverride(name = "address", column = @Column(nullable = false))
})
public class CorporateEntity extends BaseEntity {

    @Column(name = "ceo_name", nullable = false)
    private String ceoName;

    @Column(name = "business_number", nullable = false)
    private String businessNumber;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Builder
    public CorporateEntity(String identity,
                           String password,
                           String email,
                           String name,
                           String birth,
                           PhoneNumber phoneNumber,
                           Gender gender,
                           MemberRole memberRole,
                           String ceoName,
                           String businessNumber,
                           String businessName) {
        super(identity, password, email, name, birth, phoneNumber, gender, memberRole);
        this.ceoName = ceoName;
        this.businessNumber = businessNumber;
        this.businessName = businessName;
    }

    public static CorporateEntity toCorporateEntity(CorporateDto.CREATE_CORPORATE createCorporate,
                                                    PasswordEncoder passwordEncoder) {
        return CorporateEntity.builder()
                .ceoName(createCorporate.getCeoName())
                .businessName(createCorporate.getBusinessName())
                .businessNumber(createCorporate.getBusinessNumber())
                .identity(createCorporate.getIdentity())
                .password(passwordEncoder.encode(createCorporate.getPassword()))
                .name(createCorporate.getName())
                .birth(createCorporate.getBirth())
                .phoneNumber(new PhoneNumber(createCorporate.getPhone()))
                .gender(Gender.of(createCorporate.getGender()))
                .memberRole(MemberRole.ROLE_GUEST)
                .build();
    }

    public static CorporateDto.READ_CORPORATE toCorporateDto(CorporateEntity corporateEntity){
        return CorporateDto.READ_CORPORATE.builder()
                .identity(corporateEntity.getIdentity())
                .name(corporateEntity.getName())
                .address(corporateEntity.getAddress())
                .birth(corporateEntity.getBirth())
                .email(corporateEntity.getEmail())
                .phoneNumber(corporateEntity.getPhone())
                .gender(corporateEntity.getGender())
                .create_at(corporateEntity.getCreatedAt())
                .businessName(corporateEntity.businessName)
                .ceoName(corporateEntity.ceoName)
                .businessNumber(corporateEntity.getBusinessNumber())
                .build();
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void corporateUpdate(CorporateDto.UPDATE_CORPORATE updateCorporate){
        this.ceoName = updateCorporate.getCeoName();
        this.businessName = updateCorporate.getBusinessName();
        this.businessNumber = updateCorporate.getBusinessNumber();
        this.name = updateCorporate.getName();
        this.email = updateCorporate.getEmail();
        this.phone = PhoneNumber.builder()
                .number(updateCorporate.getPhoneNumber())
                .build();
        this.address = Address.builder()
                .zipcode(updateCorporate.getZipCode())
                .address1(updateCorporate.getAddress1())
                .address2(updateCorporate.getAddress2())
                .build();
    }
}
