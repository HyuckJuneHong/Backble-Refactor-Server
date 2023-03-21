package kr.co.popool.service;

import kr.co.popool.infra.error.exception.NotFoundException;
import kr.co.popool.persistence.entity.CareerEntity;
import kr.co.popool.persistence.entity.GradeEntity;
import kr.co.popool.persistence.entity.ScoreEntity;
import kr.co.popool.persistence.repository.CareerRepository;
import kr.co.popool.service.model.dto.CareerDto;
import kr.co.popool.service.model.dto.CareerDto.CAREERINFO;
import kr.co.popool.service.model.dto.CareerDto.DELETE;
import kr.co.popool.service.model.dto.QueryGradeDto.GRADEDETAIL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerService {

    private final CareerRepository careerRepository;
    private final ScoreService scoreService;

    public CareerDto.CAREERINFO getCareer(String memberIdentity) {
        final CareerEntity careerEntity = careerRepository.findByMemberIdentity(memberIdentity)
                .orElseThrow(() -> new NotFoundException(memberIdentity));
        return checkGrade(careerEntity);
    }

    public CAREERINFO checkGrade(CareerEntity careerEntity) {
        try {
            return CareerEntity.toCareerInfoDto(careerEntity);
        } catch (NullPointerException e) {
            return CareerEntity.toNoneGradeDto(careerEntity);
        }
    }

    @Transactional
    public void createCareer(CareerDto.CREATE create) {
          final CareerEntity careerEntity = CareerEntity.toCareerEntity(create);
          careerRepository.save(careerEntity);
    }

    @Transactional
    public void updateCareer(CareerDto.UPDATE careerDto) {
        CareerEntity careerEntity = careerRepository.findByMemberIdentity(careerDto.getMemberIdentity())
                .orElseThrow(() -> new NotFoundException(careerDto.getMemberIdentity()));

        careerEntity.updateCareer(careerDto);
        careerRepository.save(careerEntity);
    }

    @Transactional
    public void deleteCareer(DELETE careerDto) {
      CareerEntity careerEntity = careerRepository.findByMemberIdentity(careerDto.getMemberIdentity())
              .orElseThrow(() -> new NotFoundException(careerDto.getMemberIdentity()));
      careerRepository.save(careerEntity);
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
    public void updateGrade(String memberIdentity, GRADEDETAIL gradeDto) {
      CareerEntity careerEntity = careerRepository.findByMemberIdentity(memberIdentity)
              .orElseThrow(() -> new NotFoundException(memberIdentity));
      careerEntity.getGradeEntity().updateGrade(findScoreList(memberIdentity), gradeDto);
      careerRepository.save(careerEntity);
    }

    /**
     * 인사 객체와 매핑된 성적 entity 가져오기
     */
    public List<ScoreEntity> findScoreList(String memberIdentity) {
        return scoreService.findAllScore(memberIdentity);
    }
}