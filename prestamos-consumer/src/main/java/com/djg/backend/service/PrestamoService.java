package com.djg.backend.service;

import com.djg.backend.model.dto.PrestamoResponseDto;

/**
 * Servicio para operaciones relacionadas con préstamos.
 */
public interface PrestamoService {

    /**
     * Calcula los intereses y el total a devolver de un préstamo.
     *
     * @param monto  Monto solicitado.
     * @param meses  Plazo en meses.
     * @return       DTO con el resultado del cálculo.
     */
    PrestamoResponseDto calcularPrestamo(double monto, int meses);
}