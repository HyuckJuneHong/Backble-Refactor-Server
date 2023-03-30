package kr.co.popool.bblpayment.service;

import kr.co.popool.bblpayment.infra.error.exception.NotFoundException;
import kr.co.popool.bblpayment.persistence.entity.item.SubscribeEntity;
import kr.co.popool.bblpayment.persistence.repository.SubscribeRepository;
import kr.co.popool.bblpayment.service.model.dto.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void createSubscribe(SubscribeDto.CREATE create) {
        SubscribeEntity newSubscribe = SubscribeEntity.toSubscribeEntity(create);
        subscribeRepository.save(newSubscribe);
    }

    @Transactional
    public void updateSubscribe(SubscribeDto.UPDATE updateDTO) {
        SubscribeEntity findSubscribe = subscribeRepository.findById(updateDTO.getSubscribeId())
                .orElseThrow(() -> new NotFoundException("Subscribe Not Found Update"));

        findSubscribe.update(updateDTO);
        subscribeRepository.save(findSubscribe);
    }

    public SubscribeDto.READ readSubscribe(Long subscribeId) {
        SubscribeEntity findSubscribe = subscribeRepository.findById(subscribeId)
                .orElseThrow(() -> new NotFoundException("Subscribe Not Found Read"));
        return SubscribeEntity.toSubscribeDto(findSubscribe);
    }

    @Transactional
    public void deleteSubscribe(Long subscribeId)  {
        SubscribeEntity findSubscribe = subscribeRepository.findById(subscribeId)
                .orElseThrow(() -> new NotFoundException("Subscribe Not Found Delete"));
        subscribeRepository.delete(findSubscribe);
    }
}
