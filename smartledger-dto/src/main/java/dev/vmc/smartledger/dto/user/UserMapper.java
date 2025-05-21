package dev.vmc.smartledger.dto.user;

import dev.vmc.smartledger.model.user.Role;
import dev.vmc.smartledger.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for User entity and DTO.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Convert User entity to UserDto.
     *
     * @param user the User entity
     * @return the UserDto
     */
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToStrings")
    UserDto toDto(User user);

    /**
     * Convert UserDto to User entity.
     *
     * @param userDto the UserDto
     * @return the User entity
     */
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto userDto);

    /**
     * Convert a list of User entities to a list of UserDtos.
     *
     * @param users the list of User entities
     * @return the list of UserDtos
     */
    List<UserDto> toDtoList(List<User> users);

    /**
     * Convert a set of Role entities to a set of role name strings.
     *
     * @param roles the set of Role entities
     * @return the set of role name strings
     */
    @Named("rolesToStrings")
    default Set<String> rolesToStrings(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}