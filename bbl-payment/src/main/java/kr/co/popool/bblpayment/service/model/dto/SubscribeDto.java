package kr.co.popool.bblpayment.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SubscribeDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {
        private int price;
        private String name;
        private String payDatePerMonth;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {
        private Long subscribeId;
        private int price;
        private String name;
        private String payDatePerMonth;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {
        private Long subscribeId;
        private int price;
        private String name;
        private String payDatePerMonth;
    }
}
