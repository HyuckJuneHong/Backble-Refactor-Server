package kr.co.popool.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.infra.error.model.ResponseFormat;
import kr.co.popool.service.ScoreService;
import kr.co.popool.service.model.dto.QueryScoreDto;
import kr.co.popool.service.model.dto.QueryScoreDto.SHOWSCORE;
import kr.co.popool.service.model.dto.ScoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/scores")
public class ScoreController {

    private final ScoreService scoreService;

    @ApiOperation("평가하기")
    @PostMapping
    public ResponseFormat createScore(@RequestBody @Valid ScoreDto.CREATE create){
        scoreService.createScore(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("본인 모든 평가 내역")
    @GetMapping("/my/{my_identity}")
    public ResponseFormat<List<ScoreDto.READ>> getAllMyScore(@PathVariable(name = "my_identity") String memberIdentity) {
        return ResponseFormat.ok(scoreService.getAllMyScore(memberIdentity));
    }

    @ApiOperation("평가 했던 모든 내역")
    @GetMapping("/you/{my_identity}")
    public ResponseFormat<List<ScoreDto.READ>> getAllYourScore(@PathVariable(name = "my_identity") String memberIdentity) {
        return ResponseFormat.ok(scoreService.getAllYourScore(memberIdentity));
    }

    @ApiOperation("평가 내역 수정")
    @PutMapping
    public ResponseFormat updateScore(@RequestBody ScoreDto.UPDATE update) {
        scoreService.updateScore(update);
        return ResponseFormat.ok();
    }

    @ApiOperation("평가 내역 삭제")
    @DeleteMapping
    public ResponseFormat deleteScore(@RequestBody ScoreDto.DELETE delete){
          scoreService.deleteScore(delete);
          return ResponseFormat.ok();
    }
}
