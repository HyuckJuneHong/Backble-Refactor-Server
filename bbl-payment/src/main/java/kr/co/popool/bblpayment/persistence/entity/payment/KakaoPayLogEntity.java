package kr.co.popool.bblpayment.persistence.entity.payment;

import kr.co.popool.bblpayment.persistence.entity.item.ItemMstEntity;
import kr.co.popool.bblpayment.service.model.dto.KakaoPayDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@ToString
@Getter
@NoArgsConstructor
@DiscriminatorValue("Kakao")
@Entity
public class KakaoPayLogEntity extends PaymentLogMstEntity {

    @Column
    private String tid;

    @Column
    private String sid;

    @Column(nullable = false)
    private String itemName;

    @Builder
    public KakaoPayLogEntity(Long memberId,
                             ItemMstEntity item,
                             int quantity,
                             int totalAmount,
                             String itemName) {
        super(memberId, item, quantity, totalAmount);
        this.itemName = itemName;
    }

    public static KakaoPayLogEntity toKakaoPayLogEntity(KakaoPayDto.ORDER order,
                                                 ItemMstEntity orderItem){
        return KakaoPayLogEntity.builder()
                .memberId(Long.parseLong(order.getPartner_user_id()))
                .item(orderItem)
                .itemName(order.getItem_name())
                .totalAmount(Integer.parseInt(order.getTotal_amount()))
                .quantity(Integer.parseInt(order.getQuantity()))
                .build();
    }

    public static KakaoPayDto.READY_REQUEST toReadyRequestDto(KakaoPayLogEntity kakaoPayLog,
                                                              String CID,
                                                              String APPROVAL_URL,
                                                              String FAIL_URL,
                                                              String CANCEL_URL){
        return KakaoPayDto.READY_REQUEST.builder()
                .cid(CID)
                .partner_user_id(String.valueOf(kakaoPayLog.getMemberId()))
                .partner_order_id(String.valueOf(kakaoPayLog.getId()))
                .item_name(kakaoPayLog.getItemName())
                .quantity(String.valueOf(kakaoPayLog.getQuantity()))
                .tax_free_amount(String.valueOf(kakaoPayLog.getTaxFreeAmount()))
                .total_amount(String.valueOf(kakaoPayLog.getTotalAmount()))
                .approval_url(APPROVAL_URL + "/" + kakaoPayLog.getId())
                .fail_url(FAIL_URL + "/" + kakaoPayLog.getId())
                .cancel_url(CANCEL_URL + "/" + kakaoPayLog.getId())
                .build();
    }

    public static KakaoPayDto.APPROVAL_REQUEST toApprovalRequestDto(KakaoPayLogEntity kakaoPayLog,
                                                                    String CID,
                                                                    String pgToken){
        return KakaoPayDto.APPROVAL_REQUEST.builder()
                .cid(CID)
                .tid(kakaoPayLog.getTid())
                .partner_user_id(String.valueOf(kakaoPayLog.getMemberId()))
                .partner_order_id(String.valueOf(kakaoPayLog.getId()))
                .pg_token(pgToken)
                .build();
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
