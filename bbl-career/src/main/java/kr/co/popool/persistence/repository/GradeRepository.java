package kr.co.popool.persistence.repository;


import kr.co.popool.persistence.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long> , GradeRepositoryCustom {
}
