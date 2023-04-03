package kr.co.popool.bblmember.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.bblmember.infra.error.model.ResponseFormat;
import kr.co.popool.bblmember.service.CorporateService;
import kr.co.popool.bblmember.service.model.dtos.CorporateDto;
import kr.co.popool.bblmember.service.model.dtos.MemberDto;
import kr.co.popool.bblmember.service.model.dtos.PasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/corporate")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class CorporateController {

    private final CorporateService corporateService;

    @ApiOperation("기업 회원가입")
    @PostMapping("/signUp")
    public ResponseFormat createCorporate(@RequestBody @Valid CorporateDto.CREATE_CORPORATE create){
        corporateService.createCorporate("corporate", create);
        return ResponseFormat.ok();
    }

    @ApiOperation("기업 정보 수정")
    @PutMapping
    public ResponseFormat updateCorporate(@RequestBody @Valid CorporateDto.UPDATE_CORPORATE updateCorporate){
        corporateService.updateCorporate("corporate", updateCorporate);
        return ResponseFormat.ok();
    }

    @ApiOperation("비밀번호 수정")
    @PutMapping("/password")
    public ResponseFormat updatePasswordCorporate(@RequestBody @Valid MemberDto.UPDATE_PASSWORD update){
        corporateService.updatePassword("corporate", update);
        return ResponseFormat.ok();
    }

    @ApiOperation("기업 정보 조회")
    @GetMapping
    public ResponseFormat<CorporateDto.READ_CORPORATE> getCorporate(){
        return ResponseFormat.ok(corporateService.getCorporate("corporate"));
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping
    public ResponseFormat deleteCorporate(@RequestBody PasswordDto password){
        corporateService.deleteCorporate("corporate", password.getPassword());
        return ResponseFormat.ok();
    }
}
