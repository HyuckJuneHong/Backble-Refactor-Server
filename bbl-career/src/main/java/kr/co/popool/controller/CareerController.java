package kr.co.popool.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.infra.error.model.ResponseFormat;
import kr.co.popool.service.CareerService;
import kr.co.popool.service.model.dto.CareerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/careers")
public class CareerController {

    private final CareerService careerService;

    @ApiOperation("개인 인사 내역 조회")
    @GetMapping
    public ResponseFormat<CareerDto.CAREERINFO> getCareer(@RequestParam(name = "memberIdentity") String memberIdentity) {
        return ResponseFormat.ok(careerService.getCareer(memberIdentity));
    }

    @ApiOperation("개인 인사 내역 등록")
    @PostMapping
    public ResponseFormat createCareer(@RequestBody CareerDto.CREATE careerDto) {
        careerService.createCareer(careerDto);
        return ResponseFormat.ok();
    }

    @ApiOperation("개인 인사 내역 수정")
    @PutMapping
    public ResponseFormat updateCareer(@RequestBody CareerDto.UPDATE careerDto) {
        careerService.updateCareer(careerDto);
        return ResponseFormat.ok();
    }

    @ApiOperation("개인 인사 내역 삭제")
    @DeleteMapping
    public ResponseFormat deleteCareer(@RequestBody CareerDto.DELETE careerDto){
        careerService.deleteCareer(careerDto);
        return ResponseFormat.ok();
    }
}

