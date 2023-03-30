package kr.co.popool.bblmember.persistence.repository;

import kr.co.popool.bblmember.persistence.entity.CareerEntity;
import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity, Long> {
    Optional<CareerEntity> findByMemberEntity(MemberEntity memberEntity);
}