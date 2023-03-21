package kr.co.popool.bblpayment.service.batch;

import kr.co.popool.bblpayment.service.batch.subscription.PaySubscriptionMembersJobConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JobScheduler {

    private final JobLauncher jobLauncher;
    private final PaySubscriptionMembersJobConfig paySubscriptionMembersJobConfig;

    public void runPaySubscriptionMembersJob() {
        try {
            jobLauncher.run(paySubscriptionMembersJobConfig.paySubscriptionMembersJob(), null);
        } catch (Exception e) {

        }
    }
}
