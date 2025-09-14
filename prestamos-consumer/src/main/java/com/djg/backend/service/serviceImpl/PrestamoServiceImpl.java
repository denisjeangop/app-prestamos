package com.djg.backend.service.serviceImpl;

import com.djg.backend.model.dto.PrestamoResponseDto;
import com.djg.backend.service.PrestamoService;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de préstamos.
 * Calcula el resultado de una solicitud de préstamo según el TAE definido.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    /** Tasa Anual Equivalente fija para el cálculo de préstamos. */
    private static final double TAE = 9.67;

    /**
     * Calcula los intereses y el total a devolver de un préstamo.
     *
     * @param monto  Monto solicitado.
     * @param meses  Plazo en meses.
     * @return       DTO con el resultado del cálculo.
     */
    @Override
    public PrestamoResponseDto calcularPrestamo(double monto, int meses) {
        double intereses = monto * (TAE / 100) * (meses / 12.0);
        double total = monto + intereses;
        String mensaje = String.format(
                "Préstamo solicitado: %.2f€, Plazo: %d meses, TAE: %.2f%%, Intereses: %.2f€, Total a devolver: %.2f€",
                monto, meses, TAE, intereses, total
        );
        return new PrestamoResponseDto(monto, meses, TAE, intereses, total, mensaje);
    }
}