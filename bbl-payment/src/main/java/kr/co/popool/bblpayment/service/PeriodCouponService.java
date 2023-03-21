package kr.co.popool.bblpayment.service;

import kr.co.popool.bblpayment.service.model.dto.PeriodCouponDto;
import kr.co.popool.bblpayment.persistence.entity.item.PeriodCouponEntity;
import kr.co.popool.bblpayment.infra.error.exception.NotFoundException;
import kr.co.popool.bblpayment.persistence.repository.PeriodCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new NotFoundException("PeriodCoupon"));
        findPeriodCoupon.update(updateDTO);

        periodCouponRepository.save(findPeriodCoupon);
    }

    public PeriodCouponDto.READ readPeriodCouponDetail(Long periodCouponId) {
        final PeriodCouponEntity findPeriodCoupon = periodCouponRepository.findById(periodCouponId)
                .orElseThrow(() -> new NotFoundException("PeriodCoupon"));

        return PeriodCouponEntity.toPeriodCouponDto(findPeriodCoupon);
    }

    @Transactional
    public void deletePeriodCoupon(Long periodCouponId) {
        final PeriodCouponEntity findPeriodCoupon = periodCouponRepository.findById(periodCouponId)
                .orElseThrow(() -> new NotFoundException("PeriodCoupon"));
        periodCouponRepository.delete(findPeriodCoupon);
    }
}
