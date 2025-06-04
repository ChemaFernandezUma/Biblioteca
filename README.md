
# Proyecto Spring Boot - Biblioteca (TP8)

Este proyecto es una aplicación web de gestión de una **biblioteca**, desarrollada en **Spring Boot 3.5.0**, con persistencia en **MySQL**. Permite gestionar libros, autores, lectores, copias de libros y préstamos. Se han implementado las reglas de negocio para préstamos, devoluciones, estados de copias y multas por retrasos.

---

## Tecnologías y dependencias utilizadas

- **Spring Boot 3.5.0**
- **Spring Web** – Controladores REST y manejo de peticiones HTTP.
- **Spring Data JPA** – Persistencia y operaciones CRUD.
- **MySQL Driver** – Conexión con base de datos MySQL.
- **Lombok** – Anotaciones para reducir código repetitivo.
- **Thymeleaf** *(opcional)* – Motor de plantillas para vistas HTML.

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
├── dto               → Objetos de transferencia de datos (opcional)
└── BibliotecaTp8Application.java → Clase main
```

---

## Endpoints REST

- `GET /api/libros`
- `GET /api/copias`
- `GET /api/autores`
- `GET /api/lectores`
- `GET /api/prestamos`
- `POST /api/prestamos` → Validado contra reglas de negocio
- `PUT /api/devoluciones/{id}` → Procesa devolución y estado de la copia

---

## Ejemplo de JSON para POST de préstamo

```json
{
  "lectorId": 1,
  "copiaId": 5
}
```

---

## Consideraciones extra

- En la clase `Lector`, el método `multar()` debe ser **público**.
- La entidad `Libro` puede tener los atributos `nombreAutor`, `fechaNacAutor`, `nacionalidadAutor` si no se modela una clase `Autor` separada.
- Validaciones implementadas para préstamo:
  - Estado de la copia
  - Disponibilidad
  - Multas del lector
  - Cantidad máxima de préstamos
