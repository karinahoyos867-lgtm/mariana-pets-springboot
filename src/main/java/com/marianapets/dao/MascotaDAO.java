
package com.marianapets.dao;

import com.marianapets.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestiona las operaciones de acceso a datos de las mascotas.
 * Centraliza las consultas SQL y utiliza la conexión JDBC existente.
 */
public class MascotaDAO {

    /**
     * Registra una mascota en la base de datos.
     *
     * @param nombre nombre de la mascota
     * @param especie especie de la mascota
     * @param raza raza de la mascota
     * @param edad edad de la mascota
     * @param propietario nombre de su propietario
     * @return true si la mascota fue registrada
     * @throws SQLException si ocurre un error de base de datos
     */
    public boolean registrar(String nombre, String especie, String raza,
                             int edad, String propietario)
            throws SQLException {

        String sql = "INSERT INTO mascotas "
                + "(nombre, especie, raza, edad, propietario) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, nombre);
            consulta.setString(2, especie);
            consulta.setString(3, raza);
            consulta.setInt(4, edad);
            consulta.setString(5, propietario);

            return consulta.executeUpdate() > 0;
        }
    }

    /**
     * Consulta todas las mascotas ordenadas por su identificador.
     *
     * @return lista de mascotas con sus datos
     * @throws SQLException si ocurre un error de base de datos
     */
    public List<Map<String, Object>> listar() throws SQLException {

        List<Map<String, Object>> mascotas = new ArrayList<>();

        String sql = "SELECT id, nombre, especie, raza, edad, propietario "
                + "FROM mascotas ORDER BY id";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultados = consulta.executeQuery()) {

            while (resultados.next()) {
                Map<String, Object> mascota = new LinkedHashMap<>();

                mascota.put("id", resultados.getInt("id"));
                mascota.put("nombre", resultados.getString("nombre"));
                mascota.put("especie", resultados.getString("especie"));
                mascota.put("raza", resultados.getString("raza"));
                mascota.put("edad", resultados.getInt("edad"));
                mascota.put("propietario",
                        resultados.getString("propietario"));

                mascotas.add(mascota);
            }
        }

        return mascotas;
    }
}
