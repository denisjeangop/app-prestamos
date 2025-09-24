package com.djg.query.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para respuestas del Query Service de préstamos.
 * Contiene solo los datos relevantes para el cliente, sin información técnica interna.
 * Implementa el patrón CQRS - Query Side para consultas de préstamos procesados.
 *
 * @author Denis Jean Gopanchuk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoQueryDto {
    
    /**
     * Identificador único del préstamo.
     */
    private Long id;
    
    /**
     * DNI del cliente solicitante.
     */
    private String dni;
    
    /**
     * Monto del préstamo solicitado.
     */
    private BigDecimal monto;
    
    /**
     * Número de meses para el pago.
     */
    private Integer meses;
    
    /**
     * Cuota mensual calculada.
     */
    private BigDecimal cuotaMensual;
    
    /**
     * Tasa de interés aplicada.
     */
    private BigDecimal tasaInteres;
    
    /**
     * Total a pagar calculado.
     */
    private BigDecimal totalPagar;
    
    /**
     * Estado actual del préstamo (PROCESANDO, CALCULADO, ERROR).
     */
    private String estado;
    
    /**
     * Fecha de creación del préstamo.
     */
    private LocalDateTime fechaCreacion;
}