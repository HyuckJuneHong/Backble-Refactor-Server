package kr.co.popool.bblmember.service;

import kr.co.popool.bblmember.infra.error.exception.BadRequestException;
import kr.co.popool.bblmember.infra.interceptor.IdentityThreadLocal;
import kr.co.popool.bblmember.persistence.entity.GradeEntity;
import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import kr.co.popool.bblmember.persistence.entity.ScoreEntity;
import kr.co.popool.bblmember.persistence.repository.GradeRepository;
import kr.co.popool.bblmember.persistence.repository.MemberRepository;
import kr.co.popool.bblmember.persistence.repository.ScoreRepository;
import kr.co.popool.bblmember.service.model.dtos.GradeDto;
import kr.co.popool.bblmember.service.model.dtos.ScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeService {

    private final GradeRepository gradeRepository;
    private final MemberRepository memberRepository;
    private final ScoreRepository scoreRepository;

    public GradeDto.READ getGrade() {
        MemberEntity memberEntity = getThreadLocal();
        GradeEntity gradeEntity = firstScore(memberEntity);
        double totalAvg = getTotalAvg(gradeEntity);
        return GradeEntity.toReadDto(gradeEntity, totalAvg);
    }

    public ScoreDto.READ getMyGrade(String identity) {
        final MemberEntity memberEntity = getThreadLocal();
        final ScoreEntity scoreEntity = scoreRepository.findByMemberEntityAndOtherMemberIdentity(memberEntity, identity)
                .orElseThrow(() -> new BadRequestException("Not Found score"));

        return ScoreEntity.toReadDto(scoreEntity);
    }

    public GradeDto.READ getDetailGrade(String identity) {
        MemberEntity memberEntity = memberRepository.findByIdentity(identity)
                .orElseThrow(() -> new BadRequestException("Not Found Member"));
        GradeEntity gradeEntity = firstScore(memberEntity);
        double totalAvg = getTotalAvg(gradeEntity);
        return GradeEntity.toReadDto(gradeEntity, totalAvg);
    }


    private GradeEntity firstScore(MemberEntity memberEntity){
        return gradeRepository.existsByMemberEntity(memberEntity) ?
                gradeRepository.findByMemberEntity(memberEntity).get(): GradeEntity.toFirstGradeEntity(memberEntity);
    }

    private MemberEntity getThreadLocal() {
        String identity = IdentityThreadLocal.get();
        return memberRepository.findByIdentity(identity)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 회원입니다."));
    }

    private double getTotalAvg(GradeEntity gradeEntity){
        double totalSum = gradeEntity.getAttendAvg() + gradeEntity.getSinceAvg() +
                gradeEntity.getPositiveAvg() + gradeEntity.getCooperativeAvg() +
                gradeEntity.getTechnicAvg();
        return totalSum/5.0;
    }

    public boolean isGrade(String identity) {
        final MemberEntity memberEntity = getThreadLocal();

        return scoreRepository.existsByMemberEntityAndOtherMemberIdentity(memberEntity, identity);
    }

}
