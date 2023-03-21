package kr.co.popool.bblpayment.service.batch.subscription;

import kr.co.popool.bblpayment.service.model.dto.KakaoSubscribeDto;
import kr.co.popool.bblpayment.persistence.entity.item.ItemMstEntity;
import kr.co.popool.bblpayment.persistence.entity.payment.KakaoPayLogEntity;
import kr.co.popool.bblpayment.infra.error.exception.NotFoundException;
import kr.co.popool.bblpayment.persistence.repository.ItemRepository;
import kr.co.popool.bblpayment.persistence.repository.KakaoPayLogRepository;
import kr.co.popool.bblpayment.service.client.KakaoPayClient;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
public class PaySubscriptionMembersJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final KakaoPayClient kakaoPayClient;
    private final KakaoPayLogRepository kakaoPayLogRepository;
    private final ItemRepository<ItemMstEntity> itemRepository;

    private final int chunkSize = 10;
    private final String ADMIN_KEY = "KakaoAK c3c1fcb48b30dfb68e37449cc31dffa3";

    @Bean
    public Job paySubscriptionMembersJob() {
        return jobBuilderFactory.get("paySubscriptionMembersJob")
                .start(paySubscriptionMembersStep())
                .build();
    }

    @Bean
    public Step paySubscriptionMembersStep() {
        return stepBuilderFactory.get("paySubscriptionMembersStep")
                .<KakaoSubscribeDto.SUBSCRIPTION_PAYMENT_REQUEST, KakaoPayLogEntity> chunk(chunkSize)
                .reader(paySubscriptionMembersReader())
                .processor(paySubscriptionMembersProcessor())
                .writer(paySubscriptionMembersWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<KakaoSubscribeDto.SUBSCRIPTION_PAYMENT_REQUEST> paySubscriptionMembersReader() {
        return new JpaCursorItemReaderBuilder<KakaoSubscribeDto.SUBSCRIPTION_PAYMENT_REQUEST>()
                .name("paySubscriptionMembersReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(
                        "select" +
                        " new kr.co.popool.bblpayment.domain.dto.payment.KakaoSubscribeDTO.SUBSCRIPTION_PAYMENT_REQUEST(i.sid, 'null', i.memberId, s.name, '1', s.price, '0', '0')" +
                        " from InventoryEntity i " +
                        " join fetch i.subscribe s" +
                        " where i.sid is not null"
                )
                .build();
    }

    @Bean
    public ItemProcessor<KakaoSubscribeDto.SUBSCRIPTION_PAYMENT_REQUEST, KakaoPayLogEntity> paySubscriptionMembersProcessor() {
        return request -> {
            ItemMstEntity item = itemRepository.findById(Long.valueOf(request.getItemId()))
                    .orElseThrow(() -> new NotFoundException("item"));

            KakaoPayLogEntity kakaoPayLog = KakaoPayLogEntity.builder()
                    .quantity(Integer.valueOf(request.getQuantity()))
                    .totalAmount(Integer.valueOf(request.getTotal_amount()))
                    .itemName(request.getItem_name())
                    .memberId(Long.valueOf(request.getPartner_user_id()))
                    .item(item)
                    .build();

            KakaoPayLogEntity savedKakaoPayLog = kakaoPayLogRepository.save(kakaoPayLog);
            request.setPartner_order_id(String.valueOf(savedKakaoPayLog.getId()));

            KakaoSubscribeDto.SUBSCRIPTION_PAYMENT_RESPONSE response = kakaoPayClient.requestSubscriptionPayment(ADMIN_KEY, request);

            savedKakaoPayLog.setSid(response.getSid());
            savedKakaoPayLog.changePaymentStatusToSuccess();

            return savedKakaoPayLog;
        };
    }

    @Bean
    public JpaItemWriter<KakaoPayLogEntity> paySubscriptionMembersWriter() {
        return new JpaItemWriterBuilder<KakaoPayLogEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
