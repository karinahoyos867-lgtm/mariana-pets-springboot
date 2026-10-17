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