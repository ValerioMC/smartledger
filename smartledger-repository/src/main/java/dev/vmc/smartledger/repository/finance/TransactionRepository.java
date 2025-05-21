package dev.vmc.smartledger.repository.finance;

import dev.vmc.smartledger.model.finance.Account;
import dev.vmc.smartledger.model.finance.Category;
import dev.vmc.smartledger.model.finance.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find all transactions for an account.
     *
     * @param account the account to find transactions for
     * @return a list of transactions for the account
     */
    List<Transaction> findByAccount(Account account);

    /**
     * Find all transactions for an account, ordered by transaction date (descending).
     *
     * @param account the account to find transactions for
     * @return a list of transactions for the account, ordered by transaction date (descending)
     */
    List<Transaction> findByAccountOrderByTransactionDateDesc(Account account);

    /**
     * Find all transactions for an account, paginated and ordered by transaction date (descending).
     *
     * @param account the account to find transactions for
     * @param pageable pagination information
     * @return a page of transactions for the account, ordered by transaction date (descending)
     */
    Page<Transaction> findByAccount(Account account, Pageable pageable);

    /**
     * Find all transactions for a category.
     *
     * @param category the category to find transactions for
     * @return a list of transactions for the category
     */
    List<Transaction> findByCategory(Category category);

    /**
     * Find all transactions for an account within a date range.
     *
     * @param account the account to find transactions for
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of transactions for the account within the date range
     */
    List<Transaction> findByAccountAndTransactionDateBetween(Account account, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find all transactions for an account with a specific type.
     *
     * @param account the account to find transactions for
     * @param type the transaction type
     * @return a list of transactions for the account with the specified type
     */
    List<Transaction> findByAccountAndType(Account account, Transaction.TransactionType type);

    /**
     * Find all transactions for an account with a specific category.
     *
     * @param account the account to find transactions for
     * @param category the category to filter by
     * @return a list of transactions for the account with the specified category
     */
    List<Transaction> findByAccountAndCategory(Account account, Category category);

    /**
     * Search for transactions in an account by description.
     *
     * @param account the account to search in
     * @param description the description to search for
     * @return a list of transactions matching the search criteria
     */
    @Query("SELECT t FROM Transaction t WHERE t.account = :account AND LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Transaction> searchByAccountAndDescription(@Param("account") Account account, @Param("description") String description);
}