package com.djg.query.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA de solo lectura para consultas de préstamos procesados.
 * Implementa el patrón CQRS - Query Side para operaciones de lectura.
 * Marcada como @Immutable para garantizar que no se modifiquen los datos.
 */
@Entity
@Table(name = "prestamos_procesados")
@Immutable
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoQueryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Datos del préstamo procesado
     */
    @Column(nullable = false)
    private String dni;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private Integer meses;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cuotaMensual;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal tasaInteres;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPagar;

    /**
     * Estado del procesamiento
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado;

    /**
     * Campos de auditoría automática
     */
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    /**
     * Campos para control de idempotencia con Kafka
     */
    @Column(name = "kafka_topic")
    private String kafkaTopic;

    @Column(name = "kafka_partition")
    private Integer kafkaPartition;

    @Column(name = "kafka_offset")
    private Long kafkaOffset;

    @Column(name = "kafka_message_key")
    private String kafkaMessageKey;

    /**
     * Estados posibles del préstamo
     */
    public enum EstadoPrestamo {
        PROCESANDO,
        CALCULADO,
        ERROR
    }
}