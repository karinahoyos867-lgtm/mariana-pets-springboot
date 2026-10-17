package com.marianapets.controller;

import com.marianapets.dao.MascotaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

/**
 * Gestiona el registro de mascotas mediante Spring MVC.
 */
@Controller
public class MascotaController {

    private final MascotaDAO mascotaDAO = new MascotaDAO();

    /**
     * Presenta el formulario de registro.
     */
    @GetMapping("/registrar-mascota")
    public String mostrarFormulario() {
        return "registro-mascota";
    }

    /**
     * Valida y registra una mascota en la base de datos.
     */
    @PostMapping("/registrar-mascota")
    public String registrarMascota(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String raza,
            @RequestParam(required = false) String edad,
            @RequestParam(required = false) String propietario,
            Model model) {

        if (nombre == null || nombre.isBlank()
                || especie == null || especie.isBlank()
                || raza == null || raza.isBlank()
                || edad == null || edad.isBlank()
                || propietario == null || propietario.isBlank()) {

            model.addAttribute("mensaje",
                    "Todos los campos son obligatorios.");
            return "resultado";
        }

        try {
            int edadMascota = Integer.parseInt(edad);

            if (edadMascota < 0) {
                model.addAttribute("mensaje",
                        "La edad debe ser un número entero igual o mayor que cero.");
                return "resultado";
            }

            boolean registrado = mascotaDAO.registrar(
                    nombre.trim(),
                    especie.trim(),
                    raza.trim(),
                    edadMascota,
                    propietario.trim()
            );

            model.addAttribute("mensaje", registrado
                    ? "La mascota se registró correctamente."
                    : "No fue posible registrar la mascota.");

        } catch (NumberFormatException e) {
            model.addAttribute("mensaje",
                    "La edad debe ser un número entero igual o mayor que cero.");

        } catch (SQLException e) {
            model.addAttribute("mensaje",
                    "No fue posible registrar la mascota. Verifica la conexión a la base de datos.");
        }

        return "resultado";
    }
}
