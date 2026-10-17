
package com.marianapets.servlet;

import com.marianapets.dao.MascotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controla las solicitudes para consultar las mascotas registradas.
 * Delega las consultas de datos a la capa DAO.
 */
@WebServlet("/listar-mascotas")
public class ListarMascotasServlet extends HttpServlet {

    private final MascotaDAO mascotaDAO = new MascotaDAO();

    /**
     * Consulta las mascotas y las envía a la página JSP.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Map<String, Object>> mascotas = mascotaDAO.listar();

            request.setAttribute("mascotas", mascotas);
            request.getRequestDispatcher("/mascotas.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Error al consultar las mascotas.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No fue posible consultar las mascotas.");
        }
    }
}
