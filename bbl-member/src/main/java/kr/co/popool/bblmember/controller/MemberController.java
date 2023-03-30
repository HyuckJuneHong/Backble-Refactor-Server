package kr.co.popool.bblmember.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.bblmember.infra.error.model.ResponseFormat;
import kr.co.popool.bblmember.service.MemberService;
import kr.co.popool.bblmember.service.model.dtos.MemberDto;
import kr.co.popool.bblmember.service.model.dtos.PasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation("일반 회원가입")
    @PostMapping("/signUp")
    public ResponseFormat createMember(@RequestBody @Valid MemberDto.CREATE_MEMBER create){
        memberService.createMember(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("회원 정보 수정")
    @PutMapping
    public ResponseFormat updateMember(@RequestBody @Valid MemberDto.UPDATE update){
        memberService.updateMember(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("비밀번호 수정")
    @PutMapping("/password")
    public ResponseFormat updatePasswordMember(@RequestBody @Valid MemberDto.UPDATE_PASSWORD update){
        memberService.updatePassword(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("자신 회원 정보 조회")
    @GetMapping
    public ResponseFormat<MemberDto.READ> getMember(){
        return ResponseFormat.ok(memberService.getMember());
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping
    public ResponseFormat deleteMember(@RequestBody PasswordDto password){
        memberService.deleteMember(password.getPassword());
        return ResponseFormat.ok();
    }
}
