package kr.co.popool.persistence.repository;

import kr.co.popool.persistence.entity.CareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity, Long> {
    Optional<CareerEntity> findByMemberIdentity(String memberIdentity);
}