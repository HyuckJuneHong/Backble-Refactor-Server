package kr.co.popool.bblpayment.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PaymentDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class REQUEST {
        private Long memberId;
        private Long itemId;
        private int quantity;
        private int totalAmount;
        private int taxFreeAmount;
        private String paymentStatus;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DETAIL {
        private String itemName;
        private int itemPrice;
        private int quantity;
        private int totalAmount;
        private int taxFreeAmount;
        private String paymentStatus;
        private String paymentDate;
    }
}
