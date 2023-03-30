package kr.co.popool.bblmember.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.bblmember.infra.error.model.ResponseFormat;
import kr.co.popool.bblmember.service.CommonService;
import kr.co.popool.bblmember.service.model.dtos.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class CommonController {

    private final CommonService commonService;

    @ApiOperation("로그인")
    @PostMapping("/{userType}/login")
    public ResponseFormat<MemberDto.TOKEN> login(@PathVariable(name = "userType") String userType,
                                                 @RequestBody @Valid MemberDto.LOGIN login){
        return ResponseFormat.ok(commonService.login(userType, login));
    }
}
