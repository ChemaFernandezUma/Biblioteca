
#  Proyecto Spring Boot - Biblioteca

Este proyecto ha sido desarrollado utilizando **Spring Boot 3.5.0**. Es una aplicación web para gestionar una biblioteca, incluyendo operaciones CRUD sobre profesores, materias y estudiantes, con relaciones entre ellos. Utiliza una arquitectura en capas basada en Spring (Controller - Service - Repository) y una base de datos MySQL para la persistencia.

---

##  Tecnologías y dependencias utilizadas

- **Spring Boot 3.5.0**
- **Spring Web**: Para la creación de controladores REST y manejo de peticiones HTTP.
- **Spring Data JPA**: Para la persistencia de datos y acceso a la base de datos.
- **MySQL Driver**: Para la conexión con la base de datos MySQL.
- **Lombok**: Para reducir el código boilerplate (getters, setters, constructores, etc.).
- **Thymeleaf**: Motor de plantillas para la generación de vistas HTML.

---

##  Requisitos previos

- **Java 17 o superior**
- **Maven
- **MySQL Server**

---

##  Configuración

1. Clona el repositorio:
   ```bash
   git clone https://github.com/ChemaFernandezUma/Biblioteca.git
   ```

2. Crea una base de datos en MySQL con el nombre correspondiente (definido en `application.properties`).

3. Configura el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

4. Ejecuta la aplicación desde tu IDE o con el comando:
   ```bash
   mvn spring-boot:run
   ```

---

##  Modelo de datos y relaciones

###  Entidades principales

#### 1. `Teacher`
- `id` (Long): Identificador único.
- `nombre` (String): Nombre del docente.
- **Relación**: Un `Teacher` puede tener **muchas** materias (`List<Subject>`).  
  - Anotación JPA: `@OneToMany(mappedBy = "teacher")`

#### 2. `Subject`
- `id` (Long): Identificador único.
- `nombre` (String): Nombre de la materia.
- **Relaciones**:
  - Una `Subject` está asociada a **un solo** `Teacher`.  
    - Anotación: `@ManyToOne`
  - Una `Subject` puede tener **muchos** `Student` y viceversa.  
    - Anotación: `@ManyToMany`

#### 3. `Student`
- `id` (Long): Identificador único.
- `nombre` (String): Nombre del estudiante.
- `apellido` (String): Apellido del estudiante.
- `identificacion` (String): Documento de identidad.
- **Relación**: Puede cursar muchas materias (`List<Subject>`) mediante una relación `@ManyToMany`.

###  Diagrama de relaciones simplificado:

```text
Teacher ────< Subject >──── Student
   1         N      M        N
```

---

##  Arquitectura del proyecto

```
com.indra.tp7
├── controller    → Controladores REST (exponen endpoints HTTP)
├── service       → Lógica de negocio
├── repository    → Interfaces que extienden JpaRepository
├── model         → Clases de entidad JPA
└── BibliotecaApplication.java → Clase principal con método main()
```

---

##  Endpoints disponibles

Cada entidad (`Teacher`, `Subject`, `Student`) dispone de los siguientes endpoints REST:

- `GET /api/{entidad}`: Obtener todos los registros
- `GET /api/{entidad}/{id}`: Obtener un registro por ID
- `POST /api/{entidad}`: Crear un nuevo registro
- `PUT /api/{entidad}/{id}`: Actualizar un registro por ID
- `DELETE /api/{entidad}/{id}`: Eliminar un registro por ID

> Ejemplo para la entidad Teacher:
> 
> - `GET /api/teachers`
> - `POST /api/teachers`
> - `PUT /api/teachers/{id}`
> - `DELETE /api/teachers/{id}`

Estos endpoints se pueden probar con herramientas como **Postman** o **cURL**.

---

##  Ejemplo de JSON para POST (Teacher)

```json
{
  "nombre": "Carlos Pérez"
}
```

##  Ejemplo de JSON para POST (Subject)

```json
{
  "nombre": "Matemáticas",
  "teacher": {
    "id": 1
  }
}
```

##  Ejemplo de JSON para POST (Student)

```json
{
  "nombre": "Lucía",
  "apellido": "Gómez",
  "identificacion": "12345678X",
  "subjects": [
    { "id": 1 },
    { "id": 2 }
  ]
}
```

