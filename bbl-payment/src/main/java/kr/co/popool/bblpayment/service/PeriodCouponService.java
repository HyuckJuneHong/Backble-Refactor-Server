package kr.co.popool.bblpayment.service;

import kr.co.popool.bblpayment.infra.error.exception.NotFoundException;
import kr.co.popool.bblpayment.persistence.entity.item.PeriodCouponEntity;
import kr.co.popool.bblpayment.persistence.repository.PeriodCouponRepository;
import kr.co.popool.bblpayment.service.model.dto.PeriodCouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PeriodCouponService {

    private final PeriodCouponRepository periodCouponRepository;

    @Transactional
    public void createPeriodCoupon(PeriodCouponDto.CREATE create) {
        final PeriodCouponEntity newPeriodCoupon = PeriodCouponEntity.toPeriodCouponEntity(create);
        periodCouponRepository.save(newPeriodCoupon);
    }

    @Transactional
    public void updatePeriodCoupon(PeriodCouponDto.UPDATE updateDTO) {
        PeriodCouponEntity findPeriodCoupon = periodCouponRepository.findById(updateDTO.getPeriodCouponId())
                .orElseThrow(() -> new NotFoundException("PeriodCoupon Not Found Update"));
        findPeriodCoupon.update(updateDTO);

        periodCouponRepository.save(findPeriodCoupon);
    }

    public PeriodCouponDto.READ readPeriodCoupon(Long periodCouponId) {
        final PeriodCouponEntity findPeriodCoupon = periodCouponRepository.findById(periodCouponId)
                .orElseThrow(() -> new NotFoundException("PeriodCoupon Not Found Read"));

        return PeriodCouponEntity.toPeriodCouponDto(findPeriodCoupon);
    }

    public List<PeriodCouponDto.READ> readAllCoupon(){
        return periodCouponRepository.findAll().stream()
                .map(PeriodCouponEntity::toPeriodCouponDto).collect(Collectors.toList());
    }

    @Transactional
    public void deletePeriodCoupon(Long periodCouponId) {
        final PeriodCouponEntity findPeriodCoupon = periodCouponRepository.findById(periodCouponId)
                .orElseThrow(() -> new NotFoundException("PeriodCoupon Not Found Delete"));
        periodCouponRepository.delete(findPeriodCoupon);
    }
}
