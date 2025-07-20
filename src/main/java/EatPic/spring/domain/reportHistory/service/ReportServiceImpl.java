package EatPic.spring.domain.reportHistory.service;

import EatPic.spring.domain.reportHistory.converter.ReportConverter;
import EatPic.spring.domain.reportHistory.dto.ReportResponseDTO;
import EatPic.spring.domain.reportHistory.entity.ReportHistory;
import EatPic.spring.domain.reportHistory.entity.ReportType;
import EatPic.spring.domain.reportHistory.entity.TargetType;
import EatPic.spring.domain.reportHistory.repository.ReportHistoryRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {
    private final ReportHistoryRepository reportHistoryRepository;
    private final UserRepository userRepository;

    @Override
    public ReportResponseDTO.ReportResultResponseDTO createReport(TargetType targetType, Long targetId, ReportType reportType) {
        User user = userRepository.findUserById(1L);// todo: 로그인 사용자

        ReportHistory report =  ReportHistory.builder()
                .targetType(targetType)
                .targetId(targetId)
                .reportType(reportType)
                .reporter(user)
                .build();

        reportHistoryRepository.save(report);

        return ReportConverter.toReportResultResponseDTO(report);
    }
}
