package kr.co.popool.bblmember.service.model;

import kr.co.popool.bblmember.service.model.enums.Gender;
import kr.co.popool.bblmember.service.model.enums.MemberRole;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of="id", callSuper = false)
@Getter
@NoArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt = null;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @Column(name = "identity", unique = true, nullable = false)
    protected String identity;

    @Column(name = "password", nullable = false)
    protected String password;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "birth", nullable = false)
    protected String birth;

    @Embedded
    protected PhoneNumber phone;

    @Embedded
    @Column(name = "address")
    @AttributeOverrides({
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode")),
            @AttributeOverride(name = "address1", column = @Column(name = "address1")),
            @AttributeOverride(name = "address2", column = @Column(name = "address2"))
    })
    protected Address address;

    @Column(name = "gender", nullable = false)
    @Enumerated(value = EnumType.STRING)
    protected Gender gender;

    @Column(name = "member_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    protected MemberRole memberRole;

    public BaseEntity(String identity,
                      String password,
                      String email,
                      String name,
                      String birth,
                      PhoneNumber phone,
                      Gender gender,
                      MemberRole memberRole) {
        this.identity = identity;
        this.password = password;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.gender = gender;
        this.memberRole = memberRole;
    }
}
