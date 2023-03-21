package kr.co.popool.bblpayment.persistence.repository;

import kr.co.popool.bblpayment.persistence.entity.item.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findInventoryEntityByMemberId(Long memberId);
}
