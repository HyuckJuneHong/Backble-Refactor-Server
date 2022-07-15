package kr.co.popool.bblmember.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.bblcommon.error.model.ResponseFormat;
import kr.co.popool.bblmember.domain.dto.MemberDto;
import kr.co.popool.bblmember.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;

    @ApiOperation("일반 회원가입")
    @PostMapping("/normal/signUp")
    public ResponseFormat signUp(@RequestBody @Valid MemberDto.CREATE create){
        memberService.signUp(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("일반 회원 자동 결제 여부")
    @PutMapping("/update/member")
    public ResponseFormat corporateUpdate(){
        memberService.paymentAgreeUpdate();
        return ResponseFormat.ok();
    }

}
