package kr.co.popool.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.infra.error.model.ResponseFormat;
import kr.co.popool.service.ScoreService;
import kr.co.popool.service.model.dto.QueryScoreDto.SHOWSCORE;
import kr.co.popool.service.model.dto.QueryScoreDto.SHOWSCORE.DELETE;
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
  private final GradeController gradeController;

  @ApiOperation("개인 평가 내역 조회")
  @GetMapping()
  public ResponseFormat show(@PathVariable String memberIdentity) {
    List<SHOWSCORE> scoreDtoList = scoreService.showScores(memberIdentity);
    return ResponseFormat.ok(scoreDtoList);
  }

  @ApiOperation("평가 내역 등록")
  @PostMapping("/create")
  public ResponseFormat create(@RequestBody ScoreDto.SCOREINFO newScoreDto) {
    scoreService.createScore(newScoreDto);
    gradeController.createGrade(newScoreDto);
    return ResponseFormat.ok();
  }

  @ApiOperation("평가 내역 수정")
  @PatchMapping("/{evaluatorIdentity}")
  public ResponseFormat update(@RequestBody ScoreDto.UPDATE updateScoreDto) {
    scoreService.updateScore(updateScoreDto);
    return ResponseFormat.ok();

  }

  @ApiOperation("평가 내역 삭제")
  @DeleteMapping("/delete")
  public ResponseFormat delete(@RequestBody DELETE deleteDto){
    scoreService.delete(deleteDto);
    gradeController.updateGrade(deleteDto);
    return ResponseFormat.ok();
  }

}
