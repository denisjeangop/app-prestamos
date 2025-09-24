package com.djg.query.mapper;

import com.djg.query.model.dto.PrestamoQueryDto;
import com.djg.query.model.entity.PrestamoQueryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper para convertir entre PrestamoQueryEntity y PrestamoQueryDto.
 * Utiliza MapStruct para generación automática de código de mapeo.
 * Configurado como componente Spring para inyección de dependencias.
 *
 * @author Denis Jean Gopanchuk
 */
@Mapper(componentModel = "spring")
public interface PrestamoMapper {

    /**
     * Convierte una entidad PrestamoQueryEntity a DTO PrestamoQueryDto.
     * El mapeo se realiza automáticamente por nombres de campos coincidentes.
     *
     * @param entity Entidad a convertir
     * @return DTO convertido
     */
    @Mapping(source = "dni", target = "dni")
    PrestamoQueryDto toDto(PrestamoQueryEntity entity);

    /**
     * Convierte una lista de entidades a una lista de DTOs.
     * Utiliza el método toDto() para cada elemento de la lista.
     *
     * @param entities Lista de entidades a convertir
     * @return Lista de DTOs convertidos
     */
    List<PrestamoQueryDto> toDtoList(List<PrestamoQueryEntity> entities);
}