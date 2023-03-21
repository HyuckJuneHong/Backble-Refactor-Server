package kr.co.popool.bblmember.service;

import kr.co.popool.bblmember.infra.error.exception.BadRequestException;
import kr.co.popool.bblmember.infra.error.exception.DuplicatedException;
import kr.co.popool.bblmember.infra.error.model.ErrorCode;
import kr.co.popool.bblmember.infra.interceptor.IdentityThreadLocal;
import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import kr.co.popool.bblmember.persistence.repository.MemberRepository;
import kr.co.popool.bblmember.service.model.PhoneNumber;
import kr.co.popool.bblmember.service.model.dtos.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final CommonService commonService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createMember(MemberDto.CREATE_MEMBER create) {
        checkPhoneNumber(create.getPhone());

        commonService.checkIdentity("member", create.getIdentity());
        commonService.checkPassword(create.getPassword(), create.getCheckPassword());

        final MemberEntity memberEntity = MemberEntity.toMemberEntity(create, passwordEncoder);
        memberRepository.save(memberEntity);
    }

    @Transactional
    public void updateMember(MemberDto.UPDATE update) {
        MemberEntity memberEntity = getThreadLocal();

        checkUpdate(memberEntity, update);
        memberEntity.updateMember(update);

        memberRepository.save(memberEntity);
    }

    /**
     * 비밀번호 수정
     * @param updatePassword : 변경할 데이터
     */
    @Transactional
    public void updatePassword(MemberDto.UPDATE_PASSWORD updatePassword) {
        MemberEntity memberEntity = getThreadLocal();
        commonService.checkEncodePassword(updatePassword.getOriginalPassword(), memberEntity.getPassword());
        commonService.checkPassword(updatePassword.getNewPassword(), updatePassword.getNewCheckPassword());

        memberEntity.updatePassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        memberRepository.save(memberEntity);
    }

    /**
     * 회원 탈퇴
     * @param password : 비밀번호
     */
    @Transactional
    public void deleteMember(String password) {
        MemberEntity memberEntity = getThreadLocal();
        commonService.checkEncodePassword(password, memberEntity.getPassword());
        memberRepository.delete(memberEntity);
    }

    /**
     * 자기 자신 정보 조회
     * @return : 자신의 정보
     */
    public MemberDto.READ getMember() {
        final MemberEntity memberEntity = getThreadLocal();
        return MemberEntity.toMemberDto(memberEntity);
    }

    private MemberEntity getThreadLocal() {
        String identity = IdentityThreadLocal.get();
        return memberRepository.findByIdentity(identity)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 회원입니다."));
    }

    private void checkEmail(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    private void checkPhoneNumber(String phoneNumber) {
        if(memberRepository.existsByPhone(new PhoneNumber(phoneNumber))) {
            throw new DuplicatedException(ErrorCode.DUPLICATED_PHONE);
        }
    }

    private void checkUpdate(MemberEntity memberEntity,
                             MemberDto.UPDATE update){
        if(!memberEntity.getPhone().equals(new PhoneNumber(update.getPhoneNumber()))) {
            checkPhoneNumber(update.getPhoneNumber());
        }

        if(!memberEntity.getEmail().equals(update.getEmail())) {
            checkEmail(update.getEmail());
        }
    }

    private void checkType(String userType){
        if(!userType.equals("member")){
            throw new BadRequestException("Bad User Type");
        }
    }
}