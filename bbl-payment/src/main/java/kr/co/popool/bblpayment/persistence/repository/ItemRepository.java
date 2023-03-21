package kr.co.popool.bblpayment.persistence.repository;

import kr.co.popool.bblpayment.persistence.entity.item.ItemMstEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository<T extends ItemMstEntity> extends JpaRepository<T, Long> {
}
