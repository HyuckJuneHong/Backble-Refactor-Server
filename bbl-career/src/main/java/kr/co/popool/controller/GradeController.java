package kr.co.popool.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.infra.error.model.ResponseFormat;
import kr.co.popool.service.GradeService;
import kr.co.popool.service.model.dto.QueryGradeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/careers/{memberIdentity}/scores/grade")
public class GradeController {

    private final GradeService gradeService;

    @ApiOperation("개인 등급 상세 내역 조회")
    @GetMapping
    public ResponseFormat<QueryGradeDto.GRADEDETAIL> getGrade(@PathVariable(name = "memberIdentity") String memberIdentity) {
        return ResponseFormat.ok(gradeService.getGrade(memberIdentity));
    }
}
