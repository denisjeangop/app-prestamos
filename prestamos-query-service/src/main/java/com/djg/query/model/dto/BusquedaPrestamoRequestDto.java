package com.djg.query.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitudes de búsqueda de préstamos.
 * Contiene los filtros disponibles para consultar préstamos procesados.
 * Utilizado en endpoints POST para búsquedas complejas con datos sensibles.
 *
 * @author Denis Jean Gopanchuk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaPrestamoRequestDto {
    
    /**
     * Identificación del cliente (DNI) para filtrar préstamos.
     * Campo opcional para búsquedas específicas por cliente.
     */
    private String identificacion;
    
    /**
     * Estado del préstamo para filtrar resultados.
     * Valores posibles: PROCESANDO, CALCULADO, ERROR.
     * Campo opcional para búsquedas por estado específico.
     */
    private String estado;
}