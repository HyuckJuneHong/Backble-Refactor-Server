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
import kr.co.popool.bblmember.service.model.enums.Rank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final MemberRepository memberRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public void createScore(ScoreDto.CREATE create) {
        MemberEntity memberEntity = getThreadLocal();
        checkScore(memberEntity, create.getOtherMemberIdentity());

        final ScoreEntity scoreEntity = ScoreEntity.toScoreEntity(create, memberEntity);
        scoreRepository.save(scoreEntity);

        GradeEntity gradeEntity = firstScore(memberEntity);
        GradeDto.UPDATE update = scoreResult(gradeEntity, scoreEntity);
        gradeEntity.updateGrade(update);
        gradeRepository.save(gradeEntity);
    }

    public List<ScoreDto.READ> getAllScore() {
        MemberEntity memberEntity = getThreadLocal();
        return scoreRepository.findByOtherMemberIdentity(memberEntity.getIdentity())
                .stream().map(ScoreEntity::toReadDto).collect(Collectors.toList());
    }

    public List<ScoreDto.READ> getAllOtherScore() {
        MemberEntity memberEntity = getThreadLocal();
        return scoreRepository.findByMemberEntity(memberEntity)
                .stream().map(ScoreEntity::toReadDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteScore(ScoreDto.DELETE delete) {
        MemberEntity memberEntity = getThreadLocal();
        ScoreEntity scoreEntity = scoreRepository.findByMemberEntityAndOtherMemberIdentity(memberEntity, delete.getOtherMemberIdentity())
                .orElseThrow(() -> new BadRequestException("해당 유저에 대한 평가 기록이 없습니다."));

        GradeEntity gradeEntity = firstScore(memberEntity);
        GradeDto.UPDATE update = scoreDeleteResult(gradeEntity, scoreEntity);
        gradeEntity.updateGrade(update);
        gradeRepository.save(gradeEntity);

        scoreRepository.delete(scoreEntity);
    }

    private GradeDto.UPDATE scoreResult(GradeEntity gradeEntity,
                                        ScoreEntity scoreEntity){
        long memberCount = gradeEntity.getMemberCount() + 1;

        double attend = gradeEntity.getAttendSum() + scoreEntity.getAttend();
        double since = gradeEntity.getSinceSum() + scoreEntity.getSince();
        double positive = gradeEntity.getPositiveSum() + scoreEntity.getPositive();
        double cooperative = gradeEntity.getCooperativeSum() + scoreEntity.getCooperative();
        double technic = gradeEntity.getTechnicSum() + scoreEntity.getTechnic();

        double attendAvg = attend/memberCount;
        double sinceAvg = since/memberCount;
        double positiveAvg = positive/memberCount;
        double cooperativeAvg = cooperative/memberCount;
        double technicAvg = technic/memberCount;

        double totalAvg = (attendAvg + sinceAvg + positiveAvg + cooperativeAvg + technicAvg)/5.0;

        Rank rank = Rank.WHITE;
        if(totalAvg < 1.0) rank = Rank.WHITE;
        else if(totalAvg >= 1.0 && totalAvg < 3.0) rank = Rank.BRONZE;
        else if(totalAvg >= 3.0 && totalAvg < 7.0) rank = Rank.SILVER;
        else if(totalAvg >= 7.0) rank = Rank.GOLD;

        return GradeDto.UPDATE.builder()
                .attendSum(attend)
                .sinceSum(since)
                .positiveSum(positive)
                .cooperativeSum(cooperative)
                .technicSum(technic)
                .attendAvg(attendAvg)
                .sinceAvg(sinceAvg)
                .positiveAvg(positiveAvg)
                .cooperativeAvg(cooperativeAvg)
                .technicAvg(technicAvg)
                .memberCount(memberCount)
                .rank(rank)
                .memberEntity(gradeEntity.getMemberEntity())
                .build();
    }

    private GradeDto.UPDATE scoreDeleteResult(GradeEntity gradeEntity,
                                              ScoreEntity scoreEntity){
        long memberCount = gradeEntity.getMemberCount() - 1;

        double attend = gradeEntity.getAttendSum() - scoreEntity.getAttend();
        double since = gradeEntity.getSinceSum() - scoreEntity.getSince();
        double positive = gradeEntity.getPositiveSum() - scoreEntity.getPositive();
        double cooperative = gradeEntity.getCooperativeSum() - scoreEntity.getCooperative();
        double technic = gradeEntity.getTechnicSum() - scoreEntity.getTechnic();

        double attendAvg = attend/memberCount;
        double sinceAvg = since/memberCount;
        double positiveAvg = positive/memberCount;
        double cooperativeAvg = cooperative/memberCount;
        double technicAvg = technic/memberCount;

        double totalAvg = (attendAvg + sinceAvg + positiveAvg + cooperativeAvg + technicAvg)/5.0;

        Rank rank = Rank.WHITE;
        if(totalAvg < 1.0) rank = Rank.WHITE;
        else if(totalAvg >= 1.0 && totalAvg < 3.0) rank = Rank.BRONZE;
        else if(totalAvg >= 3.0 && totalAvg < 7.0) rank = Rank.SILVER;
        else if(totalAvg >= 7.0) rank = Rank.GOLD;

        return GradeDto.UPDATE.builder()
                .attendSum(attend)
                .sinceSum(since)
                .positiveSum(positive)
                .cooperativeSum(cooperative)
                .technicSum(technic)
                .attendAvg(attendAvg)
                .sinceAvg(sinceAvg)
                .positiveAvg(positiveAvg)
                .cooperativeAvg(cooperativeAvg)
                .technicAvg(technicAvg)
                .memberCount(memberCount)
                .rank(rank)
                .memberEntity(gradeEntity.getMemberEntity())
                .build();
    }

    private MemberEntity getThreadLocal() {
        String identity = IdentityThreadLocal.get();
        return memberRepository.findByIdentity(identity)
                .orElseThrow(() -> new BadRequestException("존재하지 않는 회원입니다."));
    }

    private GradeEntity firstScore(MemberEntity memberEntity){
        return gradeRepository.existsByMemberEntity(memberEntity) ?
                gradeRepository.findByMemberEntity(memberEntity).get(): GradeEntity.toFirstGradeEntity(memberEntity);
    }

    private void checkScore(MemberEntity memberEntity,
                            String otherMemberIdentity){
        if(scoreRepository.existsByMemberEntityAndOtherMemberIdentity(memberEntity, otherMemberIdentity)){
            throw new BadRequestException("이미 평가한 회원입니다.");
        }
    }
}



