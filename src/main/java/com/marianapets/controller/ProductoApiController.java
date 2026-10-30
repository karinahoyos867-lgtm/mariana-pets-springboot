package com.marianapets.controller;

import com.marianapets.dao.ProductoDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * API REST para gestionar el inventario de productos desde React.
 */
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoApiController {

    private final ProductoDAO productoDAO = new ProductoDAO();

    @GetMapping
    public List<Map<String, Object>> listarProductos() throws SQLException {
        return productoDAO.listar();
    }

    @GetMapping("/{id}")
    public Map<String, Object> buscarProducto(@PathVariable long id)
            throws SQLException {
        validarId(id);
        Map<String, Object> producto = productoDAO.buscarPorId(id);
        if (producto == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró el producto.");
        }
        return producto;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarProducto(
            @RequestBody Map<String, Object> datos) throws SQLException {
        DatosProducto producto = validarDatos(datos);
        boolean registrado = productoDAO.registrar(
                producto.codigo(), producto.nombre(), producto.descripcion(),
                producto.categoria(), producto.proveedor(), producto.cantidad(),
                producto.stockMinimo(), producto.precio(),
                producto.fechaIngreso(), producto.fechaVencimiento());

        if (!registrado) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible registrar el producto.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("mensaje", "Producto registrado correctamente."));
    }

    @PutMapping("/{id}")
    public Map<String, Object> actualizarProducto(
            @PathVariable long id,
            @RequestBody Map<String, Object> datos) throws SQLException {
        validarId(id);
        if (productoDAO.buscarPorId(id) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró el producto.");
        }

        DatosProducto producto = validarDatos(datos);
        boolean actualizado = productoDAO.actualizar(
                id, producto.codigo(), producto.nombre(),
                producto.descripcion(), producto.categoria(),
                producto.proveedor(), producto.cantidad(),
                producto.stockMinimo(), producto.precio(),
                producto.fechaIngreso(), producto.fechaVencimiento());

        if (!actualizado) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible actualizar el producto.");
        }
        return Map.of("mensaje", "Producto actualizado correctamente.");
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> eliminarProducto(@PathVariable long id)
            throws SQLException {
        validarId(id);
        if (!productoDAO.eliminar(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró el producto.");
        }
        return Map.of("mensaje", "Producto eliminado correctamente.");
    }

    private DatosProducto validarDatos(Map<String, Object> datos) {
        if (datos == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Debes enviar los datos.");
        }

        String codigo = texto(datos, "codigo");
        String nombre = texto(datos, "nombre");
        String descripcion = textoOpcional(datos, "descripcion");
        String categoria = texto(datos, "categoria");
        String proveedor = texto(datos, "proveedor");

        if (codigo.isBlank() || nombre.isBlank() || categoria.isBlank()
                || proveedor.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Código, nombre, categoría y proveedor son obligatorios.");
        }

        int cantidad = enteroNoNegativo(datos.get("cantidad"), "cantidad");
        int stockMinimo = enteroNoNegativo(
                datos.get("stock_minimo"), "stock mínimo");
        BigDecimal precio = precioNoNegativo(datos.get("precio"));
        LocalDate fechaIngreso = fecha(datos.get("fecha_ingreso"),
                "fecha de ingreso", true);
        LocalDate fechaVencimiento = fecha(datos.get("fecha_vencimiento"),
                "fecha de vencimiento", false);

        return new DatosProducto(codigo, nombre, descripcion, categoria,
                proveedor, cantidad, stockMinimo, precio, fechaIngreso,
                fechaVencimiento);
    }

    private void validarId(long id) {
        if (id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El ID debe ser positivo.");
        }
    }

    private String texto(Map<String, Object> datos, String campo) {
        Object valor = datos.get(campo);
        return valor == null ? "" : valor.toString().trim();
    }

    private String textoOpcional(Map<String, Object> datos, String campo) {
        String valor = texto(datos, campo);
        return valor.isBlank() ? null : valor;
    }

    private int enteroNoNegativo(Object valor, String campo) {
        if (valor == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El campo " + campo + " es obligatorio.");
        }
        try {
            int numero = new BigDecimal(valor.toString()).intValueExact();
            if (numero < 0) {
                throw new ArithmeticException();
            }
            return numero;
        } catch (NumberFormatException | ArithmeticException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El campo " + campo
                            + " debe ser un entero no negativo.");
        }
    }

    private BigDecimal precioNoNegativo(Object valor) {
        if (valor == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio es obligatorio.");
        }
        try {
            BigDecimal precio = new BigDecimal(valor.toString())
                    .setScale(2, RoundingMode.UNNECESSARY);
            if (precio.signum() < 0
                    || precio.compareTo(new BigDecimal("99999999.99")) > 0) {
                throw new ArithmeticException();
            }
            return precio;
        } catch (NumberFormatException | ArithmeticException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio debe ser un número no negativo con máximo "
                            + "dos decimales.");
        }
    }

    private LocalDate fecha(Object valor, String campo, boolean obligatoria) {
        if (valor == null || valor.toString().isBlank()) {
            if (!obligatoria) {
                return null;
            }
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La " + campo + " es obligatoria.");
        }
        try {
            return LocalDate.parse(valor.toString().trim());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La " + campo + " debe tener formato AAAA-MM-DD.");
        }
    }

    private record DatosProducto(
            String codigo,
            String nombre,
            String descripcion,
            String categoria,
            String proveedor,
            int cantidad,
            int stockMinimo,
            BigDecimal precio,
            LocalDate fechaIngreso,
            LocalDate fechaVencimiento) {
    }
}
