package com.djg.query.service;

import com.djg.query.model.dto.BusquedaPrestamoRequestDto;
import com.djg.query.model.dto.PrestamoQueryDto;

import java.util.List;

/**
 * Interfaz para el servicio de consultas de préstamos.
 * Define las operaciones de solo lectura para el patrón CQRS - Query Side.
 *
 * @author Denis Jean Gopanchuk
 */
public interface PrestamoQueryService {

    /**
     * Busca préstamos aplicando los filtros especificados en la request.
     * 
     * @param request DTO con filtros de búsqueda (identificación y estado)
     * @return Lista de préstamos que coinciden con los filtros
     */
    List<PrestamoQueryDto> buscarPrestamos(BusquedaPrestamoRequestDto request);

}