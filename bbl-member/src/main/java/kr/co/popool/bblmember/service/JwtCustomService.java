package kr.co.popool.bblmember.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.popool.bblmember.infra.error.exception.NotFoundException;
import kr.co.popool.bblmember.infra.error.exception.UserDefineException;
import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import kr.co.popool.bblmember.persistence.repository.MemberRepository;
import kr.co.popool.bblmember.service.model.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtCustomService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    final String MEMBER_IDENTITY = "identity";
    final String MEMBER_ROLE = "member_role";
    final String MEMBER_NAME = "name";

    private final String ACCESS_TOKEN = "AccessToken";
    private final String REFRESH_TOKEN = "RefreshToken";

    private final String TOKEN_TYPE = "typ";
    private final String TOKEN_ALGO = "JWT";

    private final long ACCESS_EXPIRE = 1000*60*30;        //30min
    private final long REFRESH_EXPIRE = 1000*60*60*24*14; //2week

    private final MemberRepository memberRepository;
    private final RedisService redisService;

    public long getRefreshExpire() {
        return REFRESH_EXPIRE;
    }

    /**
     * 시크릿 키를 Base64로 인코딩을 하는 메소드.
     */
    @PostConstruct
    protected void init(){
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * 키 변환을 위한 key 를 만드는 메서드
     * @return secret key
     */
    private byte[] generateKey(){
        try{
            return SECRET_KEY.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new UserDefineException("키 변환에 실패하였습니다. ", e.getMessage());
        }
    }

    /**
     * 사용자 정보를 통해 Cliams 객체를 생성하는 메소드
     * @param identity : 사용자 아이디
     * @param memberRole : 사용자 권한
     * @param name : 사용자 이름
     * @return 사용자 정보를 포함한 Claims 객체
     */
    private Claims generateClaims(String identity,
                                  MemberRole memberRole,
                                  String name){
        Claims claims = Jwts.claims();
        claims.put(MEMBER_IDENTITY, identity);
        claims.put(MEMBER_ROLE, memberRole);
        claims.put(MEMBER_NAME, name);

        return claims;
    }

    /**
     * 사용자 정보를 통해 AccessToken을 생성하는 메소드
     * @param identity : 사용자 아이디
     * @param memberRole : 사용자 권한
     * @param name : 사용자 이름
     * @return 사용자의 AccessToken
     */
    public String createAccessToken(String identity,
                                    MemberRole memberRole,
                                    String name){
        Date issueDate = new Date();
        Date expireDate = new Date();
        expireDate.setTime(issueDate.getTime() + ACCESS_EXPIRE);

        return Jwts.builder()
                .setHeaderParam(TOKEN_TYPE, TOKEN_ALGO)
                .setClaims(generateClaims(identity, memberRole, name))
                .setIssuedAt(issueDate)
                .setSubject(ACCESS_TOKEN)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    /**
     * 사용자 정보를 통해 RefreshToken 을 만드는 메서드
     * @param identity : 사용자 아이디
     * @param memberRole : 사용자 권한
     * @param name : 사용자 이름
     * @return 사용자의 RefreshToken
     */
    public String createRefreshToken(String identity,
                                     MemberRole memberRole,
                                     String name){
        Date issueDate = new Date();
        Date expireDate = new Date();
        expireDate.setTime(issueDate.getTime() + REFRESH_EXPIRE);

        return Jwts.builder()
                .setHeaderParam(TOKEN_TYPE, TOKEN_ALGO)
                .setClaims(generateClaims(identity, memberRole, name))
                .setIssuedAt(issueDate)
                .setSubject(REFRESH_TOKEN)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    /**
     * RefreshToken 을 이용하여 AccessToken 을 만들어내는 메서드
     * @param refreshToken 사용자의 RefreshToken
     * @return 사용자의 새로운 AccessToken
     */
    public String reCreateAccessToken(String refreshToken){
        MemberEntity member = findMemberByToken(refreshToken);
        redisService.checkValue(refreshToken, redisService.getValue(member.getIdentity()));

        return createAccessToken(member.getIdentity(), member.getMemberRole(), member.getName());
    }

    /**
     * 토큰을 통해 MemberMstEntity 객체를 가져오는 메서드
     * @param token : 토큰
     * @return : jwt 토큰을 통해 찾은 MemberMstEntity 객체
     * @Exception UserNotFoundException : 해당 회원을 찾을 수 없는 경우 발생하는 예외
     */
    public MemberEntity findMemberByToken(String token){
        return memberRepository.findByIdentity(findIdentityByToken(token))
                .orElseThrow(() -> new NotFoundException("해당 Token의 회원은 존재하지 않습니다."));
    }

    /**
     * 토큰을 이용하여 사용자 아이디를 찾는 메서드
     *
     * @param token 토큰
     * @return 사용자의 아이디
     */
    public String findIdentityByToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get(MEMBER_IDENTITY);
    }
}