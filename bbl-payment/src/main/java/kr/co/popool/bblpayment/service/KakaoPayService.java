package kr.co.popool.bblpayment.service;

import kr.co.popool.bblpayment.service.model.dto.KakaoPayDto;
import kr.co.popool.bblpayment.persistence.entity.item.CouponEntity;
import kr.co.popool.bblpayment.persistence.entity.item.InventoryEntity;
import kr.co.popool.bblpayment.persistence.entity.item.ItemMstEntity;
import kr.co.popool.bblpayment.persistence.entity.item.PeriodCouponEntity;
import kr.co.popool.bblpayment.persistence.entity.payment.KakaoPayLogEntity;
import kr.co.popool.bblpayment.infra.error.exception.NotFoundException;
import kr.co.popool.bblpayment.persistence.repository.InventoryRepository;
import kr.co.popool.bblpayment.persistence.repository.ItemRepository;
import kr.co.popool.bblpayment.persistence.repository.KakaoPayLogRepository;
import kr.co.popool.bblpayment.service.client.KakaoPayClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class KakaoPayService {

    private final KakaoPayClient kakaoPayClient;
    private final KakaoPayLogRepository kakaoPayLogRepository;
    private final ItemRepository<ItemMstEntity> itemRepository;
    private final InventoryRepository inventoryRepository;

    private final String ADMIN_KEY = "KakaoAK c3c1fcb48b30dfb68e37449cc31dffa3";

    private final String CID = "TC0ONETIME";

    private final String APPROVAL_URL = "http://localhost:8080/payments/kakao/success";
    private final String FAIL_URL = "http://localhost:8080/payments/kakao/fail";
    private final String CANCEL_URL = "http://localhost:8080/payments/kakao/cancel";

    @Transactional
    public void requestPayment(KakaoPayDto.ORDER order) {
        ItemMstEntity orderItem = itemRepository.findById(Long.parseLong(order.getItem_id()))
                .orElseThrow(() -> new NotFoundException("item"));

        final KakaoPayLogEntity kakaoPayLog = KakaoPayLogEntity.toKakaoPayLogEntity(order, orderItem);
        KakaoPayLogEntity savedKakaoPayLog = kakaoPayLogRepository.save(kakaoPayLog);

        KakaoPayDto.READY_REQUEST request = KakaoPayLogEntity.toReadyRequestDto(savedKakaoPayLog, CID, APPROVAL_URL, FAIL_URL, CANCEL_URL);
        KakaoPayDto.READY_RESPONSE response = kakaoPayClient.requestPayForReady(ADMIN_KEY, request);

        savedKakaoPayLog.setTid(response.getTid());
        kakaoPayLogRepository.save(savedKakaoPayLog);
    }

    @Transactional
    public void successPayment(Long kakaoPayLogId,
                               String pgToken) {
        KakaoPayLogEntity findKakaoPayLog = kakaoPayLogRepository.findById(kakaoPayLogId)
                .orElseThrow(() -> new NotFoundException("KakaoPaymentLog"));

        KakaoPayDto.APPROVAL_REQUEST request = KakaoPayLogEntity.toApprovalRequestDto(findKakaoPayLog, CID, pgToken);
        kakaoPayClient.requestPayForApproval(ADMIN_KEY, request);
        reflectToMemberInventory(findKakaoPayLog.getMemberId(), findKakaoPayLog.getItem());

        findKakaoPayLog.changePaymentStatusToSuccess();
        kakaoPayLogRepository.save(findKakaoPayLog);
    }

    private void reflectToMemberInventory(Long memberId,
                                          ItemMstEntity purchasedItem) {

        InventoryEntity buyerInventory = inventoryRepository.findInventoryEntityByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("inventory"));

        if (purchasedItem instanceof CouponEntity) {
            buyerInventory.addCoupon(((CouponEntity) purchasedItem).getAmount());
            inventoryRepository.save(buyerInventory);
            return;
        }

        if (purchasedItem instanceof PeriodCouponEntity) {
            buyerInventory.addPeriod(((PeriodCouponEntity) purchasedItem).getPeriod());
            inventoryRepository.save(buyerInventory);
            return;
        }
    }
}
