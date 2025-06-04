
# Proyecto Spring Boot - Biblioteca (TP8)

Este proyecto es una aplicación web de gestión de una **biblioteca**, desarrollada en **Spring Boot 3.5.0**, con persistencia en **MySQL**. Permite gestionar libros, autores, lectores, copias de libros y préstamos. Se han implementado las reglas de negocio para préstamos, devoluciones, estados de copias y multas por retrasos.

---

## Tecnologías y dependencias utilizadas

- **Spring Boot 3.5.0**
- **Spring Web** – Controladores REST y manejo de peticiones HTTP.
- **Spring Data JPA** – Persistencia y operaciones CRUD.
- **MySQL Driver** – Conexión con base de datos MySQL.
- **Lombok** – Anotaciones para reducir código repetitivo.
- **Thymeleaf**  – Motor de plantillas para vistas HTML.

---

## Requisitos previos

- **Java 17 o superior**
- **Maven**
- **MySQL Server**

---

## Configuración

1. Clona el repositorio:
   ```bash
   git clone https://github.com/TuUsuarioGit/BibliotecaTP8.git
   ```

2. Crea una base de datos en MySQL con el nombre definido en `application.properties`.

3. Configura el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_tp8
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.thymeleaf.prefix=classpath:/templates/
   spring.thymeleaf.suffix=.html
   spring.mvc.view.prefix=/templates/
   spring.mvc.view.suffix=.html

   ```

4. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

---

## Modelo de datos

### Entidades principales

#### `Autor`
- `id`: Identificador único
- `nombre`
- `nacionalidad`
- `fechaNacimiento`

#### `Libro`
- `id`
- `nombre`
- `tipo` (novela, teatro, poesía, ensayo)
- `editorial`
- `año`
- `autor` (relación con `Autor`)
- **Relación**: Un libro puede tener muchas copias (`@OneToMany`)

#### `Copia`
- `id`
- `isbn`
- `estado` (`EN_BIBLIOTECA`, `PRESTADA`, `RETRASADA`, `EN_REPARACION`, `BAJA`)
- `cantidadTotal`
- `cantidadDisponible`
- **Relación**: Pertenece a un `Libro`

#### `Lector`
- `id`
- `nombre`
- `apellido`
- `identificacion`
- Puede tener **máximo 3 préstamos activos**
- Tiene métodos para aplicar **multas**

#### `Prestamo`
- `id`
- `lector` (relación con `Lector`)
- `copia` (relación con `Copia`)
- `fechaRetiro` (máximo 30 días)
- `fechaDevolucion`
- **Reglas**:
  - Por cada día de retraso, se aplican 2 días sin poder pedir un nuevo libro.
  - Si hay multa activa, el lector no puede pedir libros.

---

## Lógica de negocio

- Validación de préstamos:
  - Verifica que el lector no tenga más de 3 préstamos activos.
  - Verifica que la copia esté disponible y en buen estado.
  - Aplica fecha actual con `LocalDate.now()` al hacer un préstamo.
- Devoluciones:
  - Se verifica el estado físico del libro.
  - Se actualiza el estado de la copia: `DISPONIBLE`, `EN_REPARACION`, o `BAJA`.
- Se calculan multas si la devolución supera los 30 días.
- Se actualizan los contadores de copias disponibles y prestadas.

---

## Simulación solicitada

- Cargar 10 libros con varias copias.
- Prestar 3 libros al lector A.
- Prestar 2 libros al lector B.
- Mostrar por consola el **stock de libros** disponibles.

---

## Arquitectura del proyecto

```
com.biblioteca.tp8
├── controller        → Endpoints REST
├── service           → Reglas de negocio
├── repository        → Interfaces JpaRepository
├── model             → Entidades JPA
└── BibliotecaTp8Application.java → Clase main
```

---

## Endpoints REST

| Método | Endpoint              | Descripción               |
| ------ | --------------------- | ------------------------- |
| GET    | `/autors/getAll`      | Obtener todos los autores |
| GET    | `/autors/{id}`        | Obtener autor por ID      |
| POST   | `/autors/create`      | Crear un nuevo autor      |
| PUT    | `/autors/update/{id}` | Actualizar autor por ID   |
| DELETE | `/autors/delete/{id}` | Eliminar autor por ID     |

| Método | Endpoint              | Descripción              |
| ------ | --------------------- | ------------------------ |
| GET    | `/copias/getAll`      | Obtener todas las copias |
| GET    | `/copias/{id}`        | Obtener copia por ID     |
| POST   | `/copias/create`      | Crear una nueva copia    |
| PUT    | `/copias/update/{id}` | Actualizar copia por ID  |
| DELETE | `/copias/delete/{id}` | Eliminar copia por ID    |

| Método | Endpoint                | Descripción                |
| ------ | ----------------------- | -------------------------- |
| GET    | `/lectores/getAll`      | Obtener todos los lectores |
| GET    | `/lectores/{id}`        | Obtener lector por ID      |
| POST   | `/lectores/create`      | Crear un nuevo lector      |
| PUT    | `/lectores/update/{id}` | Actualizar lector por ID   |
| DELETE | `/lectores/delete/{id}` | Eliminar lector por ID     |

| Método | Endpoint              | Descripción              |
| ------ | --------------------- | ------------------------ |
| GET    | `/libros/getAll`      | Obtener todos los libros |
| GET    | `/libros/{id}`        | Obtener libro por ID     |
| POST   | `/libros/create`      | Crear un nuevo libro     |
| PUT    | `/libros/update/{id}` | Actualizar libro por ID  |
| DELETE | `/libros/delete/{id}` | Eliminar libro por ID    |

| Método | Endpoint       | Descripción              |
| ------ | -------------- | ------------------------ |
| GET    | `/multas`      | Obtener todas las multas |
| GET    | `/multas/{id}` | Obtener multa por ID     |
| POST   | `/multas`      | Crear una nueva multa    |
| PUT    | `/multas/{id}` | Actualizar multa por ID  |
| DELETE | `/multas/{id}` | Eliminar multa por ID    |


---

## Ejmeplos de JSON

### POST de autores

```json
{
  "nombre": "Gabriel García Márquez",
  "nacionalidad": "Colombiana",
  "fechaNacimiento": "1927-03-06"
}
```

### POST de autores

```json
{
  "nombre": "Gabriel García Márquez",
  "nacionalidad": "Colombiana",
  "fechaNacimiento": "1927-03-06"
}
```

### POST de libros

```json
{
  "nombre": "Cien años de soledad",
  "tipo": "novela",
  "editorial": "Sudamericana",
  "anio": 1967,
  "autor": {
    "id": 1
  }
}
```

### POST de copias
```json
{
  "isbn": "978-84-376-0494-7",
  "estado": "EN_BIBLIOTECA",
  "cantidadTotal": 5,
  "cantidadDisponible": 5,
  "libro": {
    "id": 1
  }
}
```

### POST de lectores
```json
{
  "nombre": "Lucía",
  "apellido": "Gómez",
  "identificacion": "12345678X"
}

```

### POST de préstamo

```json
{
  "lectorId": 1,
  "copiaId": 5
}
```

