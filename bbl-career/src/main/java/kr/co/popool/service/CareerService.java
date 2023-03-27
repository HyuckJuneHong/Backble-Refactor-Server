package kr.co.popool.service;

import kr.co.popool.infra.error.exception.NotFoundException;
import kr.co.popool.persistence.entity.CareerEntity;
import kr.co.popool.persistence.repository.CareerRepository;
import kr.co.popool.service.model.dto.CareerDto;
import kr.co.popool.service.model.dto.CareerDto.DELETE;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerService {

    private final CareerRepository careerRepository;

    public CareerDto.CAREERINFO getCareer(String memberIdentity) {
        final CareerEntity careerEntity = careerRepository.findByMemberIdentity(memberIdentity)
                .orElseThrow(() -> new NotFoundException(memberIdentity));

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
}