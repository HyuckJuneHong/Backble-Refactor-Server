package kr.co.popool.bblmember.persistence.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.popool.bblmember.persistence.entity.CorporateEntity;
import kr.co.popool.bblmember.persistence.entity.QCorporateEntity;
import kr.co.popool.bblmember.persistence.entity.QMemberEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

@Component
public class CorporateRepositoryCustomImpl extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QCorporateEntity qCorporateEntity = QCorporateEntity.corporateEntity;
    private final QMemberEntity qMemberEntity = QMemberEntity.memberEntity;

    public CorporateRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        super(CorporateEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
