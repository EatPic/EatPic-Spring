package EatPic.spring.domain.term.Repository;

import EatPic.spring.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {
    // termType 별 createdAt 기준 가장 최신 행 반환
    @Query("""
        select t from Term t
        where t.createdAt = (
            select max(t2.createdAt) from Term t2
            where t2.termType = t.termType
            )    
    """)

    List<Term> findLastByTermType();
}
