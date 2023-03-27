package kr.co.popool.bblmember.persistence.repository;

import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import kr.co.popool.bblmember.service.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    //find
    Optional<MemberEntity> findByIdentity(String identity);

    //exists
    boolean existsByIdentity(String identity);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(PhoneNumber phoneNumber);
}
