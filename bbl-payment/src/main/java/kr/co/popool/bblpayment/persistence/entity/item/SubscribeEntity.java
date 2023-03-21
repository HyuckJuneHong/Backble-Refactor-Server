package kr.co.popool.bblpayment.persistence.entity.item;

import kr.co.popool.bblpayment.service.model.dto.SubscribeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@DiscriminatorValue("S")
@Entity
public class SubscribeEntity extends ItemMstEntity{

    @Column(nullable = true)
    private LocalDate payDatePerMonth;

    @Builder
    public SubscribeEntity(int price,
                           String name,
                           LocalDate payDatePerMonth) {
        super(price, name);
        this.payDatePerMonth = payDatePerMonth;
    }

    public static SubscribeEntity toSubscribeEntity(SubscribeDto.CREATE create){
        return SubscribeEntity.builder()
                .name(create.getName())
                .price(create.getPrice())
                .payDatePerMonth(LocalDate.parse(create.getPayDatePerMonth()))
                .build();
    }

    public static SubscribeDto.READ toSubscribeDto(SubscribeEntity subscribeEntity){
        return SubscribeDto.READ.builder()
                .subscribeId(subscribeEntity.getId())
                .name(subscribeEntity.getName())
                .price(subscribeEntity.getPrice())
                .payDatePerMonth(subscribeEntity.getPayDatePerMonth().toString())
                .build();
    }

    public void update(SubscribeDto.UPDATE update) {
        this.name = update.getName();
        this.price = update.getPrice();
        this.payDatePerMonth = LocalDate.parse(update.getPayDatePerMonth());
    }
}
