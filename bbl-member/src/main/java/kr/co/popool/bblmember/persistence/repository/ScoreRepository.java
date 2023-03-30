package kr.co.popool.bblmember.persistence.repository;

import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import kr.co.popool.bblmember.persistence.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {

    List<ScoreEntity> findByOtherMemberIdentity(String otherMemberIdentity);

    List<ScoreEntity> findByMemberEntity(MemberEntity memberEntity);

    Optional<ScoreEntity> findByMemberEntityAndOtherMemberIdentity(MemberEntity memberEntity, String otherMemberIdentity);

    boolean existsByMemberEntityAndOtherMemberIdentity(MemberEntity memberEntity, String otherMemberIdentity);
}
