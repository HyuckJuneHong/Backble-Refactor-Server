package kr.co.popool.bblmember.persistence.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.popool.bblmember.persistence.entity.MemberEntity;
import kr.co.popool.bblmember.persistence.entity.QMemberEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

@Component
public class MemberRepositoryCustomImpl extends QuerydslRepositorySupport  {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMemberEntity qMemberEntity = QMemberEntity.memberEntity;


    public MemberRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        super(MemberEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
