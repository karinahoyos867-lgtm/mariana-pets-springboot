
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
 */
public class MascotaDAO {

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

    public List<Map<String, Object>> listar() throws SQLException {
        List<Map<String, Object>> mascotas = new ArrayList<>();

        String sql = "SELECT id, nombre, especie, raza, edad, propietario "
                + "FROM mascotas ORDER BY id";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultados = consulta.executeQuery()) {

            while (resultados.next()) {
                mascotas.add(mapearMascota(resultados));
            }
        }

        return mascotas;
    }

    public Map<String, Object> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, especie, raza, edad, propietario "
                + "FROM mascotas WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);

            try (ResultSet resultados = consulta.executeQuery()) {
                if (resultados.next()) {
                    return mapearMascota(resultados);
                }
            }
        }

        return null;
    }

    public boolean actualizar(int id, String nombre, String especie,
                              String raza, int edad, String propietario)
            throws SQLException {

        String sql = "UPDATE mascotas SET nombre = ?, especie = ?, "
                + "raza = ?, edad = ?, propietario = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, nombre);
            consulta.setString(2, especie);
            consulta.setString(3, raza);
            consulta.setInt(4, edad);
            consulta.setString(5, propietario);
            consulta.setInt(6, id);

            return consulta.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM mascotas WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, id);
            return consulta.executeUpdate() > 0;
        }
    }

    private Map<String, Object> mapearMascota(ResultSet resultados)
            throws SQLException {

        Map<String, Object> mascota = new LinkedHashMap<>();
        mascota.put("id", resultados.getInt("id"));
        mascota.put("nombre", resultados.getString("nombre"));
        mascota.put("especie", resultados.getString("especie"));
        mascota.put("raza", resultados.getString("raza"));
        mascota.put("edad", resultados.getInt("edad"));
        mascota.put("propietario", resultados.getString("propietario"));

        return mascota;
    }
}
