package kr.co.popool.bblpayment.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ItemDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {
        private Long itemId;
        private int price;
        private String name;
        private int amount;
        private String period;
        private LocalDate payDatePerMonth;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {
        private int price;
        private String name;
        private int amount;
        private String period;
        private LocalDate payDatePerMonth;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DETAIL {
        private Long itemId;
        private int price;
        private String name;
        private int amount;
        private String period;
        private LocalDate payDatePerMonth;
    }
}
