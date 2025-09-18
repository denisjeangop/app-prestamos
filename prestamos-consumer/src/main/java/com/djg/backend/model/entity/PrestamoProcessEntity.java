package com.djg.backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA para persistir préstamos procesados por el consumer.
 * Incluye campos de auditoría y control de idempotencia con Kafka.
 */
@Entity
@Table(name = "prestamos_procesados")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Datos del préstamo procesado
     */
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