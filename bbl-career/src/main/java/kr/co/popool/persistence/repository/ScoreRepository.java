package kr.co.popool.persistence.repository;

import kr.co.popool.persistence.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> , ScoreRepositoryCustom {
    Optional<ScoreEntity> findById(Long id);
    Optional<ScoreEntity> findByEvaluatorIdentity(String evaluatorIdentity);
}
