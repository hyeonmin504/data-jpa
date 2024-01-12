package study.datajpa.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;

    protected Member() {} //프록시 같은 애들이 강제 조회할 수 있어서 protected로 설정

    public Member(String username) {
        this.username = username;
    }
}

