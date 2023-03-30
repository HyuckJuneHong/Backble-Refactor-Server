package kr.co.popool.bblpayment.service;

import kr.co.popool.bblpayment.infra.error.exception.NotFoundException;
import kr.co.popool.bblpayment.persistence.entity.item.CouponEntity;
import kr.co.popool.bblpayment.persistence.repository.CouponRepository;
import kr.co.popool.bblpayment.service.model.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void createCoupon(CouponDto.CREATE create) {
        final CouponEntity newCoupon = CouponEntity.toCouponEntity(create);
        couponRepository.save(newCoupon);
    }

    @Transactional
    public void updateCoupon(CouponDto.UPDATE update) {
        CouponEntity findCoupon = couponRepository.findById(update.getCouponId())
                .orElseThrow(() -> new NotFoundException("Coupon Not Fount Update"));

        findCoupon.update(update);
        couponRepository.save(findCoupon);
    }

    public CouponDto.READ readCoupon(Long couponId) {
        final CouponEntity findCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("Coupon Not Fount Read"));

        return CouponEntity.toCouponDto(findCoupon);
    }

    public List<CouponDto.READ> readAllCoupon(){
        return couponRepository.findAll().stream()
                .map(CouponEntity::toCouponDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteCoupon(Long couponId) {
        final  CouponEntity findCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("Coupon Not Fount Delete"));

        couponRepository.delete(findCoupon);
    }
}
