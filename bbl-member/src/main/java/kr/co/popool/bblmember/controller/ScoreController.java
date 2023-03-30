package kr.co.popool.bblmember.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.bblmember.infra.error.model.ResponseFormat;
import kr.co.popool.bblmember.service.ScoreService;
import kr.co.popool.bblmember.service.model.dtos.ScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreService scoreService;

    @ApiOperation("평가하기")
    @PostMapping
    public ResponseFormat createScore(@RequestBody @Valid ScoreDto.CREATE create){
        scoreService.createScore(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 모든 평가 내역")
    @GetMapping
    public ResponseFormat<List<ScoreDto.READ>> getAllScore() {
        return ResponseFormat.ok(scoreService.getAllScore());
    }

    @ApiOperation("평가 했던 모든 내역")
    @GetMapping("/others")
    public ResponseFormat<List<ScoreDto.READ>> getAllYourScore() {
        return ResponseFormat.ok(scoreService.getAllOtherScore());
    }

    @ApiOperation("평가 내역 삭제")
    @DeleteMapping
    public ResponseFormat deleteScore(@RequestBody @Valid ScoreDto.DELETE delete){
          scoreService.deleteScore(delete);
          return ResponseFormat.ok();
    }
}
