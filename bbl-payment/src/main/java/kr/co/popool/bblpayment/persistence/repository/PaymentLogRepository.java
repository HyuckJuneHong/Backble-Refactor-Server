package kr.co.popool.bblpayment.persistence.repository;

import kr.co.popool.bblpayment.persistence.entity.payment.PaymentLogMstEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLogMstEntity, Long> {
    @EntityGraph(attributePaths = {"item"})
    List<PaymentLogMstEntity> findPaymentLogMstEntitiesByMemberId(Long memberId);
}
