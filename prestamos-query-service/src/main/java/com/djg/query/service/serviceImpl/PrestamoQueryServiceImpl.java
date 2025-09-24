package com.djg.query.service.serviceImpl;

import com.djg.query.mapper.PrestamoMapper;
import com.djg.query.model.dto.BusquedaPrestamoRequestDto;
import com.djg.query.model.dto.PrestamoQueryDto;
import com.djg.query.model.entity.PrestamoQueryEntity;
import com.djg.query.repository.PrestamoQueryRepository;
import com.djg.query.service.PrestamoQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del servicio de consultas de préstamos.
 * Maneja las operaciones de solo lectura para el patrón CQRS - Query Side.
 *
 * @author Denis Jean Gopanchuk
 */
@Service
@RequiredArgsConstructor
public class PrestamoQueryServiceImpl implements PrestamoQueryService {

    private final PrestamoQueryRepository prestamoQueryRepository;
    private final PrestamoMapper prestamoMapper;

    @Override
    public List<PrestamoQueryDto> buscarPrestamos(BusquedaPrestamoRequestDto request) {
        // Convertir String a Enum si es necesario
        PrestamoQueryEntity.EstadoPrestamo estadoEnum = null;
        if (request.getEstado() != null && !request.getEstado().isEmpty()) {
            try {
                estadoEnum = PrestamoQueryEntity.EstadoPrestamo.valueOf(request.getEstado());
            } catch (IllegalArgumentException e) {
                return new ArrayList<>();
            }
        }
        

        List<PrestamoQueryEntity> entidades = prestamoQueryRepository.buscarConFiltros(
            request.getIdentificacion(), 
            estadoEnum
        );

        return prestamoMapper.toDtoList(entidades);
    }
}