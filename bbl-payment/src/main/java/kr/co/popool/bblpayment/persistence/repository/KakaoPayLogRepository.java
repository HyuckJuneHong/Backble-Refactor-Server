package kr.co.popool.bblpayment.persistence.repository;

import kr.co.popool.bblpayment.persistence.entity.payment.KakaoPayLogEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KakaoPayLogRepository extends JpaRepository<KakaoPayLogEntity, Long> {
    @EntityGraph(attributePaths = {"item"})
    Optional<KakaoPayLogEntity> findById(Long logId);
}
