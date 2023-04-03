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
@RequestMapping("/career")
@CrossOrigin(origins = "http://localhost:63342")
public class CareerController {

    private final CareerService careerService;

    @ApiOperation("인사 내역 등록")
    @PostMapping
    public ResponseFormat createCareer(@RequestBody CareerDto.CREATE careerDto) {
        careerService.createCareer(careerDto);
        return ResponseFormat.ok();
    }

    @GetMapping("/is-career")
    public ResponseFormat<Boolean> isCareer(){
        return ResponseFormat.ok(careerService.isCareer());
    }

    @ApiOperation("이력서 조회")
    @GetMapping
    public ResponseFormat<CareerDto.READ> getCareer() {
        return ResponseFormat.ok(careerService.getCareer());
    }

    @ApiOperation("이력서 조회")
    @GetMapping("/{identity}")
    public ResponseFormat<CareerDto.READ> getCareer(@PathVariable(name = "identity") String identity) {
        return ResponseFormat.ok(careerService.getCareerIdentity(identity));
    }

    @ApiOperation("모든 이력서 조회")
    @GetMapping("/all-career")
    public ResponseFormat<List<CareerDto.READ_CAREER_CORPORATE>> getAllCareer() {
        return ResponseFormat.ok(careerService.getAllCareer());
    }

    @ApiOperation("인사 내역 수정")
    @PutMapping
    public ResponseFormat updateCareer(@RequestBody CareerDto.UPDATE careerDto) {
        careerService.updateCareer(careerDto);
        return ResponseFormat.ok();
    }

    @ApiOperation("개인 인사 내역 삭제")
    @DeleteMapping
    public ResponseFormat deleteCareer(){
        careerService.deleteCareer();
        return ResponseFormat.ok();
    }
}

