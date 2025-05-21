package dev.vmc.smartledger.rest.controller;

import dev.vmc.smartledger.dto.report.ReportDto;
import dev.vmc.smartledger.service.report.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    private ReportDto reportDto;
    private UUID reportId;
    private List<ReportDto> reportList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        reportId = UUID.randomUUID();
        reportDto = ReportDto.builder()
                .id(reportId)
                .name("Test Report")
                .description("Test Description")
                .startDate(LocalDate.now().minusMonths(1))
                .endDate(LocalDate.now())
                .generatedAt(LocalDateTime.now())
                .totalExpenses(new BigDecimal("500.00"))
                .totalIncome(new BigDecimal("1000.00"))
                .netBalance(new BigDecimal("500.00"))
                .userId(1L)
                .username("testuser")
                .build();

        reportList = Arrays.asList(reportDto);
    }

    @Test
    void getAllReports_ShouldReturnAllReports() {
        // Arrange
        when(reportService.getAllReports()).thenReturn(reportList);

        // Act
        ResponseEntity<List<ReportDto>> response = reportController.getAllReports();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reportList, response.getBody());
    }

    @Test
    void getCurrentUserReports_ShouldReturnUserReports() {
        // Arrange
        when(reportService.getCurrentUserReports()).thenReturn(reportList);

        // Act
        ResponseEntity<List<ReportDto>> response = reportController.getCurrentUserReports();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reportList, response.getBody());
    }

    @Test
    void getReportById_ShouldReturnReport() {
        // Arrange
        when(reportService.getReportById(reportId)).thenReturn(reportDto);

        // Act
        ResponseEntity<ReportDto> response = reportController.getReportById(reportId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reportDto, response.getBody());
    }

    @Test
    void createReport_ShouldReturnCreatedReport() {
        // Arrange
        when(reportService.createReport(any(ReportDto.class))).thenReturn(reportDto);

        // Act
        ResponseEntity<ReportDto> response = reportController.createReport(reportDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reportDto, response.getBody());
        assertEquals(URI.create("/api/reports/" + reportId), response.getHeaders().getLocation());
    }

    @Test
    void updateReport_ShouldReturnUpdatedReport() {
        // Arrange
        when(reportService.updateReport(eq(reportId), any(ReportDto.class))).thenReturn(reportDto);

        // Act
        ResponseEntity<ReportDto> response = reportController.updateReport(reportId, reportDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reportDto, response.getBody());
    }

    @Test
    void deleteReport_ShouldReturnNoContent() {
        // Arrange
        doNothing().when(reportService).deleteReport(reportId);

        // Act
        ResponseEntity<Void> response = reportController.deleteReport(reportId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(reportService, times(1)).deleteReport(reportId);
    }
}