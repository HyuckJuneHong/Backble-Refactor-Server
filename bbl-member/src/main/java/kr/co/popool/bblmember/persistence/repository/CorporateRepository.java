package kr.co.popool.bblmember.persistence.repository;

import kr.co.popool.bblmember.persistence.entity.CorporateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporateRepository extends JpaRepository<CorporateEntity, Long> {

    //find
    Optional<CorporateEntity> findById(Long id);
    Optional<CorporateEntity> findByIdentity(String identity);

    //exists
    boolean existsByIdentity(String identity);
}
