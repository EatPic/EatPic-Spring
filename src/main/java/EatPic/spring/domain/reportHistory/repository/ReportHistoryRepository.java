package EatPic.spring.domain.reportHistory.repository;

import EatPic.spring.domain.reportHistory.entity.ReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportHistoryRepository extends JpaRepository<ReportHistory,Long> {
}
