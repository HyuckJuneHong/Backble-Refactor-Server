package kr.co.popool.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.popool.infra.error.model.ResponseFormat;
import kr.co.popool.service.CareerService;
import kr.co.popool.service.model.dto.CareerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/careers")
public class CareerController {

  private final CareerService careerService;

  @GetMapping("/test")
  public String getTest(){
    return "test careers";
  }

  @ApiOperation("전체 인사 내역 조회")
  @GetMapping("/all")
  public List<CareerDto.CAREERINFO> showAll() {
    return careerService.showAll();
  }

  @ApiOperation("개인 인사 내역 조회")
  @GetMapping("")
  public CareerDto.CAREERINFO show(@RequestParam String memberIdentity) {
    return careerService.show(memberIdentity);
  }

  @ApiOperation("개인 인사 내역 등록")
  @PostMapping(value = "/create",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseFormat createCareer(@RequestPart(value = "createDto") CareerDto.CREATE careerDto, @RequestPart(value = "file",required = false)
  MultipartFile multipartFile) {
    careerService.newCareer(careerDto,multipartFile);
    return ResponseFormat.ok();
  }

  @ApiOperation("개인 인사 내역 수정")
  @PatchMapping("/{memberIdentity}")
  public ResponseFormat updateCareer(@RequestBody CareerDto.UPDATE careerDto) {
    careerService.update(careerDto);
    return ResponseFormat.ok();

  }

  @ApiOperation("개인 인사 내역 삭제")
  @DeleteMapping("/delete")
  public ResponseFormat delete(@RequestBody CareerDto.DELETE careerDto){
    careerService.delete(careerDto);
    return ResponseFormat.ok();
  }
}

