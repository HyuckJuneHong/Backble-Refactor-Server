package kr.co.popool.bblmember.persistence.repository;


import kr.co.popool.bblmember.persistence.entity.GradeEntity;
import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long>{
    Optional<GradeEntity> findByMemberEntity(MemberEntity memberEntity);

    boolean existsByMemberEntity(MemberEntity memberEntity);
}
