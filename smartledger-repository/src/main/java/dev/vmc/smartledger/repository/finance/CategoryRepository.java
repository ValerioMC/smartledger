package dev.vmc.smartledger.repository.finance;

import dev.vmc.smartledger.model.finance.Category;
import dev.vmc.smartledger.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Category entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find all categories belonging to a user.
     *
     * @param user the user to find categories for
     * @return a list of categories belonging to the user
     */
    List<Category> findByUser(User user);

    /**
     * Find all categories belonging to a user, ordered by name.
     *
     * @param user the user to find categories for
     * @return a list of categories belonging to the user, ordered by name
     */
    List<Category> findByUserOrderByNameAsc(User user);

    /**
     * Find all categories belonging to a user with a specific type.
     *
     * @param user the user to find categories for
     * @param type the category type
     * @return a list of categories belonging to the user with the specified type
     */
    List<Category> findByUserAndType(User user, Category.CategoryType type);

    /**
     * Find all top-level categories (categories without a parent) belonging to a user.
     *
     * @param user the user to find categories for
     * @param parent the parent category (null for top-level categories)
     * @return a list of top-level categories belonging to the user
     */
    List<Category> findByUserAndParent(User user, Category parent);

    /**
     * Find a category by id and user.
     *
     * @param id the category id
     * @param user the user who owns the category
     * @return an Optional containing the category if found, or empty if not found
     */
    Optional<Category> findByIdAndUser(Long id, User user);

    /**
     * Find a category by name and user.
     *
     * @param name the category name
     * @param user the user who owns the category
     * @return an Optional containing the category if found, or empty if not found
     */
    Optional<Category> findByNameAndUser(String name, User user);

    /**
     * Check if a category exists with the given name for a user.
     *
     * @param name the category name
     * @param user the user who owns the category
     * @return true if a category exists with the given name for the user, false otherwise
     */
    boolean existsByNameAndUser(String name, User user);
}