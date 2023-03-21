package kr.co.popool.bblmember.service;

import kr.co.popool.bblmember.infra.error.exception.JwtTokenExpiredException;
import kr.co.popool.bblmember.infra.error.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String getValue(String key){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void createData(String key, String value, long expired){
       redisTemplate.opsForValue().set(key, value, expired, TimeUnit.MILLISECONDS);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

    public void checkValue(String refreshToken, String redisToken) {
        if(!refreshToken.equals(redisToken)) throw new JwtTokenExpiredException(ErrorCode.FAIL_EXPIRE);
    }
}
