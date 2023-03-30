package kr.co.popool.bblpayment.service;

import kr.co.popool.bblpayment.service.model.dto.KakaoSubscribeDto;
import kr.co.popool.bblpayment.persistence.entity.item.InventoryEntity;
import kr.co.popool.bblpayment.persistence.entity.item.ItemMstEntity;
import kr.co.popool.bblpayment.persistence.entity.item.SubscribeEntity;
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
public class KakaoSubscribeService {

    private final KakaoPayClient kakaoPayClient;
    private final KakaoPayLogRepository kakaoPayLogRepository;
    private final ItemRepository<ItemMstEntity> itemRepository;
    private final InventoryRepository inventoryRepository;

    private final String ADMIN_KEY = "KakaoAK c3c1fcb48b30dfb68e37449cc31dffa3";

    private final String CID = "TCSUBSCRIP";

    private final String APPROVAL_URL = "http://localhost:8080/payments/kakao/subscription/success";
    private final String FAIL_URL = "http://localhost:8080/payments/kakao/subscription/fail";
    private final String CANCEL_URL = "http://localhost:8080/payments/kakao/subscription/cancel";


    @Transactional
    public KakaoSubscribeDto.FIRST_READY_RESPONSE requestSubscription(KakaoSubscribeDto.FIRST_ORDER orderDTO) {

        ItemMstEntity orderItem = itemRepository.findById(Long.parseLong(orderDTO.getItem_id()))
                .orElseThrow(() -> new NotFoundException("item Not Found Request Subscription"));

        KakaoPayLogEntity kakaoPayLog = KakaoPayLogEntity.builder()
                .memberId(Long.parseLong(orderDTO.getPartner_user_id()))
                .item(orderItem)
                .itemName(orderDTO.getItem_name())
                .totalAmount(Integer.parseInt(orderDTO.getTotal_amount()))
                .quantity(Integer.parseInt(orderDTO.getQuantity()))
                .build();

        KakaoPayLogEntity savedKakaoPayLog = kakaoPayLogRepository.save(kakaoPayLog);

        KakaoSubscribeDto.FIRST_READY_REQUEST requestDTO = KakaoSubscribeDto.FIRST_READY_REQUEST.builder()
                .cid(CID)
                .partner_user_id(String.valueOf(savedKakaoPayLog.getMemberId()))
                .partner_order_id(String.valueOf(savedKakaoPayLog.getId()))
                .item_name(savedKakaoPayLog.getItemName())
                .quantity(String.valueOf(savedKakaoPayLog.getQuantity()))
                .tax_free_amount(String.valueOf(savedKakaoPayLog.getTaxFreeAmount()))
                .total_amount(String.valueOf(savedKakaoPayLog.getTotalAmount()))
                .approval_url(APPROVAL_URL + "/" + savedKakaoPayLog.getId())
                .fail_url(FAIL_URL + "/" + savedKakaoPayLog.getId())
                .cancel_url(CANCEL_URL + "/" + savedKakaoPayLog.getId())
                .build();

        KakaoSubscribeDto.FIRST_READY_RESPONSE responseDTO = kakaoPayClient.requestFirstSubscribeForReady(ADMIN_KEY, requestDTO);

        savedKakaoPayLog.setTid(responseDTO.getTid());

        return responseDTO;
    }

    @Transactional
    public KakaoPayLogEntity successSubscription(Long kakaoPayLogId,
                                                 String pgToken) {

        KakaoPayLogEntity findKakaoPayLog = kakaoPayLogRepository.findById(kakaoPayLogId)
                .orElseThrow(() -> new NotFoundException("KakaoPaymentLog Not Found Success Subscription"));

        KakaoSubscribeDto.FIRST_APPROVAL_REQUEST requestDTO = KakaoSubscribeDto.FIRST_APPROVAL_REQUEST.builder()
                .cid(CID)
                .tid(findKakaoPayLog.getTid())
                .partner_user_id(String.valueOf(findKakaoPayLog.getMemberId()))
                .partner_order_id(String.valueOf(findKakaoPayLog.getId()))
                .pg_token(pgToken)
                .build();
        KakaoSubscribeDto.FIRST_APPROVAL_RESPONSE responseDTO = null;

        try {
            responseDTO = kakaoPayClient.requestFirstSubscribeForApproval(ADMIN_KEY, requestDTO);
        }catch (Exception e){
            return findKakaoPayLog;
        }

        InventoryEntity memberInventory = inventoryRepository.findInventoryEntityByMemberId(findKakaoPayLog.getMemberId())
                .orElseThrow(() -> new NotFoundException("inventory Not Found"));

        memberInventory.doSubscription((SubscribeEntity) findKakaoPayLog.getItem(), responseDTO.getSid());

        findKakaoPayLog.setSid(responseDTO.getSid());
        findKakaoPayLog.changePaymentStatusToSuccess();

        return findKakaoPayLog;
    }

}
