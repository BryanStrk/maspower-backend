# 🏋️ MAS POWER — Gym Management API

> **Industrial Brutalist Design** · High-Performance · Mission Control

```
███╗   ███╗ █████╗ ███████╗    ██████╗  ██████╗ ██╗    ██╗███████╗██████╗
████╗ ████║██╔══██╗██╔════╝    ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗
██╔████╔██║███████║███████╗    ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝
██║╚██╔╝██║██╔══██║╚════██║    ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗
██║ ╚═╝ ██║██║  ██║███████║    ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║
╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝
```

---

## 📋 Descripción del Proyecto

**MAS POWER** es un sistema integral de gestión para gimnasios y centros fitness. Construido como una API REST robusta con Spring Boot, permite administrar de forma completa el ciclo de vida de las actividades deportivas, los instructores y los miembros del gimnasio.

El sistema implementa lógica de negocio real: control de cupos por actividad, registro de usuarios en sesiones, gestión del estado de instructores y validación automática de reglas operativas — todo accesible mediante una API limpia y bien estructurada.

**Contexto de uso:** La aplicación está diseñada para ser utilizada en recepción, tanto por llamada como de forma presencial, para inscribir a miembros registrados en actividades del gimnasio.

---

## ⚡ Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| ☕ Java | 25 | Lenguaje principal |
| 🍃 Spring Boot | 4.0.5 | Framework principal |
| 🗄️ Spring Data JPA | 4.0.x | Persistencia y ORM |
| 🔍 Spring Validation | 4.0.x | Validación de datos |
| 🗃️ MySQL | 8.0 | Base de datos relacional |
| 🔨 Hibernate | 7.2.7 | ORM / DDL automático |
| ☁️ Cloudinary | 1.39.0 | Gestión de imágenes |
| ⚙️ Lombok | 1.18.x | Reducción de boilerplate |
| 📦 Maven | 3.x | Gestión de dependencias |

---

## 🏗️ Arquitectura

El proyecto sigue una **arquitectura por capas** estricta, garantizando separación de responsabilidades y mantenibilidad:

```
HTTP Request
     │
     ▼
┌─────────────┐
│  Controller │  ← Recibe peticiones, delega al Service, devuelve ResponseEntity
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Service   │  ← Lógica de negocio, validaciones, reglas operativas
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Repository  │  ← Acceso a datos mediante JPA / consultas personalizadas
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    Model    │  ← Entidades JPA mapeadas a tablas MySQL
└─────────────┘
```

### DTOs y Mappers

Se utilizan **DTOs manuales con Mappers estáticos** para controlar con precisión qué datos se exponen en cada respuesta, evitando la serialización directa de entidades JPA y previniendo referencias circulares:

```
Entity → Mapper.toDTO() → ResponseDTO → JSON Response
```

---

## 📁 Estructura del Proyecto

```
src/main/java/com/maspower/
│
├── 🎮 controller/
│   ├── ActivityController.java      # CRUD actividades + inscripciones
│   ├── UserController.java          # CRUD usuarios
│   ├── ProfessorController.java     # CRUD profesores
│   └── ImageController.java         # Upload de imágenes a Cloudinary
│
├── ⚙️ service/
│   ├── ActivityService.java         # Lógica de negocio principal
│   ├── UserService.java
│   ├── ProfessorService.java
│   └── CloudinaryService.java       # Gestión de subida de imágenes
│
├── 🗃️ repository/
│   ├── ActivityRepository.java
│   ├── UserRepository.java
│   └── ProfessorRepository.java
│
├── 📦 model/
│   ├── Activity.java                # @ManyToOne Professor, @ManyToMany User
│   ├── User.java
│   └── Professor.java
│
├── 📤 dto/
│   ├── ActivityResponseDTO.java
│   ├── ProfessorSummaryDTO.java
│   ├── UserSummaryDTO.java
│   └── ActivityMapper.java
│
├── ❌ exception/
│   ├── GlobalExceptionHandler.java  # @RestControllerAdvice
│   ├── ResourceNotFoundException.java
│   └── BusinessException.java
│
└── 🔧 config/
    └── CloudinaryConfig.java
```

---

## 🔗 Endpoints Principales

### 👤 Users — `/api/users`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/users` | Obtener todos los usuarios |
| `GET` | `/api/users/{id}` | Obtener usuario por ID |
| `POST` | `/api/users` | Crear nuevo usuario |
| `PUT` | `/api/users/{id}` | Actualizar usuario |
| `DELETE` | `/api/users/{id}` | Eliminar usuario |

### 🧑‍🏫 Professors — `/api/professors`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/professors` | Obtener todos los profesores |
| `GET` | `/api/professors/{id}` | Obtener profesor por ID |
| `POST` | `/api/professors` | Crear nuevo profesor |
| `PUT` | `/api/professors/{id}` | Actualizar profesor |
| `DELETE` | `/api/professors/{id}` | Eliminar profesor |

### 🏃 Activities — `/api/activities`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/activities` | Obtener todas las actividades |
| `GET` | `/api/activities/future` | Obtener actividades futuras |
| `GET` | `/api/activities/{id}` | Obtener actividad por ID |
| `POST` | `/api/activities` | Crear nueva actividad |
| `PUT` | `/api/activities/{id}` | Actualizar actividad |
| `DELETE` | `/api/activities/{id}` | Eliminar actividad |
| `POST` | `/api/activities/{activityId}/enroll/{userId}` | ✅ Inscribir usuario en actividad |
| `DELETE` | `/api/activities/{activityId}/unenroll/{userId}` | ❌ Desinscribir usuario de actividad |

### 🖼️ Images — `/api/images`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/images/upload` | Subir imagen a Cloudinary |

---

## 🛡️ Reglas de Negocio

Las siguientes validaciones están implementadas en `ActivityService.java` y se aplican automáticamente en el proceso de inscripción:

### ✅ Regla 1 — Usuario Activo
```
Un usuario que no ha pagado su cuota anual (isActive = false)
no puede inscribirse en ninguna actividad.
→ Lanza BusinessException: "User is not active"
```

### ✅ Regla 2 — Sin Duplicados
```
Un usuario no puede inscribirse dos veces en la misma actividad.
→ Lanza BusinessException: "User already enrolled in this activity"
```

### ✅ Regla 3 — Límite de Actividades Futuras
```
Un usuario no puede estar inscrito en más de 3 actividades futuras a la vez.
→ Lanza BusinessException: "User already has 3 future activities"
```

### ✅ Regla 4 — Profesor Activo
```
Un profesor no puede ser asignado a una actividad si no está dado de alta.
→ Validado mediante isActive en la entidad Professor.
```

---

## ⚠️ Manejo de Excepciones

El sistema utiliza un `GlobalExceptionHandler` con `@RestControllerAdvice` que intercepta y formatea todos los errores:

```json
{
  "timestamp": "2026-04-14T10:58:16.760",
  "status": 404,
  "error": "User not found with id: 999"
}
```

| Excepción | HTTP Status | Uso |
|---|---|---|
| `ResourceNotFoundException` | `404 Not Found` | Recurso no encontrado |
| `BusinessException` | `400 Bad Request` | Violación de regla de negocio |
| `MethodArgumentNotValidException` | `400 Bad Request` | Fallo de validación `@Valid` |
| `Exception` | `500 Internal Server Error` | Error inesperado |

---

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 21+
- Maven 3.x
- MySQL 8.0
- Cuenta en [Cloudinary](https://cloudinary.com)

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/BryanStrk/maspower-backend.git
cd maspower-backend
```

**2. Crear la base de datos**
```sql
CREATE DATABASE maspower_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**3. Configurar credenciales**

Crea el archivo `src/main/resources/application.properties`:
```properties
spring.application.name=maspower-backend

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/maspower_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Cloudinary
cloudinary.cloud-name=TU_CLOUD_NAME
cloudinary.api-key=TU_API_KEY
cloudinary.api-secret=TU_API_SECRET

# Server
server.port=8080
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

**4. Ejecutar la aplicación**
```bash
./mvnw spring-boot:run
```

La API estará disponible en: `http://localhost:8080/api`

---

## 🗄️ Modelo de Base de Datos

```
users                    professors
─────────────────        ─────────────────────
id (PK)                  id (PK)
name                     name
surname                  dni (UNIQUE)
dni (UNIQUE)             hiring_year
registration_year        is_active
is_active                image_url
image_url
                         activities
activity_users           ─────────────────────
──────────────           id (PK)
activity_id (FK)    ←──  title
user_id (FK)        ←──  description
                         price
                         date
                         image_url
                         professor_id (FK) ──→ professors
```

---

## 👤 Autor

**Bryan Paico Albines**
- GitHub: [@BryanStrk](https://github.com/BryanStrk)
- Proyecto académico — DAW

---

*MAS POWER · MISSION CONTROL / HIGH PERFORMANCE TACTICAL OPS*
