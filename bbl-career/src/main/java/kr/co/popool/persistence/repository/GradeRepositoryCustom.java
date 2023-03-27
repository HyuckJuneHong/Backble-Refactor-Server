package kr.co.popool.persistence.repository;

import kr.co.popool.service.model.dto.QueryGradeDto.GETVALUE;
import kr.co.popool.service.model.dto.QueryGradeDto.GRADEDETAIL;

import java.util.Optional;

public interface GradeRepositoryCustom {

  GETVALUE getValue(String memberIdentity) ;

  Optional<GRADEDETAIL> makeGradeDto(String memberIdentity, GETVALUE valueDto);

  Optional<GRADEDETAIL> showGradeDetail(String memberIdentity);

}
