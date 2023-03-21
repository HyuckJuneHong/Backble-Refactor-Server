package kr.co.popool.bblpayment.persistence.entity.item;

import kr.co.popool.bblpayment.service.model.dto.CouponDto;
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
@DiscriminatorValue("C")
@Entity
public class CouponEntity extends ItemMstEntity {

    @Column(nullable = true)
    private int amount;

    @Builder
    public CouponEntity(int price,
                        String name,
                        int amount) {
        super(price, name);
        this.amount = amount;
    }

    public static CouponEntity toCouponEntity(CouponDto.CREATE create){
        return CouponEntity.builder()
                .name(create.getName())
                .price(create.getPrice())
                .amount(create.getAmount())
                .build();
    }

    public static CouponDto.READ toCouponDto(CouponEntity couponEntity){
        return CouponDto.READ.builder()
                .couponId(couponEntity.getId())
                .name(couponEntity.getName())
                .amount(couponEntity.getAmount())
                .price(couponEntity.getPrice())
                .build();
    }

    public void update(CouponDto.UPDATE update) {
        this.name = update.getName();
        this.price = update.getPrice();
        this.amount = update.getAmount();
    }

}
