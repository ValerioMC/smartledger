package dev.vmc.smartledger.model.finance;

import dev.vmc.smartledger.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a financial report in the system.
 */
@Entity
@Table(name = "reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Column(name = "total_expenses", precision = 19, scale = 2, nullable = false)
    private BigDecimal totalExpenses;

    @Column(name = "total_income", precision = 19, scale = 2, nullable = false)
    private BigDecimal totalIncome;

    @Column(name = "net_balance", precision = 19, scale = 2, nullable = false)
    private BigDecimal netBalance;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (generatedAt == null) {
            generatedAt = LocalDateTime.now();
        }
        if (totalExpenses == null) {
            totalExpenses = BigDecimal.ZERO;
        }
        if (totalIncome == null) {
            totalIncome = BigDecimal.ZERO;
        }
        if (netBalance == null) {
            netBalance = totalIncome.subtract(totalExpenses);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}