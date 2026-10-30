package com.marianapets.dao;

import com.marianapets.util.Conexion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestiona las operaciones de acceso a datos de los productos.
 */
public class ProductoDAO {

    private static final String COLUMNAS =
            "id, codigo, nombre, descripcion, categoria, proveedor, "
            + "cantidad, stock_minimo, precio, fecha_ingreso, fecha_vencimiento";

    public boolean registrar(String codigo, String nombre, String descripcion,
                             String categoria, String proveedor, int cantidad,
                             int stockMinimo, BigDecimal precio,
                             LocalDate fechaIngreso, LocalDate fechaVencimiento)
            throws SQLException {

        String sql = "INSERT INTO productos "
                + "(codigo, nombre, descripcion, categoria, proveedor, "
                + "cantidad, stock_minimo, precio, fecha_ingreso, "
                + "fecha_vencimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            asignarDatos(consulta, codigo, nombre, descripcion, categoria,
                    proveedor, cantidad, stockMinimo, precio, fechaIngreso,
                    fechaVencimiento);
            return consulta.executeUpdate() > 0;
        }
    }

    public List<Map<String, Object>> listar() throws SQLException {
        List<Map<String, Object>> productos = new ArrayList<>();
        String sql = "SELECT " + COLUMNAS + " FROM productos ORDER BY nombre";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultados = consulta.executeQuery()) {
            while (resultados.next()) {
                productos.add(mapearProducto(resultados));
            }
        }
        return productos;
    }

    public Map<String, Object> buscarPorId(long id) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM productos WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setLong(1, id);
            try (ResultSet resultados = consulta.executeQuery()) {
                if (resultados.next()) {
                    return mapearProducto(resultados);
                }
            }
        }
        return null;
    }

    public boolean actualizar(long id, String codigo, String nombre,
                              String descripcion, String categoria,
                              String proveedor, int cantidad, int stockMinimo,
                              BigDecimal precio, LocalDate fechaIngreso,
                              LocalDate fechaVencimiento)
            throws SQLException {

        String sql = "UPDATE productos SET codigo = ?, nombre = ?, "
                + "descripcion = ?, categoria = ?, proveedor = ?, "
                + "cantidad = ?, stock_minimo = ?, precio = ?, "
                + "fecha_ingreso = ?, fecha_vencimiento = ? WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            asignarDatos(consulta, codigo, nombre, descripcion, categoria,
                    proveedor, cantidad, stockMinimo, precio, fechaIngreso,
                    fechaVencimiento);
            consulta.setLong(11, id);
            return consulta.executeUpdate() > 0;
        }
    }

    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setLong(1, id);
            return consulta.executeUpdate() > 0;
        }
    }

    private void asignarDatos(PreparedStatement consulta, String codigo,
                              String nombre, String descripcion,
                              String categoria, String proveedor,
                              int cantidad, int stockMinimo, BigDecimal precio,
                              LocalDate fechaIngreso,
                              LocalDate fechaVencimiento)
            throws SQLException {
        consulta.setString(1, codigo);
        consulta.setString(2, nombre);
        if (descripcion == null) {
            consulta.setNull(3, Types.VARCHAR);
        } else {
            consulta.setString(3, descripcion);
        }
        consulta.setString(4, categoria);
        consulta.setString(5, proveedor);
        consulta.setInt(6, cantidad);
        consulta.setInt(7, stockMinimo);
        consulta.setBigDecimal(8, precio);
        consulta.setDate(9, Date.valueOf(fechaIngreso));
        if (fechaVencimiento == null) {
            consulta.setNull(10, Types.DATE);
        } else {
            consulta.setDate(10, Date.valueOf(fechaVencimiento));
        }
    }

    private Map<String, Object> mapearProducto(ResultSet resultados)
            throws SQLException {
        Map<String, Object> producto = new LinkedHashMap<>();
        producto.put("id", resultados.getLong("id"));
        producto.put("codigo", resultados.getString("codigo"));
        producto.put("nombre", resultados.getString("nombre"));
        producto.put("descripcion", resultados.getString("descripcion"));
        producto.put("categoria", resultados.getString("categoria"));
        producto.put("proveedor", resultados.getString("proveedor"));
        producto.put("cantidad", resultados.getInt("cantidad"));
        producto.put("stock_minimo", resultados.getInt("stock_minimo"));
        producto.put("precio", resultados.getBigDecimal("precio"));
        producto.put("fecha_ingreso",
                resultados.getDate("fecha_ingreso").toString());
        Date vencimiento = resultados.getDate("fecha_vencimiento");
        producto.put("fecha_vencimiento",
                vencimiento == null ? null : vencimiento.toString());
        return producto;
    }
}
