package kr.co.popool.bblpayment.controller;

import kr.co.popool.bblpayment.infra.error.model.ResponseFormat;
import kr.co.popool.bblpayment.service.PeriodCouponService;
import kr.co.popool.bblpayment.service.model.dto.CouponDto;
import kr.co.popool.bblpayment.service.model.dto.PeriodCouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items/periodCoupon")
public class PeriodCouponController {

    private final PeriodCouponService periodCouponService;

    @PostMapping
    public ResponseFormat createCoupon(@RequestBody PeriodCouponDto.CREATE createDTO) {
        periodCouponService.createPeriodCoupon(createDTO);
        return ResponseFormat.ok();
    }

    @GetMapping("/{periodCouponId}")
    public ResponseFormat<CouponDto.READ> readCoupon(@PathVariable("periodCouponId") Long periodCouponId) {
        return ResponseFormat.ok(periodCouponService.readPeriodCoupon(periodCouponId));
    }

    @PutMapping
    public ResponseFormat updateCoupon(@RequestBody PeriodCouponDto.UPDATE updateDTO) {
        periodCouponService.updatePeriodCoupon(updateDTO);
        return ResponseFormat.ok();
    }

    @DeleteMapping("/{periodCouponId}")
    public ResponseFormat deleteCoupon(@PathVariable("periodCouponId") Long periodCouponId) {
        periodCouponService.deletePeriodCoupon(periodCouponId);
        return ResponseFormat.ok();
    }
}
