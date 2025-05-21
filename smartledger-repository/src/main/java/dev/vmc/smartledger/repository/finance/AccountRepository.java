package dev.vmc.smartledger.repository.finance;

import dev.vmc.smartledger.model.finance.Account;
import dev.vmc.smartledger.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Account entity.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find all accounts belonging to a user.
     *
     * @param user the user to find accounts for
     * @return a list of accounts belonging to the user
     */
    List<Account> findByUser(User user);

    /**
     * Find all accounts belonging to a user, ordered by name.
     *
     * @param user the user to find accounts for
     * @return a list of accounts belonging to the user, ordered by name
     */
    List<Account> findByUserOrderByNameAsc(User user);

    /**
     * Find an account by id and user.
     *
     * @param id the account id
     * @param user the user who owns the account
     * @return an Optional containing the account if found, or empty if not found
     */
    Optional<Account> findByIdAndUser(Long id, User user);

    /**
     * Find an account by name and user.
     *
     * @param name the account name
     * @param user the user who owns the account
     * @return an Optional containing the account if found, or empty if not found
     */
    Optional<Account> findByNameAndUser(String name, User user);

    /**
     * Check if an account exists with the given name for a user.
     *
     * @param name the account name
     * @param user the user who owns the account
     * @return true if an account exists with the given name for the user, false otherwise
     */
    boolean existsByNameAndUser(String name, User user);
}