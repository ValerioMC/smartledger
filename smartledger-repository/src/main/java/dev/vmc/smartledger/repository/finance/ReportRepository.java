package dev.vmc.smartledger.repository.finance;

import dev.vmc.smartledger.model.finance.Report;
import dev.vmc.smartledger.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Report entity.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    /**
     * Find all reports belonging to a user.
     *
     * @param user the user to find reports for
     * @return a list of reports belonging to the user
     */
    List<Report> findByUser(User user);

    /**
     * Find all reports belonging to a user, ordered by generated date (descending).
     *
     * @param user the user to find reports for
     * @return a list of reports belonging to the user, ordered by generated date (descending)
     */
    List<Report> findByUserOrderByGeneratedAtDesc(User user);

    /**
     * Find all reports belonging to a user, paginated.
     *
     * @param user the user to find reports for
     * @param pageable pagination information
     * @return a page of reports belonging to the user
     */
    Page<Report> findByUser(User user, Pageable pageable);

    /**
     * Find a report by id and user.
     *
     * @param id the report id
     * @param user the user who owns the report
     * @return an Optional containing the report if found, or empty if not found
     */
    Optional<Report> findByIdAndUser(UUID id, User user);

    /**
     * Find all reports for a user within a date range (based on report period).
     *
     * @param user the user to find reports for
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of reports for the user within the date range
     */
    List<Report> findByUserAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
            User user, LocalDate startDate, LocalDate endDate);

    /**
     * Find all reports for a user with a name containing the given string.
     *
     * @param user the user to find reports for
     * @param name the name to search for
     * @return a list of reports for the user with a name containing the given string
     */
    List<Report> findByUserAndNameContainingIgnoreCase(User user, String name);
}