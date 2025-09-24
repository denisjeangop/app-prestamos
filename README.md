# 🏦 App Préstamos - Sistema Bancario con CQRS

Este proyecto es una mini aplicación bancaria que simula la solicitud y cálculo de intereses de un préstamo, implementando una **arquitectura CQRS (Command Query Responsibility Segregation)** orientada a eventos con Apache Kafka y Spring Boot.

## 🛠️ Tecnologías utilizadas

- **Java 17+**
- **Spring Boot 3.4.x**
- **Apache Kafka 7.4.0**
- **MySQL 8**
- **MapStruct 1.5.5** (para mapeo automático de entidades)
- **Maven** (gestión de dependencias)
- **Docker Compose** (orquestación de servicios)
- **Postman** (para pruebas de API)

## 🏗️ Arquitectura del Sistema

El proyecto implementa una **arquitectura de microservicios** con patrón **CQRS**, separando las operaciones de **escritura (Command)** de las de **lectura (Query)**:

### Microservicios

1. **prestamos-producer** (Puerto 8000) - **Command Side**
   - Recibe solicitudes de préstamos
   - Publica eventos a Kafka
   - Maneja operaciones de escritura

2. **prestamos-consumer** (Puerto 8001) - **Command Side**  
   - Procesa eventos de Kafka
   - Calcula intereses y cuotas
   - Actualiza estado de préstamos

3. **prestamos-query-service** (Puerto 8082) - **Query Side**
   - **Servicio de solo lectura** optimizado para consultas
   - API RESTful para búsquedas flexibles
   - Implementa mappers automáticos con MapStruct

### Flujo de Datos

```
[Client] → [Producer] → [Kafka] → [Consumer] → [Database]
                                      ↓
[Client] ← [Query Service] ←────────────────── [Database]
```

## ⚙️ Funcionalidades principales

### Command Side (Escritura)
- Comunicación asíncrona entre productores y consumidores con Kafka
- Cálculo dinámico de intereses en el consumer
- Flujo completo: solicitud → aprobación → cálculo de cuotas
- Manejo de excepciones para robustez y control de errores

### Query Side (Lectura)
- **API RESTful optimizada** para consultas de préstamos
- **Filtros flexibles** por DNI, estado, fechas y montos
- **Mapeo automático** de entidades a DTOs con MapStruct
- **Rendimiento optimizado** con consultas JPQL personalizadas
- **Entidades inmutables** para garantizar integridad de datos

## 📋 Endpoints disponibles

### Producer Service (Puerto 8000)
- `POST /api/prestamos` - Crear solicitud de préstamo

### Query Service (Puerto 8082)  
- `POST /api/consultas/buscar` - Búsqueda avanzada de préstamos

**Ejemplo de búsqueda:**
```json
{
  "dni": "12345678",
  "estado": "APROBADO",
  "montoMinimo": 1000,
  "montoMaximo": 50000
}
```

## 🚀 Cómo ejecutar el proyecto

### Prerequisitos
- Java 17+
- Maven 3.8+
- Docker y Docker Compose

### Ejecución con Docker
```bash
# Levantar servicios de infraestructura
docker-compose up -d

# Ejecutar cada microservicio
cd prestamos-producer && mvn spring-boot:run
cd prestamos-consumer && mvn spring-boot:run  
cd prestamos-query-service && mvn spring-boot:run
```

### Puertos de servicios
- **Producer**: http://localhost:8000
- **Consumer**: http://localhost:8001  
- **Query Service**: http://localhost:8082
- **MySQL**: localhost:3306
- **Kafka**: localhost:9092

## 🧪 Pruebas

Todas las pruebas de endpoints han sido realizadas con **Postman**, validando:
- Creación de préstamos (Producer)
- Procesamiento asíncrono (Consumer)  
- Consultas optimizadas (Query Service)
- Manejo de errores y excepciones

## 📊 Beneficios de la Arquitectura CQRS

- **Separación de responsabilidades**: Commands vs Queries
- **Escalabilidad independiente**: Cada lado puede escalar según demanda
- **Optimización específica**: Escritura vs Lectura optimizadas por separado
- **Flexibilidad en consultas**: APIs especializadas para diferentes necesidades
- **Mantenibilidad**: Código más limpio y enfocado por responsabilidad
