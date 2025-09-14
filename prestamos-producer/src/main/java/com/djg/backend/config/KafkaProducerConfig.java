package com.djg.backend.config;

import com.djg.backend.model.dto.PrestamoRequestDto;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuración personalizada para el productor Kafka.
 * <p>
 * Permite enviar objetos {@link PrestamoRequestDto} serializados en JSON
 * al broker configurado en el proyecto.
 * </p>
 *
 * @author Denis Jean Gopanchuk
 */
@Configuration
public class KafkaProducerConfig {

    @Autowired
    KafkaProperties kafkaProperties;

    /**
     * Crea el ProducerFactory con serialización JSON para los mensajes de préstamo.
     *
     * @return ProducerFactory configurado para {@link PrestamoRequestDto}
     */
    @Bean
    public ProducerFactory<String, PrestamoRequestDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Bean de KafkaTemplate para enviar mensajes de préstamo al topic correspondiente.
     *
     * @return KafkaTemplate configurado para {@link PrestamoRequestDto}
     */
    @Bean
    public KafkaTemplate<String, PrestamoRequestDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}