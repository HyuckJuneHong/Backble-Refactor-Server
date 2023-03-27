package kr.co.popool.persistence.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.popool.persistence.entity.QCareerEntity;
import kr.co.popool.persistence.entity.QScoreEntity;
import kr.co.popool.persistence.entity.ScoreEntity;
import kr.co.popool.persistence.repository.ScoreRepositoryCustom;
import kr.co.popool.service.model.dto.QQueryScoreDto_SHOWSCORE;
import kr.co.popool.service.model.dto.QueryScoreDto;
import kr.co.popool.service.model.dto.QueryScoreDto.SHOWSCORE;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static kr.co.popool.persistence.entity.QCareerEntity.careerEntity;
import static kr.co.popool.persistence.entity.QScoreEntity.scoreEntity;

@RequiredArgsConstructor
public class ScoreRepositoryImpl implements ScoreRepositoryCustom {


  private final JPAQueryFactory query;

  private final QCareerEntity qCareerEntity = careerEntity;

  private final QScoreEntity qScoreEntity = scoreEntity;

  @Override
  public Optional<List<SHOWSCORE>> showAllScores(String memberIdentity) {

    return Optional.ofNullable(query
        .select((
            new QQueryScoreDto_SHOWSCORE(
                qScoreEntity.attendance,
                qScoreEntity.sincerity,
                qScoreEntity.positiveness,
                qScoreEntity.technical,
                qScoreEntity.cooperative,
                qScoreEntity.evaluatorIdentity
            ))
        )
        .from(qScoreEntity)
        .where(qScoreEntity.del_yn.eq("N"))
        .join(qScoreEntity.careerEntity, qCareerEntity)
        .where(qCareerEntity.memberIdentity.eq(memberIdentity))
        .limit(1000)
        .fetch());
  }

  public Optional<List<ScoreEntity>> getAllScoreList(String memberIdentity) {

    return Optional.ofNullable(query
        .select(qScoreEntity
        )
        .from(qScoreEntity)
        .where(qScoreEntity.del_yn.eq("N"))
        .join(qScoreEntity.careerEntity, qCareerEntity)
        .where(qCareerEntity.memberIdentity.eq(memberIdentity))
        .limit(1000)
        .fetch());

  }

  public Optional<ScoreEntity> getScoreEntity(QueryScoreDto.DELETE deleteDto) {

    return Optional.ofNullable(query
        .select(qScoreEntity)
        .from(qScoreEntity)
        .join(qScoreEntity.careerEntity, qCareerEntity)
        .where(qScoreEntity.evaluatorIdentity.eq(deleteDto.getEvaluatorIdentity()))
        .where(qCareerEntity.memberIdentity.eq(deleteDto.getMemberIdentity()))
        .fetchOne());

  }
}


