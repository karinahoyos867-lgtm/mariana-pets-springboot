package com.marianapets.controller;

import com.marianapets.dao.MascotaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Gestiona la consulta de mascotas registradas mediante Spring MVC.
 */
@Controller
public class ListarMascotasController {

    private final MascotaDAO mascotaDAO = new MascotaDAO();

    /**
     * Consulta las mascotas y prepara los datos para la vista.
     */
    @GetMapping("/listar-mascotas")
    public String listarMascotas(Model model) {
        try {
            List<Map<String, Object>> mascotas = mascotaDAO.listar();
            model.addAttribute("mascotas", mascotas);
            return "mascotas";
        } catch (SQLException e) {
            model.addAttribute("mensaje",
                    "No fue posible consultar las mascotas. Verifica la conexión a la base de datos.");
            return "resultado";
        }
    }
}
