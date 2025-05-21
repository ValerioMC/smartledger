package dev.vmc.smartledger.rest.controller;

import dev.vmc.smartledger.dto.report.ReportDto;
import dev.vmc.smartledger.service.report.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Controller for Report endpoints.
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    /**
     * Get all reports (admin only).
     *
     * @return list of all reports
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportDto>> getAllReports() {
        log.info("Getting all reports");
        return ResponseEntity.ok(reportService.getAllReports());
    }

    /**
     * Get reports for the current user.
     *
     * @return list of reports for the current user
     */
    @GetMapping
    public ResponseEntity<List<ReportDto>> getCurrentUserReports() {
        log.info("Getting reports for current user");
        return ResponseEntity.ok(reportService.getCurrentUserReports());
    }

    /**
     * Get a report by ID.
     *
     * @param id the report ID
     * @return the report
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getReportById(@PathVariable UUID id) {
        log.info("Getting report with ID: {}", id);
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    /**
     * Create a new report.
     *
     * @param reportDto the report data
     * @return the created report
     */
    @PostMapping
    public ResponseEntity<ReportDto> createReport(@Valid @RequestBody ReportDto reportDto) {
        log.info("Creating new report: {}", reportDto.getName());
        ReportDto createdReport = reportService.createReport(reportDto);
        return ResponseEntity
                .created(URI.create("/api/reports/" + createdReport.getId()))
                .body(createdReport);
    }

    /**
     * Update an existing report.
     *
     * @param id the report ID
     * @param reportDto the updated report data
     * @return the updated report
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReportDto> updateReport(@PathVariable UUID id, @Valid @RequestBody ReportDto reportDto) {
        log.info("Updating report with ID: {}", id);
        return ResponseEntity.ok(reportService.updateReport(id, reportDto));
    }

    /**
     * Delete a report.
     *
     * @param id the report ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable UUID id) {
        log.info("Deleting report with ID: {}", id);
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}