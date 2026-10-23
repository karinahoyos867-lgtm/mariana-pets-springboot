# Mariana Pets — Adaptación a Spring Boot

## 1. Descripción del proyecto

Mariana Pets es una aplicación web para registrar y consultar mascotas. En esta versión se adapta el proyecto al framework Spring Boot, manteniendo la organización del código por responsabilidades y una interfaz web con HTML y JSP.

## 2. Tecnologías utilizadas

- Java JDK 21.
- Spring Boot.
- Spring MVC para gestionar las solicitudes web mediante controladores.
- JSP para las vistas.
- Maven para administrar dependencias y construir el proyecto.
- MySQL y MySQL Connector/J para la persistencia de datos.
- Git y GitHub para el control de versiones.

## 3. Estructura del proyecto

- `MarianaPetsApplication.java`: punto de entrada de la aplicación.
- `controller`: controladores para inicio, registro y consulta de mascotas.
- `dao`: acceso a los datos de las mascotas.
- `util/Conexion.java`: configuración de la conexión con MySQL.
- `src/main/resources/application.properties`: configuración de la aplicación.
- `src/main/webapp`: vistas JSP.

## 4. Funcionalidades implementadas

- Página de inicio.
- Registro de mascotas.
- Consulta de mascotas registradas.
- Integración con la base de datos MySQL.

Las páginas de inicio, registro y consulta se verificaron en ejecución. El listado mostró las dos mascotas registradas durante las pruebas.

## 5. Base de datos y seguridad

La aplicación utiliza la base de datos `mariana_pets` y la tabla `mascotas`.

La contraseña de MySQL debe configurarse mediante la variable de entorno `MARIANA_DB_PASSWORD`. No se debe almacenar una contraseña real en el repositorio.

## 6. Construcción y ejecución

Requisitos: Java JDK 21, Maven y una instancia de MySQL configurada.

1. Configurar la variable de entorno `MARIANA_DB_PASSWORD`.
2. Verificar la configuración de la conexión en `application.properties` y `Conexion.java`.
3. Abrir una terminal en la carpeta del proyecto.
4. Ejecutar `mvn clean package`.
5. Iniciar la aplicación con `java -jar .\target\mariana-pets-springboot.war`.
6. Abrir `http://localhost:8080/` en el navegador.

## 7. Control de versiones

Repositorio GitHub: https://github.com/karinahoyos867-lgtm/mariana-pets-springboot

El proyecto utiliza Git para registrar los cambios y GitHub para alojar el código fuente.


## 8. API REST de mascotas

La API REST permite gestionar las mascotas registradas en la base de datos MySQL del proyecto Mariana Pets. La ruta base es `/api/mascotas`.

### 8.1 Endpoints disponibles

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| GET | `/api/mascotas` | Consulta todas las mascotas. |
| GET | `/api/mascotas/{id}` | Consulta una mascota por su identificador. |
| POST | `/api/mascotas` | Registra una mascota nueva. |
| PUT | `/api/mascotas/{id}` | Actualiza los datos de una mascota existente. |
| DELETE | `/api/mascotas/{id}` | Elimina una mascota por su identificador. |

### 8.2 Datos de entrada

Los endpoints POST y PUT reciben un objeto JSON con los siguientes campos:

```json
{
  "nombre": "Luna",
  "especie": "Perro",
  "raza": "Criolla",
  "edad": 3,
  "propietario": "María"
}
```

Todos los campos son obligatorios. La edad debe ser un número entero no negativo. Para actualizar o eliminar una mascota, se utiliza su identificador en la URL.

### 8.3 Respuestas y códigos HTTP

- **200 OK:** operación atendida correctamente; se utiliza en las pruebas de consulta y actualización.
- **400 Bad Request:** datos incompletos, edad inválida o identificador no positivo.
- **404 Not Found:** la mascota solicitada no existe.
- **500 Internal Server Error:** no fue posible completar una operación de registro o actualización.

El registro y la eliminación devuelven un mensaje de confirmación cuando la operación se completa correctamente.

### 8.4 Validaciones implementadas

- Verificación de los campos obligatorios.
- Rechazo de edades negativas o que no sean enteros válidos.
- Verificación de que el identificador sea positivo.
- Respuesta de error cuando no existe la mascota consultada, actualizada o eliminada.

### 8.5 Pruebas realizadas con Postman

Se verificaron las siguientes operaciones:

- Consulta de todas las mascotas.
- Consulta de una mascota por ID.
- Registro de una mascota y comprobación de su aparición en el listado.
- Actualización de una mascota y comprobación de los nuevos datos.
- Eliminación de una mascota y comprobación de la respuesta 404 posterior.
- Rechazo de registros con campos obligatorios incompletos.
- Rechazo de edades negativas.
- Rechazo de identificadores iguales a cero.
- Respuesta 404 para un identificador inexistente.
