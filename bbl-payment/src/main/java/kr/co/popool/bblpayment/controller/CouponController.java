package kr.co.popool.bblpayment.controller;

import kr.co.popool.bblpayment.infra.error.model.ResponseFormat;
import kr.co.popool.bblpayment.service.CouponService;
import kr.co.popool.bblpayment.service.model.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/items/coupon")
@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseFormat createCoupon(@RequestBody CouponDto.CREATE createDTO) {
        couponService.createCoupon(createDTO);
        return ResponseFormat.ok();
    }

    @GetMapping("/all/{couponId}")
    public ResponseFormat<CouponDto.READ> readCoupon(@PathVariable("couponId") Long couponId) {
        return ResponseFormat.ok(couponService.readCoupon(couponId));
    }

    @GetMapping("/all")
    public ResponseFormat<List<CouponDto.READ>> readCoupon() {
        return ResponseFormat.ok(couponService.readAllCoupon());
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
