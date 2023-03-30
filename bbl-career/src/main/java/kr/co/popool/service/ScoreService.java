package kr.co.popool.service;

import kr.co.popool.infra.error.exception.BadRequestException;
import kr.co.popool.infra.error.exception.NotFoundException;
import kr.co.popool.persistence.entity.CareerEntity;
import kr.co.popool.persistence.entity.GradeEntity;
import kr.co.popool.persistence.entity.ScoreEntity;
import kr.co.popool.persistence.repository.CareerRepository;
import kr.co.popool.persistence.repository.GradeRepository;
import kr.co.popool.persistence.repository.ScoreRepository;
import kr.co.popool.service.model.dto.GradeDto;
import kr.co.popool.service.model.dto.QueryScoreDto;
import kr.co.popool.service.model.dto.QueryScoreDto.SHOWSCORE;
import kr.co.popool.service.model.dto.ScoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public void createScore(ScoreDto.CREATE create) {
        GradeEntity gradeEntity = firstScore(create.getMemberIdentity());

        scoreRepository.save(ScoreEntity.toScoreEntity(newScore, careerEntity));

        GradeDto.GETVALUE valueDto = gradeRepository.getValue(memberIdentity);
        GradeDto.GRADEDETAIL gradeDetail = gradeRepository.makeGradeDto(memberIdentity, valueDto)
                .orElseThrow((() -> new BadRequestException("최종 등급 DTO 생성 실패")));

        gradeService.saveGradeEntity(findAllScore(memberIdentity), gradeDetail, memberIdentity);
    }

    private GradeEntity firstScore(String memberIdentity){
        return !gradeRepository.existsByMemberIdentity(memberIdentity) ? GradeEntity.toFirstGradeEntity(memberIdentity) : null;
    }

    public List<SHOWSCORE> getScores(String memberIdentity) {
        return scoreRepository.showAllScores(memberIdentity)
                .orElseThrow(() -> new NotFoundException("memberIdentity"));
    }

    /**
     * 평가 수정
     */
    @Transactional
    public void updateScore(ScoreDto.UPDATE updateScoreDto) {
        ScoreEntity scoreEntity = scoreRepository.findByEvaluatorIdentity(updateScoreDto.getEvaluatorIdentity())
                .orElseThrow(() -> new BadRequestException("평가 수정 실패 - 아이디에 해당하는 인사 내역이 존재하지 않습니다"));

        scoreEntity.updateScore(updateScoreDto);
        scoreRepository.save(scoreEntity);
    }

    /**
     * 모든 평가 조회
     */
    public List<ScoreEntity> findAllScore(String memberIdentity) {
        return scoreRepository.getAllScoreList(memberIdentity)
                .orElseThrow(() -> new NotFoundException("아이디에 해당하는 평가 내역이 존재하지 않습니다"));
    }

    @Transactional
    public void deleteScore(QueryScoreDto.DELETE deleteDto) {
        final ScoreEntity scoreEntity = scoreRepository.getScoreEntity(deleteDto)
                .orElseThrow(() -> new NotFoundException("아이디에 해당하는 평가 내역이 존재하지 않습니다"));

        scoreRepository.delete(scoreEntity);

        GradeDto.GETVALUE valueDto = gradeRepository.getValue(deleteDto.getMemberIdentity());
        GradeDto.GRADEDETAIL gradeDetail = gradeRepository.makeGradeDto(deleteDto.getMemberIdentity(), valueDto)
                .orElseThrow((() -> new BadRequestException("최종 등급 DTO 생성 실패")));

        gradeService.saveGradeEntity(findAllScore(deleteDto.getMemberIdentity()), gradeDetail, deleteDto.getMemberIdentity());
    }
}



