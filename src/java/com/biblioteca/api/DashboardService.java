package com.biblioteca.api;

import com.biblioteca.config.Conexion;
import com.google.gson.JsonObject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// Servlet que expone un endpoint para obtener estadísticas del dashboard
@WebServlet("/api/dashboard")
public class DashboardService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Configuramos la respuesta como JSON con codificación UTF-8
        resp.setContentType("application/json;charset=UTF-8");
        JsonObject json = new JsonObject();

        // Conexión a la base de datos
        try (Connection con = Conexion.getConnection()) {
            int total = 0;
            int prestados = 0;

            // Consulta para obtener el total de ejemplares (sumando la cantidad de todos los libros)
            try (PreparedStatement ps = con.prepareStatement("SELECT SUM(cantidad) FROM libros");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) total = rs.getInt(1);
            }

            // Consulta para contar los préstamos activos (estado = 'Prestado')
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM prestamos WHERE estado='Prestado'");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) prestados = rs.getInt(1);
            }

            // Calculamos los disponibles como diferencia entre total y prestados
            int disponibles = total - prestados;

            // Construimos el objeto JSON con las estadísticas
            json.addProperty("totalLibros", total);
            json.addProperty("librosPrestados", prestados);
            json.addProperty("librosDisponibles", disponibles);

        } catch (Exception e) {
            // En caso de error, lo imprimimos en consola (se recomienda usar un logger en producción)
            e.printStackTrace();
        }

        // Enviamos la respuesta JSON al cliente
        resp.getWriter().write(json.toString());
    }
}
