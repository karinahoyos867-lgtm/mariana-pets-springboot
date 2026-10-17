
package com.marianapets.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión entre Mariana Pets y la base de datos MySQL.
 * La contraseña se obtiene de una variable de entorno por seguridad.
 */
public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/mariana_pets";

    private static final String USUARIO = "root";

    /**
     * Establece una conexión con la base de datos.
     *
     * @return conexión activa con MySQL
     * @throws SQLException si falta el controlador, la contraseña
     *                      o no es posible establecer la conexión
     */
    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "No se encontró el controlador JDBC de MySQL.", e
            );
        }

        String password = System.getenv("MARIANA_DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new SQLException(
                    "Falta configurar la variable MARIANA_DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(URL, USUARIO, password);
    }
}
