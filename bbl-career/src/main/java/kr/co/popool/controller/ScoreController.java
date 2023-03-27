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

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/careers/{memberIdentity}/scores")
public class ScoreController {

    private final ScoreService scoreService;

    @ApiOperation("개인 평가 내역 조회")
    @GetMapping
    public ResponseFormat<List<SHOWSCORE>> getScore(@PathVariable(name = "memberIdentity") String memberIdentity) {
        return ResponseFormat.ok(scoreService.getScores(memberIdentity));
    }

    @ApiOperation("평가 내역 등록")
    @PostMapping
    public ResponseFormat createScore(@PathVariable(name = "memberIdentity") String memberIdentity,
                                      @RequestBody ScoreDto.SCOREINFO newScoreDto) {
        scoreService.createScore(memberIdentity, newScoreDto);
        return ResponseFormat.ok();
    }

    @ApiOperation("평가 내역 수정")
    @PutMapping
    public ResponseFormat updateScore(@PathVariable(name = "memberIdentity") String memberIdentity,
                                      @RequestBody ScoreDto.UPDATE updateScoreDto) {
        scoreService.updateScore(updateScoreDto);
        return ResponseFormat.ok();
    }

    @ApiOperation("평가 내역 삭제")
    @DeleteMapping
    public ResponseFormat deleteScore(@RequestBody QueryScoreDto.DELETE deleteDto){
          scoreService.deleteScore(deleteDto);
          return ResponseFormat.ok();
    }
}
