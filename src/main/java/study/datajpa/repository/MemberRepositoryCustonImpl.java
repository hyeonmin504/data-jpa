package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import java.util.List;

@RequiredArgsConstructor // 규칙 = 인터페이스 이름 + Impl 붙여서 이름을 만들기 (관례)
public class MemberRepositoryCustonImpl implements MemberRepositoryCuston{

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
