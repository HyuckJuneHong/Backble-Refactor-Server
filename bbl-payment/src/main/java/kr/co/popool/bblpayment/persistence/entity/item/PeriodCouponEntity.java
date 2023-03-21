package kr.co.popool.bblpayment.persistence.entity.item;

import kr.co.popool.bblpayment.service.model.dto.PeriodCouponDto;
import kr.co.popool.bblpayment.service.model.enums.CouponPeriod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DiscriminatorValue("P")
@Entity
public class PeriodCouponEntity extends ItemMstEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CouponPeriod period;

    @Builder
    public PeriodCouponEntity(int price,
                              String name,
                              String period) {
        super(price, name);
        this.period = CouponPeriod.of(period);
    }

    public static PeriodCouponEntity toPeriodCouponEntity(PeriodCouponDto.CREATE create){
        return PeriodCouponEntity.builder()
                .name(create.getName())
                .price(create.getPrice())
                .period(create.getPeriod())
                .build();
    }

    public static PeriodCouponDto.READ toPeriodCouponDto(PeriodCouponEntity periodCouponEntity){
        return PeriodCouponDto.READ.builder()
                .periodCouponId(periodCouponEntity.getId())
                .name(periodCouponEntity.getName())
                .price(periodCouponEntity.getPrice())
                .period(periodCouponEntity.getPeriod().getPeriod_str())
                .build();
    }

    public void update(PeriodCouponDto.UPDATE update) {
        this.name = update.getName();
        this.price = update.getPrice();
        this.period = CouponPeriod.of(update.getPeriod());
    }
}
