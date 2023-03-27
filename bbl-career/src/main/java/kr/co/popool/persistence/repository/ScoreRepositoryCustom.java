package kr.co.popool.persistence.repository;

import kr.co.popool.persistence.entity.ScoreEntity;
import kr.co.popool.service.model.dto.QueryScoreDto;
import kr.co.popool.service.model.dto.QueryScoreDto.SHOWSCORE;

import java.util.List;
import java.util.Optional;

public interface ScoreRepositoryCustom {

  Optional<List<SHOWSCORE>> showAllScores(String memberIdentity);

  Optional<List<ScoreEntity>> getAllScoreList(String memberIdentity);

  Optional<ScoreEntity> getScoreEntity(QueryScoreDto.DELETE deleteDto);
}
