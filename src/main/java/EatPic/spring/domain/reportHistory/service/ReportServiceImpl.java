package EatPic.spring.domain.reportHistory.service;

import EatPic.spring.domain.card.repository.CardRepository;
import EatPic.spring.domain.reportHistory.converter.ReportConverter;
import EatPic.spring.domain.reportHistory.dto.ReportResponseDTO;
import EatPic.spring.domain.reportHistory.entity.ReportHistory;
import EatPic.spring.domain.reportHistory.entity.ReportType;
import EatPic.spring.domain.reportHistory.entity.TargetType;
import EatPic.spring.domain.reportHistory.repository.ReportHistoryRepository;
import EatPic.spring.domain.user.entity.User;
import EatPic.spring.domain.user.repository.UserRepository;
import EatPic.spring.global.common.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static EatPic.spring.global.common.code.status.ErrorStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {
    private final ReportHistoryRepository reportHistoryRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Override
    public ReportResponseDTO.ReportResultResponseDTO createReport(TargetType targetType, Long targetId, ReportType reportType) {
        User user = userRepository.findUserById(1L);// todo: 로그인 사용자
        validateTargetExists(targetType, targetId);

        ReportHistory report =  ReportHistory.builder()
                .targetType(targetType)
                .targetId(targetId)
                .reportType(reportType)
                .reporter(user)
                .build();

        reportHistoryRepository.save(report);

        return ReportConverter.toReportResultResponseDTO(report);
    }

    private void validateTargetExists(TargetType targetType, Long targetId) {
        switch (targetType) {
            case CARD ->{
                if (!cardRepository.existsById(targetId)) {
                    throw new ExceptionHandler(CARD_NOT_FOUND);
                }
            }
            case USER ->{
                if (!userRepository.existsById(targetId)) {
                    throw new ExceptionHandler(USER_NOT_FOUND);
                }
            }
        }
    }

}
