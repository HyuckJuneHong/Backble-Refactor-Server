package kr.co.popool.bblpayment.controller;

import kr.co.popool.bblpayment.infra.error.model.ResponseFormat;
import kr.co.popool.bblpayment.service.SubscribeService;
import kr.co.popool.bblpayment.service.model.dto.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items/subscribe")
@CrossOrigin(origins = "http://localhost:63342")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping
    public ResponseFormat createCoupon(@RequestBody SubscribeDto.CREATE create) {
        subscribeService.createSubscribe(create);
        return ResponseFormat.ok();
    }

    @GetMapping("/all-subscribe/{subscribeId}")
    public ResponseFormat<SubscribeDto.READ> readCoupon(@PathVariable("subscribeId") Long subscribeId) {
        return ResponseFormat.ok(subscribeService.readSubscribe(subscribeId));
    }

    @GetMapping("/all-subscribe")
    public ResponseFormat<List<SubscribeDto.READ>> readCoupon() {
        return ResponseFormat.ok(subscribeService.readAllCoupon());
    }

    @PutMapping
    public ResponseFormat updateCoupon(@RequestBody SubscribeDto.UPDATE updateDTO) {
        subscribeService.updateSubscribe(updateDTO);
        return ResponseFormat.ok();
    }

    @DeleteMapping("/{subscribeId}")
    public ResponseFormat deleteCoupon(@PathVariable("subscribeId") Long subscribeId) {
        subscribeService.deleteSubscribe(subscribeId);
        return ResponseFormat.ok();
    }
}
