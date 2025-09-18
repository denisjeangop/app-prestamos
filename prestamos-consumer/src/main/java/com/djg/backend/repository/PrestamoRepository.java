package com.djg.backend.repository;

import com.djg.backend.model.entity.PrestamoProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de préstamos procesados.
 * Proporciona métodos para consultas específicas y control de idempotencia.
 */
@Repository
public interface PrestamoRepository extends JpaRepository<PrestamoProcessEntity, Long> {

    /**
     * @param kafkaOffset    el offset del mensaje en Kafka
     * @param kafkaPartition la partición del mensaje en Kafka
     * @return Optional con la entidad si existe
     */
    Optional<PrestamoProcessEntity> findByKafkaOffsetAndKafkaPartition(
            Long kafkaOffset, 
            Integer kafkaPartition
    );

    /**
     * Buscar préstamos por clave de mensaje de Kafka.
     *
     * @param kafkaMessageKey la clave del mensaje
     * @return lista de préstamos con esa clave
     */
    List<PrestamoProcessEntity> findByKafkaMessageKey(String kafkaMessageKey);

    /**
     * Buscar préstamos por estado.
     */
    List<PrestamoProcessEntity> findByEstado(PrestamoProcessEntity.EstadoPrestamo estado);

    /**
     * @param fechaInicio fecha de inicio (inclusive)
     * @param fechaFin    fecha de fin (inclusive)
     * @return numero de prestamos procesados
     */
    @Query("SELECT COUNT(p) FROM PrestamoProcessEntity p WHERE p.fechaCreacion BETWEEN :fechaInicio AND :fechaFin")
    long countByFechaCreacionBetween(@Param("fechaInicio") java.time.LocalDateTime fechaInicio,
                                     @Param("fechaFin") java.time.LocalDateTime fechaFin);

    /**'
     * @param kafkaOffset    offset del mensaje
     * @param kafkaPartition partición del mensaje
     * @param kafkaTopic     tópico del mensaje
     * @return true si ya existe, false en caso contrario
     */
    boolean existsByKafkaOffsetAndKafkaPartitionAndKafkaTopic(
            Long kafkaOffset, 
            Integer kafkaPartition, 
            String kafkaTopic
    );
}