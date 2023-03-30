package kr.co.popool.bblmember.persistence.entity;

import kr.co.popool.bblmember.service.model.Address;
import kr.co.popool.bblmember.persistence.UserMstEntity;
import kr.co.popool.bblmember.service.model.PhoneNumber;
import kr.co.popool.bblmember.service.model.dtos.MemberDto;
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
@Table(name = "tbl_member")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "member_id")),
    @AttributeOverride(name = "email", column = @Column(name = "email", unique = true)),
    @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number", unique = true))
})
public class MemberEntity extends UserMstEntity {

    @Builder
    public MemberEntity(String identity,
                        String password,
                        String email,
                        String name,
                        String birth,
                        PhoneNumber phoneNumber,
                        Gender gender,
                        MemberRole memberRole) {
        super(
                identity,
                password,
                email,
                name,
                birth,
                phoneNumber,
                gender,
                memberRole
        );
    }

    public static MemberEntity toMemberEntity(MemberDto.CREATE_MEMBER create,
                                              PasswordEncoder passwordEncoder) {
        return MemberEntity.builder()
                .identity(create.getIdentity())
                .password(passwordEncoder.encode(create.getPassword()))
                .name(create.getName())
                .birth(create.getBirth())
                .phoneNumber(new PhoneNumber(create.getPhoneNumber()))
                .gender(Gender.of(create.getGender()))
                .memberRole(MemberRole.ROLE_GUEST)
                .build();
    }

    public static MemberDto.READ toMemberDto(MemberEntity memberEntity){
        return MemberDto.READ.builder()
                .identity(memberEntity.getIdentity())
                .name(memberEntity.getName())
                .address(memberEntity.getAddress())
                .birth(memberEntity.getBirth())
                .email(memberEntity.getEmail())
                .phoneNumber(memberEntity.getPhoneNumber())
                .gender(memberEntity.getGender())
                .create_at(memberEntity.getCreatedAt())
                .build();
    }

    public void updateMember(MemberDto.UPDATE memberUpdate){
        this.name = memberUpdate.getName();
        this.email = memberUpdate.getEmail();
        this.phoneNumber = PhoneNumber.builder()
                .phoneNumber(memberUpdate.getPhoneNumber())
                .build();
        this.address = Address.builder()
                .zipcode(memberUpdate.getZipCode())
                .address1(memberUpdate.getAddress1())
                .address2(memberUpdate.getAddress2())
                .build();
    }

    public void updatePassword(String password){
        this.password = password;
    }
}
