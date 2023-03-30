package kr.co.popool.persistence.repository;


import kr.co.popool.persistence.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long>{
    Optional<GradeEntity> findByMemberIdentity(String memberIdentity);

    boolean existsByMemberIdentity(String memberIdentity);
}
