package kr.co.popool.service;

import kr.co.popool.infra.error.exception.NotFoundException;
import kr.co.popool.persistence.entity.CareerEntity;
import kr.co.popool.persistence.repository.CareerRepository;
import kr.co.popool.service.model.dto.CareerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerService {

    private final CareerRepository careerRepository;

    @Transactional
    public void createCareer(CareerDto.CREATE create) {
        final CareerEntity careerEntity = CareerEntity.toCareerEntity(create);
        careerRepository.save(careerEntity);
    }

    public CareerDto.READ getCareer(String memberIdentity) {
        final CareerEntity careerEntity = careerRepository.findByMemberIdentity(memberIdentity)
                .orElseThrow(() -> new NotFoundException(memberIdentity + "회원의 이력서가 존재하지 않습니다."));

        return CareerEntity.toReadDto(careerEntity);
    }

    public List<CareerDto.READ> getAllCareer() {
        return careerRepository.findAll().stream().map(CareerEntity::toReadDto).collect(Collectors.toList());
    }

    @Transactional
    public void updateCareer(CareerDto.UPDATE careerDto) {
        CareerEntity careerEntity = careerRepository.findByMemberIdentity(careerDto.getMemberIdentity())
                .orElseThrow(() -> new NotFoundException(careerDto.getMemberIdentity() + "해당 아이디는 존재하지 않습니다."));

        careerEntity.updateCareer(careerDto);
        careerRepository.save(careerEntity);
    }

    @Transactional
    public void deleteCareer(CareerDto.DELETE careerDto) {
      CareerEntity careerEntity = careerRepository.findByMemberIdentity(careerDto.getMemberIdentity())
              .orElseThrow(() -> new NotFoundException(careerDto.getMemberIdentity()));
      careerRepository.save(careerEntity);
    }
}