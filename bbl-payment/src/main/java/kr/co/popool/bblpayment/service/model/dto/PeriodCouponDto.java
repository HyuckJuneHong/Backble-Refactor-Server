package kr.co.popool.bblpayment.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PeriodCouponDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {
        private int price;
        private String name;
        private String period;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {
        private Long periodCouponId;
        private int price;
        private String name;
        private String period;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {
        private Long periodCouponId;
        private int price;
        private String name;
        private String period;
    }
}
