package dev.vmc.smartledger.dto.report;

import dev.vmc.smartledger.model.finance.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * Mapper for Report entity and DTO.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {

    /**
     * Convert Report entity to ReportDto.
     *
     * @param report the Report entity
     * @return the ReportDto
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    ReportDto toDto(Report report);

    /**
     * Convert ReportDto to Report entity.
     *
     * @param reportDto the ReportDto
     * @return the Report entity
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Report toEntity(ReportDto reportDto);

    /**
     * Convert a list of Report entities to a list of ReportDtos.
     *
     * @param reports the list of Report entities
     * @return the list of ReportDtos
     */
    List<ReportDto> toDtoList(List<Report> reports);
}