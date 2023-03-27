package kr.co.popool.bblmember.service;

import kr.co.popool.bblmember.infra.error.exception.BadRequestException;
import kr.co.popool.bblmember.infra.error.exception.BusinessLogicException;
import kr.co.popool.bblmember.infra.error.exception.DuplicatedException;
import kr.co.popool.bblmember.infra.error.model.ErrorCode;
import kr.co.popool.bblmember.persistence.entity.CorporateEntity;
import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import kr.co.popool.bblmember.persistence.repository.CorporateRepository;
import kr.co.popool.bblmember.persistence.repository.MemberRepository;
import kr.co.popool.bblmember.service.model.dtos.MemberDto;
import kr.co.popool.bblmember.service.model.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonService {
    private final MemberRepository memberRepository;
    private final CorporateRepository corporateRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final JwtCustomService jwtCustomService;

    @Transactional
    public MemberDto.TOKEN login(String userType,
                                 MemberDto.LOGIN login) {
        if(userType.equals("member")){
            return memberLogin(login);
        }else if(userType.equals("corporate")){
            return corporateLogin(login);
        }else{
            throw new BadRequestException("Bad User Type Login");
        }
    }

    public void checkIdentity(String userType,
                              String identity) {
        if(userType.equals("member")){
            if (memberRepository.existsByIdentity(identity)) {
                throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
            }
        }else if(userType.equals("corporate")){
            if(corporateRepository.existsByIdentity(identity)){
                throw new DuplicatedException(ErrorCode.DUPLICATED_ID);
            }
        }else{
            throw new BadRequestException("잘못된 유저 타입");
        }
    }

    public void checkPassword(String password,
                              String checkPassword) {
        if(!password.equals(checkPassword)) {
            throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
        }
    }

    public void checkEncodePassword(String password,
                                    String encodePassword) {
        if(!passwordEncoder.matches(password, encodePassword)) {
            throw new BusinessLogicException(ErrorCode.WRONG_PASSWORD);
        }
    }

    private MemberDto.TOKEN memberLogin(MemberDto.LOGIN login){
        MemberEntity memberEntity = memberRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BadRequestException("아이디나 비밀번호를 다시 확인해주세요"));
        checkEncodePassword(login.getPassword(), memberEntity.getPassword());

        String[] tokens = generateToken(memberEntity.getIdentity(), memberEntity.getMemberRole(), memberEntity.getName());
        redisService.createData(memberEntity.getIdentity(), tokens[1], jwtCustomService.getRefreshExpire());

        return MemberDto.TOKEN.builder()
                .accessToken(tokens[0])
                .refreshToken(tokens[1])
                .build();
    }

    private MemberDto.TOKEN corporateLogin(MemberDto.LOGIN login){
        CorporateEntity corporateEntity = corporateRepository.findByIdentity(login.getIdentity())
                .orElseThrow(() -> new BadRequestException("아이디나 비밀번호를 다시 확인해주세요"));
        checkEncodePassword(login.getPassword(), corporateEntity.getPassword());

        String[] tokens = generateToken(corporateEntity.getIdentity(), corporateEntity.getMemberRole(), corporateEntity.getName());
        redisService.createData(corporateEntity.getIdentity(), tokens[1], jwtCustomService.getRefreshExpire());

        return MemberDto.TOKEN.builder()
                .accessToken(tokens[0])
                .refreshToken(tokens[1])
                .build();

    }

    private String[] generateToken(String identity,
                                   MemberRole role,
                                   String name){
        String accessToken = jwtCustomService.createAccessToken(identity, role, name);
        String refreshToken = jwtCustomService.createRefreshToken(identity, role, name);
        return new String[]{accessToken, refreshToken};
    }
}
