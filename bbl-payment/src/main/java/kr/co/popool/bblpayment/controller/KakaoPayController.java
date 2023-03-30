package kr.co.popool.bblpayment.controller;

import kr.co.popool.bblpayment.infra.error.model.ResponseFormat;
import kr.co.popool.bblpayment.persistence.entity.payment.KakaoPayLogEntity;
import kr.co.popool.bblpayment.service.KakaoPayService;
import kr.co.popool.bblpayment.service.KakaoSubscribeService;
import kr.co.popool.bblpayment.service.model.dto.KakaoPayDto;
import kr.co.popool.bblpayment.service.model.dto.KakaoSubscribeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments/kakao")
@CrossOrigin(origins = "http://localhost:63342")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final KakaoSubscribeService kakaoSubscribeService;

    @PostMapping
    public ResponseFormat<KakaoPayDto.READY_RESPONSE> requestPayment(@RequestBody KakaoPayDto.ORDER request) {
        return ResponseFormat.ok(kakaoPayService.requestPayment(request));
    }

    @GetMapping("/success/{kakaoPayLogId}")
    public ResponseFormat<KakaoPayLogEntity> successPayment(@PathVariable("kakaoPayLogId") Long kakaoPayLogId,
                                                            @RequestParam("pg_token") String pgToken) {
        return ResponseFormat.ok(kakaoPayService.successPayment(kakaoPayLogId, pgToken));
    }

    @PostMapping("/subscription")
    public ResponseFormat<KakaoSubscribeDto.FIRST_READY_RESPONSE> requestSubscription(@RequestBody KakaoSubscribeDto.FIRST_ORDER requestDTO) {
        return ResponseFormat.ok(kakaoSubscribeService.requestSubscription(requestDTO));
    }

    @GetMapping("/subscription/success/{kakaoPayLogId}")
    public ResponseFormat<KakaoPayLogEntity> successSubscription(@PathVariable("kakaoPayLogId") Long kakaoPayLogId,
                                                                 @RequestParam("pg_token") String pgToken) {
        return ResponseFormat.ok(kakaoSubscribeService.successSubscription(kakaoPayLogId, pgToken));
    }
}
