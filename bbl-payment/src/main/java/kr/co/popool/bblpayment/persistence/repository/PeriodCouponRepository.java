package kr.co.popool.bblpayment.persistence.repository;

import kr.co.popool.bblpayment.persistence.entity.item.PeriodCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodCouponRepository extends JpaRepository<PeriodCouponEntity, Long> {
}
