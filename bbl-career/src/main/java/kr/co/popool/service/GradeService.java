package kr.co.popool.service;

import kr.co.popool.infra.error.exception.NotFoundException;
import kr.co.popool.persistence.entity.CareerEntity;
import kr.co.popool.persistence.entity.GradeEntity;
import kr.co.popool.persistence.entity.ScoreEntity;
import kr.co.popool.persistence.repository.CareerRepository;
import kr.co.popool.persistence.repository.GradeRepository;
import kr.co.popool.service.model.dto.GradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeService {

    private final GradeRepository gradeRepository;
    private final CareerRepository careerRepository;

    public GradeDto.GRADEDETAIL getGrade(String memberIdentity) {
        return gradeRepository.showGradeDetail(memberIdentity)
                .orElseThrow(() -> new NotFoundException("해당 아이디는 등급이 없습니다"));
    }

    @Transactional
    public void saveGradeEntity(List<ScoreEntity> allScore,
                                GradeDto.GRADEDETAIL gradeDetail,
                                String memberIdentity) {
        try {
            updateGrade(allScore, memberIdentity, gradeDetail);
        } catch (NullPointerException e) {
            GradeEntity gradeEntity = GradeEntity.toGradeEntity(allScore, gradeDetail);
            gradeRepository.save(gradeEntity);

            saveGrade(memberIdentity, gradeEntity);
        }
    }

    /**
     * 최초 평가 등록 시, 인사 - 등급 매핑
     */
    @Transactional
    public void saveGrade(String memberIdentity,
                          GradeEntity gradeEntity) {
        CareerEntity careerEntity = careerRepository.findByMemberIdentity(memberIdentity)
                .orElseThrow(() -> new NotFoundException(memberIdentity));
        careerEntity.createGrade(gradeEntity);
        careerRepository.save(careerEntity);
    }

    /**
     * 평가 등록 시, 인사 - 등급 매핑
     */
    @Transactional
    public void updateGrade(List<ScoreEntity> allScore,
                            String memberIdentity,
                            GradeDto.GRADEDETAIL gradeDto) {
        CareerEntity careerEntity = careerRepository.findByMemberIdentity(memberIdentity)
                .orElseThrow(() -> new NotFoundException(memberIdentity));
        careerEntity.getGradeEntity().updateGrade(allScore, gradeDto);
        careerRepository.save(careerEntity);
    }
}
