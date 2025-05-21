package dev.vmc.smartledger.repository.user;

import dev.vmc.smartledger.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role by name.
     *
     * @param name the role name to search for
     * @return an Optional containing the role if found, or empty if not found
     */
    Optional<Role> findByName(Role.RoleType name);

    /**
     * Check if a role exists with the given name.
     *
     * @param name the role name to check
     * @return true if a role exists with the given name, false otherwise
     */
    boolean existsByName(Role.RoleType name);
}