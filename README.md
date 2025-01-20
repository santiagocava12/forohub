# ForoHub

ForoHub es una API REST construida con Spring Boot que permite gestionar tópicos en un foro. Este proyecto incluye autenticación y autorización mediante tokens JWT, así como operaciones CRUD sobre tópicos.

---

## **Características**

- **CRUD de Tópicos**: Crear, leer, actualizar y eliminar tópicos.
- **Autenticación con JWT**: Solo los usuarios autenticados pueden interactuar con la API.
- **Validación de datos**: Verifica campos obligatorios y evita la duplicación de tópicos.
- **Control de acceso**: Seguridad integrada mediante Spring Security.
- **Base de datos relacional**: Implementada con PostgreSQL y gestionada con JPA y Flyway.

---

## **Tecnologías utilizadas**

- **Java**: Versión 17
- **Spring Boot**: 3.4.1
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring DevTools
- **PostgreSQL**: Como base de datos
- **Flyway**: Para migraciones de la base de datos
- **JWT (JSON Web Token)**: Para autenticación
- **Lombok**: Reducción de código boilerplate
- **Maven**: Para la gestión de dependencias

---

## **Requisitos previos**

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- Java 17 o superior
- PostgreSQL
- Maven

---

## **Configuración del proyecto**

### **1. Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/foroHub.git
cd foroHub
```

### **2. Configurar la base de datos**

1. Crea una base de datos en PostgreSQL. Por ejemplo:
   ```sql
   CREATE DATABASE forohub;
   ```
2. Actualiza las variables de entorno necesarias:
   - **DB_HOST**: Host de la base de datos (por ejemplo, `localhost`).
   - **DB_NAME**: Nombre de la base de datos (por ejemplo, `forohub`).
   - **DB_USER**: Usuario de la base de datos.
   - **DB_PASSWORD**: Contraseña del usuario.

### **3. Configurar las propiedades de JWT**

Asegúrate de definir las siguientes propiedades en el archivo `application.properties` o como variables de entorno:

```properties
jwt.secret=tu_clave_secreta_segura
jwt.expiration=86400000 # 24 horas en milisegundos
```

### **4. Ejecutar las migraciones**
Flyway aplicará automáticamente las migraciones al iniciar la aplicación.

---

## **Ejecutar el proyecto**

### **1. Compilar y ejecutar**
Usa Maven para compilar y ejecutar la aplicación:

```bash
mvn spring-boot:run
```

### **2. Endpoints disponibles**

- **POST /login**: Generar un token JWT.
  - **Body (JSON):**
    ```json
    {
        "username": "usuario",
        "password": "contraseña"
    }
    ```

- **GET /topicos**: Listar todos los tópicos.
- **GET /topicos/{id}**: Consultar un tópico por su ID.
- **POST /topicos**: Crear un nuevo tópico.
  - **Body (JSON):**
    ```json
    {
        "titulo": "Título del tópico",
        "mensaje": "Mensaje del tópico",
        "estado": "Abierto",
        "autor": "Autor del tópico",
        "curso": "Curso relacionado"
    }
    ```
- **PUT /topicos/{id}**: Actualizar un tópico existente.
- **DELETE /topicos/{id}**: Eliminar un tópico.

### **3. Usar el token en las solicitudes**

Incluye el token JWT en el encabezado `Authorization` para interactuar con los endpoints protegidos:

```plaintext
Authorization: Bearer <TOKEN>
```

---

## **Ejemplo de flujo**

1. **Autenticarse para obtener un token:**
   - **Endpoint:** `POST /login`
   - **Body:**
     ```json
     {
         "username": "usuario",
         "password": "contraseña"
     }
     ```

2. **Usar el token para crear un tópico:**
   - **Endpoint:** `POST /topicos`
   - **Encabezados:**
     ```plaintext
     Authorization: Bearer <TOKEN>
     Content-Type: application/json
     ```
   - **Body:**
     ```json
     {
         "titulo": "Mi primer tópico",
         "mensaje": "Este es un mensaje de ejemplo",
         "estado": "Abierto",
         "autor": "Juan Pérez",
         "curso": "Java Avanzado"
     }
     ```

---

## **Estructura del proyecto**

```plaintext
src/
├── main/
│   ├── java/com/aluracursos/forohub/forohub/
│   │   ├── config/            # Configuración de seguridad y JWT
│   │   ├── controlador/       # Controladores REST
│   │   ├── modelo/            # Entidades de base de datos
│   │   ├── repositorio/       # Repositorios JPA
│   │   ├── servicio/          # Lógica de negocio (TokenService, AutenticacionService)
│   │   └── ForohubApplication # Clase principal
│   ├── resources/
│   │   ├── db/migration/      # Migraciones Flyway
│   │   └── application.properties # Configuración del proyecto
└── test/                      # Tests unitarios y de integración
```

---

