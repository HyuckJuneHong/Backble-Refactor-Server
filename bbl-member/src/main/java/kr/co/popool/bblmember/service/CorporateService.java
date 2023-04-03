package kr.co.popool.bblmember.service;

import kr.co.popool.bblmember.infra.error.exception.BadRequestException;
import kr.co.popool.bblmember.infra.interceptor.IdentityThreadLocal;
import kr.co.popool.bblmember.persistence.entity.CorporateEntity;
import kr.co.popool.bblmember.persistence.repository.CorporateRepository;
import kr.co.popool.bblmember.service.model.dtos.CorporateDto;
import kr.co.popool.bblmember.service.model.dtos.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorporateService {

    private final CorporateRepository corporateRepository;
    private final CommonService commonService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createCorporate(String userType,
                                CorporateDto.CREATE_CORPORATE create) {
        checkType(userType);

        commonService.checkIdentity(userType, create.getIdentity());
        commonService.checkPassword(create.getPassword(), create.getCheckPassword());

        final CorporateEntity corporateEntity = CorporateEntity.toCorporateEntity(create, passwordEncoder);
        corporateRepository.save(corporateEntity);
    }

    @Transactional
    public void updateCorporate(String userType,
                                CorporateDto.UPDATE_CORPORATE update) {
        checkType(userType);

        CorporateEntity corporateEntity = getThreadLocal();
        corporateEntity.corporateUpdate(update);
        corporateRepository.save(corporateEntity);
    }

    @Transactional
    public void updatePassword(String userType,
                               MemberDto.UPDATE_PASSWORD updatePassword) {
        checkType(userType);

        CorporateEntity corporateEntity = getThreadLocal();
        commonService.checkEncodePassword(updatePassword.getOriginalPassword(), corporateEntity.getPassword());
        commonService.checkPassword(updatePassword.getNewPassword(), updatePassword.getNewCheckPassword());

        corporateEntity.updatePassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        corporateRepository.save(corporateEntity);
    }

    public CorporateDto.READ_CORPORATE getCorporate(String userType) {
        checkType(userType);

        final CorporateEntity corporateEntity = getThreadLocal();
        return CorporateEntity.toCorporateDto(corporateEntity);
    }

    @Transactional
    public void deleteCorporate(String userType,
                                String password) {
        checkType(userType);

        CorporateEntity corporateEntity = getThreadLocal();
        commonService.checkEncodePassword(password, corporateEntity.getPassword());
        corporateRepository.delete(corporateEntity);
    }

    private CorporateEntity getThreadLocal() {
        String identity = IdentityThreadLocal.get();
        return corporateRepository.findByIdentity(identity)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 기업 회원입니다."));
    }

    private void checkType(String userType){
        if(!userType.equals("corporate")){
            throw new BadRequestException("Bad User Type");
        }
    }
}
