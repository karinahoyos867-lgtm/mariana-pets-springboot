
# Mariana Pets — Codificación de módulos

## 1. Tecnologías seleccionadas

Se utiliza Java 21 con Jakarta Servlet y Jakarta Server Pages (JSP), ejecutados en Apache Tomcat 10.1. La aplicación sigue una organización por capas inspirada en MVC: los servlets gestionan las solicitudes, la capa DAO centraliza las operaciones de acceso a datos y la clase Conexion administra la conexión con MySQL.

Esta selección permite conservar los módulos web ya implementados y probados, separar responsabilidades y facilitar el mantenimiento del código. Maven administra las dependencias y genera el archivo WAR para el despliegue.

## 2. Tecnologías utilizadas

- Java JDK 21.
- Jakarta Servlet y JSP.
- Apache Tomcat 10.1.
- Apache Maven 3.10.
- MySQL y MySQL Connector/J.
- HTML y formularios JSP.
- Git y GitHub para el control de versiones.

## 3. Integración con la base de datos

La aplicación utiliza la base de datos `mariana_pets` y la tabla `mascotas`. La conexión JDBC se configura en `Conexion.java`.

Por seguridad, la contraseña de MySQL se obtiene de la variable de entorno `MARIANA_DB_PASSWORD`; no debe escribirse directamente en el código fuente.

## 4. Módulos web implementados

- Registro de mascotas mediante `RegistrarMascotaServlet`.
- Consulta de mascotas mediante `ListarMascotasServlet`.
- Acceso a datos centralizado en `MascotaDAO`.
- Formularios y resultados presentados mediante JSP.

## 5. Configuración y ejecución

1. Instalar Java JDK 21, Maven 3.10 y Apache Tomcat 10.1.
2. Configurar MySQL y verificar que exista la base de datos `mariana_pets` con la tabla `mascotas`.
3. Configurar la variable de entorno `MARIANA_DB_PASSWORD` con la contraseña de MySQL.
4. Abrir una terminal en la carpeta del proyecto.
5. Ejecutar `mvn clean package`.
6. Copiar `target/mariana-pets-web.war` a la carpeta `webapps` de Tomcat.
7. Iniciar Tomcat y acceder a las rutas de registro y consulta de mascotas.

## 6. Estándares de codificación

Se utilizan nombres descriptivos para clases y métodos, paquetes organizados por responsabilidad, consultas SQL parametrizadas mediante `PreparedStatement`, cierre de recursos con `try-with-resources` y comentarios para explicar responsabilidades relevantes.

## 7. Control de versiones

Repositorio: https://github.com/karinahoyos867-lgtm/mariana-pets

La rama `master` contiene los cambios versionados del proyecto. Los módulos de registro y consulta se verificaron en ejecución y los cambios se publicaron en GitHub.
