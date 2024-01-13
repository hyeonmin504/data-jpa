package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("userA", 10);
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(member.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); //같은 트렌젝셔널에서는 같은 영속성컨텐츠를 사용함
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 10);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member findmember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findmember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findmember1).isEqualTo(member1);
        assertThat(findmember2).isEqualTo(member2);

        List<Member> all =memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count =memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeReaterTen() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> aaa = memberJpaRepository.findByUsernameAndAgeGeaterTen("AAA", 15);
        assertThat(aaa.get(0).getUsername()).isEqualTo("AAA");
        assertThat(aaa.get(0).getAge()).isEqualTo(20);
        assertThat(aaa.size()).isEqualTo(1);

    }

    @Test
    public void paging() {
        memberJpaRepository.save(new Member("member1",10));
        memberJpaRepository.save(new Member("member2",10));
        memberJpaRepository.save(new Member("member3",10));
        memberJpaRepository.save(new Member("member4",10));
        memberJpaRepository.save(new Member("member5",10));
        int age = 10;
        int offset = 1;
        int limit = 3;

        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        //then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }

    @Test
    public void bulkUpdate() {
        memberJpaRepository.save(new Member("member1",10));
        memberJpaRepository.save(new Member("member2",19));
        memberJpaRepository.save(new Member("member3",20));
        memberJpaRepository.save(new Member("member4",30));
        memberJpaRepository.save(new Member("member5",40));

        //when
        int reusltCount = memberJpaRepository.bulkAgePlus(20);

        //then
        assertThat(reusltCount).isEqualTo(3);
    }

}