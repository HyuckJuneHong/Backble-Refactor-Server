package kr.co.popool.bblpayment.controller;

import kr.co.popool.bblpayment.infra.error.model.ResponseFormat;
import kr.co.popool.bblpayment.service.CouponService;
import kr.co.popool.bblpayment.service.model.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/items/coupon")
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseFormat createCoupon(@RequestBody CouponDto.CREATE createDTO) {
        couponService.createCoupon(createDTO);
        return ResponseFormat.ok();
    }

    @GetMapping("/{couponId}")
    public ResponseFormat<CouponDto.READ> readCoupon(@PathVariable("couponId") Long couponId) {
        return ResponseFormat.ok(couponService.readCoupon(couponId));
    }

    @PutMapping
    public ResponseFormat updateCoupon(@RequestBody CouponDto.UPDATE updateDTO) {
        couponService.updateCoupon(updateDTO);
        return ResponseFormat.ok();
    }

    @DeleteMapping("/{couponId}")
    public ResponseFormat deleteCoupon(@PathVariable("couponId") Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseFormat.ok();
    }
}
