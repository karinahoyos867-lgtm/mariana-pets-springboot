
package com.marianapets.controller;

import com.marianapets.dao.MascotaDAO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * API REST para gestionar mascotas desde React.
 */
@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "http://localhost:5173")
public class MascotaApiController {

    private final MascotaDAO mascotaDAO = new MascotaDAO();

    @GetMapping
    public List<Map<String, Object>> listarMascotas()
            throws SQLException {
        return mascotaDAO.listar();
    }

    @GetMapping("/{id}")
    public Map<String, Object> buscarMascota(@PathVariable int id)
            throws SQLException {
        if (id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El ID debe ser positivo.");
        }

        Map<String, Object> mascota = mascotaDAO.buscarPorId(id);

        if (mascota == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró la mascota.");
        }

        return mascota;
    }

    @PostMapping
    public Map<String, Object> registrarMascota(
            @RequestBody Map<String, Object> datos)
            throws SQLException {

        DatosMascota mascota = validarDatos(datos);

        boolean registrado = mascotaDAO.registrar(
                mascota.nombre(), mascota.especie(), mascota.raza(),
                mascota.edad(), mascota.propietario());

        if (!registrado) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible registrar la mascota.");
        }

        return Map.of("mensaje", "Mascota registrada correctamente.");
    }

    @PutMapping("/{id}")
    public Map<String, Object> actualizarMascota(
            @PathVariable int id,
            @RequestBody Map<String, Object> datos)
            throws SQLException {

        if (id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El ID debe ser positivo.");
        }

        if (mascotaDAO.buscarPorId(id) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró la mascota.");
        }

        DatosMascota mascota = validarDatos(datos);

        boolean actualizado = mascotaDAO.actualizar(
                id, mascota.nombre(), mascota.especie(), mascota.raza(),
                mascota.edad(), mascota.propietario());

        if (!actualizado) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible actualizar la mascota.");
        }

        return Map.of("mensaje", "Mascota actualizada correctamente.");
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> eliminarMascota(@PathVariable int id)
            throws SQLException {

        if (id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El ID debe ser positivo.");
        }

        boolean eliminado = mascotaDAO.eliminar(id);

        if (!eliminado) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró la mascota.");
        }

        return Map.of("mensaje", "Mascota eliminada correctamente.");
    }

    private DatosMascota validarDatos(Map<String, Object> datos) {
        if (datos == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Debes enviar los datos.");
        }

        String nombre = texto(datos, "nombre");
        String especie = texto(datos, "especie");
        String raza = texto(datos, "raza");
        String propietario = texto(datos, "propietario");
        Object edadRecibida = datos.get("edad");

        if (nombre.isBlank() || especie.isBlank()
                || raza.isBlank() || propietario.isBlank()
                || edadRecibida == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Todos los campos son obligatorios.");
        }

        int edad;
        try {
            if (edadRecibida instanceof Number numero) {
                double valor = numero.doubleValue();
                if (!Double.isFinite(valor)
                        || valor != Math.rint(valor)
                        || valor < 0 || valor > Integer.MAX_VALUE) {
                    throw new NumberFormatException();
                }
                edad = (int) valor;
            } else {
                edad = Integer.parseInt(edadRecibida.toString().trim());
            }
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La edad debe ser un número entero no negativo.");
        }

        if (edad < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La edad no puede ser negativa.");
        }

        return new DatosMascota(
                nombre, especie, raza, edad, propietario);
    }

    private String texto(Map<String, Object> datos, String campo) {
        Object valor = datos.get(campo);
        return valor == null ? "" : valor.toString().trim();
    }

    private record DatosMascota(
            String nombre,
            String especie,
            String raza,
            int edad,
            String propietario) {
    }
}
