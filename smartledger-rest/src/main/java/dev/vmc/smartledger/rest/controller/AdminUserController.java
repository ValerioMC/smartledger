package dev.vmc.smartledger.rest.controller;

import dev.vmc.smartledger.dto.user.UserDto;
import dev.vmc.smartledger.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller for admin user management endpoints.
 */
@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {

    private final UserService userService;

    /**
     * Get all users.
     *
     * @return list of all users
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Getting all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Get a user by ID.
     *
     * @param id the user ID
     * @return the user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        log.info("Getting user with ID: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Create a new user.
     *
     * @param userDto the user data
     * @param password the user's password
     * @return the created user
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody UserDto userDto,
            @RequestParam String password) {
        log.info("Creating new user: {}", userDto.getUsername());
        UserDto createdUser = userService.createUser(userDto, password);
        return ResponseEntity
                .created(URI.create("/api/admin/users/" + createdUser.getId()))
                .body(createdUser);
    }

    /**
     * Update an existing user.
     *
     * @param id the user ID
     * @param userDto the updated user data
     * @return the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) {
        log.info("Updating user with ID: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    /**
     * Delete a user.
     *
     * @param id the user ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a role to a user.
     *
     * @param id the user ID
     * @param roleName the role name
     * @return the updated user
     */
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> addRoleToUser(
            @PathVariable Long id,
            @RequestParam String roleName) {
        log.info("Adding role {} to user with ID: {}", roleName, id);
        return ResponseEntity.ok(userService.addRoleToUser(id, roleName));
    }

    /**
     * Remove a role from a user.
     *
     * @param id the user ID
     * @param roleName the role name
     * @return the updated user
     */
    @DeleteMapping("/{id}/roles")
    public ResponseEntity<UserDto> removeRoleFromUser(
            @PathVariable Long id,
            @RequestParam String roleName) {
        log.info("Removing role {} from user with ID: {}", roleName, id);
        return ResponseEntity.ok(userService.removeRoleFromUser(id, roleName));
    }

    /**
     * Change a user's password.
     *
     * @param id the user ID
     * @param newPassword the new password
     * @return no content response
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestParam String newPassword) {
        log.info("Changing password for user with ID: {}", id);
        userService.changePassword(id, newPassword);
        return ResponseEntity.noContent().build();
    }
}