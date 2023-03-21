package kr.co.popool.bblpayment.controller;

import kr.co.popool.bblpayment.infra.error.model.ResponseFormat;
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
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final KakaoSubscribeService kakaoSubscribeService;

    @PostMapping
    public ResponseFormat<KakaoPayDto.READY_RESPONSE> requestPayment(@RequestBody KakaoPayDto.ORDER request) {
        kakaoPayService.requestPayment(request);
        return ResponseFormat.ok();
    }

    @GetMapping("/success/{kakaoPayLogId}")
    public ResponseFormat successPayment(@PathVariable("kakaoPayLogId") Long kakaoPayLogId,
                                         @RequestParam("pg_token") String pgToken) {
        kakaoPayService.successPayment(kakaoPayLogId, pgToken);
        return ResponseFormat.ok();
    }

    @PostMapping("/subscription")
    public ResponseFormat<KakaoSubscribeDto.FIRST_READY_RESPONSE> requestSubscription(@RequestBody KakaoSubscribeDto.FIRST_ORDER requestDTO) {
        kakaoSubscribeService.requestSubscription(requestDTO);
        return ResponseFormat.ok();
    }

    @GetMapping("/subscription/success/{kakaoPayLogId}")
    public ResponseFormat successSubscription(@PathVariable("kakaoPayLogId") Long kakaoPayLogId,
                                              @RequestParam("pg_token") String pgToken) {
        kakaoSubscribeService.successSubscription(kakaoPayLogId, pgToken);
        return ResponseFormat.ok();
    }
}
