package kr.co.popool.bblmember.controller;

import kr.co.popool.bblmember.infra.error.model.ResponseFormat;
import kr.co.popool.bblmember.service.GradeService;
import kr.co.popool.bblmember.service.model.dtos.GradeDto;
import kr.co.popool.bblmember.service.model.dtos.ScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/is-grade/{identity}")
    public ResponseFormat<Boolean> isGrade(@PathVariable("identity") String identity){

        return ResponseFormat.ok(gradeService.isGrade(identity));
    }

    @GetMapping("/{identity}")
    public ResponseFormat<ScoreDto.READ> getMyGrade(@PathVariable("identity") String identity){

        return ResponseFormat.ok(gradeService.getMyGrade(identity));
    }

    @GetMapping("/detail/{identity}")
    public ResponseFormat<ScoreDto.READ> getDetailGrade(@PathVariable("identity") String identity){

        return ResponseFormat.ok(gradeService.getDetailGrade(identity));
    }
}
