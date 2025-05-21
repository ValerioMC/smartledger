package dev.vmc.smartledger.service.report;

import dev.vmc.smartledger.dto.report.ReportDto;
import dev.vmc.smartledger.dto.report.ReportMapper;
import dev.vmc.smartledger.model.finance.Report;
import dev.vmc.smartledger.model.user.User;
import dev.vmc.smartledger.repository.finance.ReportRepository;
import dev.vmc.smartledger.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service for Report operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ReportMapper reportMapper;

    /**
     * Get all reports (admin only).
     *
     * @return list of all reports
     */
    @Transactional(readOnly = true)
    public List<ReportDto> getAllReports() {
        checkAdminAccess();
        return reportMapper.toDtoList(reportRepository.findAll());
    }

    /**
     * Get reports for the current user.
     *
     * @return list of reports for the current user
     */
    @Transactional(readOnly = true)
    public List<ReportDto> getCurrentUserReports() {
        User user = getCurrentUser();
        return reportMapper.toDtoList(reportRepository.findByUser(user));
    }

    /**
     * Get a report by ID.
     *
     * @param id the report ID
     * @return the report
     * @throws AccessDeniedException if the current user doesn't have access to the report
     */
    @Transactional(readOnly = true)
    public ReportDto getReportById(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        checkReportAccess(report);
        return reportMapper.toDto(report);
    }

    /**
     * Create a new report.
     *
     * @param reportDto the report data
     * @return the created report
     */
    @Transactional
    public ReportDto createReport(ReportDto reportDto) {
        User user = getCurrentUser();
        Report report = reportMapper.toEntity(reportDto);
        report.setUser(user);
        report.setGeneratedAt(LocalDateTime.now());
        
        // Calculate net balance if not provided
        if (report.getNetBalance() == null && report.getTotalIncome() != null && report.getTotalExpenses() != null) {
            report.setNetBalance(report.getTotalIncome().subtract(report.getTotalExpenses()));
        }
        
        Report savedReport = reportRepository.save(report);
        return reportMapper.toDto(savedReport);
    }

    /**
     * Update an existing report.
     *
     * @param id the report ID
     * @param reportDto the updated report data
     * @return the updated report
     * @throws AccessDeniedException if the current user doesn't have access to the report
     */
    @Transactional
    public ReportDto updateReport(UUID id, ReportDto reportDto) {
        Report existingReport = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        checkReportAccess(existingReport);

        // Update fields
        existingReport.setName(reportDto.getName());
        existingReport.setDescription(reportDto.getDescription());
        existingReport.setStartDate(reportDto.getStartDate());
        existingReport.setEndDate(reportDto.getEndDate());
        existingReport.setTotalExpenses(reportDto.getTotalExpenses());
        existingReport.setTotalIncome(reportDto.getTotalIncome());
        
        // Calculate net balance
        if (existingReport.getTotalIncome() != null && existingReport.getTotalExpenses() != null) {
            existingReport.setNetBalance(existingReport.getTotalIncome().subtract(existingReport.getTotalExpenses()));
        } else if (reportDto.getNetBalance() != null) {
            existingReport.setNetBalance(reportDto.getNetBalance());
        }

        Report updatedReport = reportRepository.save(existingReport);
        return reportMapper.toDto(updatedReport);
    }

    /**
     * Delete a report.
     *
     * @param id the report ID
     * @throws AccessDeniedException if the current user doesn't have access to the report
     */
    @Transactional
    public void deleteReport(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        checkReportAccess(report);
        reportRepository.delete(report);
    }

    /**
     * Check if the current user has access to a report.
     * Admin users have access to all reports.
     * Regular users only have access to their own reports.
     *
     * @param report the report to check access for
     * @throws AccessDeniedException if the current user doesn't have access to the report
     */
    private void checkReportAccess(Report report) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Admin can access all reports
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }
        
        // Regular users can only access their own reports
        String username = authentication.getName();
        if (!report.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You don't have access to this report");
        }
    }

    /**
     * Check if the current user has admin access.
     *
     * @throws AccessDeniedException if the current user doesn't have admin access
     */
    private void checkAdminAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Admin access required");
        }
    }

    /**
     * Get the current user.
     *
     * @return the current user
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + authentication.getName()));
    }
}