package kr.co.popool.bblpayment.service;

import kr.co.popool.bblpayment.service.model.dto.CouponDto;
import kr.co.popool.bblpayment.persistence.entity.item.CouponEntity;
import kr.co.popool.bblpayment.infra.error.exception.NotFoundException;
import kr.co.popool.bblpayment.persistence.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void createCoupon(CouponDto.CREATE create) {
        CouponEntity newCoupon = CouponEntity.toCouponEntity(create);
        couponRepository.save(newCoupon);
    }

    @Transactional
    public void updateCoupon(CouponDto.UPDATE update) {
        CouponEntity findCoupon = couponRepository.findById(update.getCouponId())
                .orElseThrow(() -> new NotFoundException("Coupon"));

        findCoupon.update(update);
        couponRepository.save(findCoupon);
    }

    public CouponDto.READ readCoupon(Long couponId) {
        CouponEntity findCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("Coupon"));

        return CouponEntity.toCouponDto(findCoupon);
    }

    @Transactional
    public void deleteCoupon(Long couponId) {
        CouponEntity findCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("Coupon"));

        couponRepository.delete(findCoupon);
    }
}
