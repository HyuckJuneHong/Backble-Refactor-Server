package kr.co.popool.bblpayment.service.client;

import kr.co.popool.bblpayment.service.model.dto.KakaoPayDto;
import kr.co.popool.bblpayment.service.model.dto.KakaoSubscribeDto;
import kr.co.popool.bblpayment.infra.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-pay", url = "https://kapi.kakao.com", configuration = {FeignClientConfig.class})
public interface KakaoPayClient {
    @PostMapping(value = "/v1/payment/ready", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    KakaoPayDto.READY_RESPONSE requestPayForReady(@RequestHeader("Authorization") String adminKey,
                                                  @RequestBody KakaoPayDto.READY_REQUEST request);

    @PostMapping(value = "/v1/payment/approve", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    KakaoPayDto.APPROVAL_RESPONSE requestPayForApproval(@RequestHeader("Authorization") String adminKey,
                                                        @RequestBody KakaoPayDto.APPROVAL_REQUEST request);

    @PostMapping(value = "/v1/payment/ready", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    KakaoSubscribeDto.FIRST_READY_RESPONSE requestFirstSubscribeForReady(@RequestHeader("Authorization") String adminKey,
                                                                         @RequestBody KakaoSubscribeDto.FIRST_READY_REQUEST request);

    @PostMapping(value = "/v1/payment/approve", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    KakaoSubscribeDto.FIRST_APPROVAL_RESPONSE requestFirstSubscribeForApproval(@RequestHeader("Authorization") String adminKey,
                                                                               @RequestBody KakaoSubscribeDto.FIRST_APPROVAL_REQUEST request);

    @PostMapping(value = "/v1/payment/approve", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    KakaoSubscribeDto.SUBSCRIPTION_PAYMENT_RESPONSE requestSubscriptionPayment(@RequestHeader("Authorization") String adminKey,
                                                                               @RequestBody KakaoSubscribeDto.SUBSCRIPTION_PAYMENT_REQUEST request);
}
