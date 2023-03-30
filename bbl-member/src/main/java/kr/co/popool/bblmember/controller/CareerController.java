package kr.co.popool.bblmember.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.bblmember.infra.error.model.ResponseFormat;
import kr.co.popool.bblmember.service.CareerService;
import kr.co.popool.bblmember.service.model.dtos.CareerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/careers")
@CrossOrigin(origins = "http://localhost:63342")
public class CareerController {

    private final CareerService careerService;

    @ApiOperation("인사 내역 등록")
    @PostMapping
    public ResponseFormat createCareer(@RequestBody CareerDto.CREATE careerDto) {
        careerService.createCareer(careerDto);
        return ResponseFormat.ok();
    }

    @ApiOperation("이력서 조회")
    @GetMapping("/{career_id}")
    public ResponseFormat<CareerDto.READ> getCareer(@PathVariable(name = "career_id") Long careerId) {
        return ResponseFormat.ok(careerService.getCareer(careerId));
    }

    @ApiOperation("모든 이력서 조회")
    @GetMapping("/all-career")
    public ResponseFormat<List<CareerDto.READ>> getAllCareer() {
        return ResponseFormat.ok(careerService.getAllCareer());
    }

    @ApiOperation("인사 내역 수정")
    @PutMapping
    public ResponseFormat updateCareer(@RequestBody CareerDto.UPDATE careerDto) {
        careerService.updateCareer(careerDto);
        return ResponseFormat.ok();
    }

    @ApiOperation("개인 인사 내역 삭제")
    @DeleteMapping("/{career_id}")
    public ResponseFormat deleteCareer(@PathVariable(name = "career_id") Long careerId){
        careerService.deleteCareer(careerId);
        return ResponseFormat.ok();
    }
}

