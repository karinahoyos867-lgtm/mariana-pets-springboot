
package com.marianapets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Gestiona la página principal de Mariana Pets.
 */
@Controller
public class InicioController {

    /**
     * Muestra la página de inicio.
     */
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
}
