package kr.co.popool.bblmember.controller;

import kr.co.popool.bblmember.infra.error.model.ResponseFormat;
import kr.co.popool.bblmember.service.GradeService;
import kr.co.popool.bblmember.service.model.dtos.GradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade")
@CrossOrigin(origins = "http://localhost:63342")
public class GradeController {

    private final GradeService gradeService;

    @GetMapping
    public ResponseFormat<GradeDto.READ> getGrade() {
        return ResponseFormat.ok(gradeService.getGrade());
    }
}
