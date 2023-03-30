package kr.co.popool.bblmember.controller;

import kr.co.popool.bblmember.infra.error.model.ResponseFormat;
import kr.co.popool.bblmember.service.GradeService;
import kr.co.popool.bblmember.service.model.dtos.GradeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/grade")
public class GradeController {

    private final GradeService gradeService;

    @GetMapping
    public ResponseFormat<GradeDto.READ> getGrade() {
        return ResponseFormat.ok(gradeService.getGrade());
    }
}
