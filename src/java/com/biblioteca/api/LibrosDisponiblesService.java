package com.biblioteca.api;

import com.biblioteca.config.Conexion;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

/**
 * Servlet que expone un endpoint para obtener la lista de libros disponibles.
 * Devuelve un JSON con id, título y cantidad disponible de cada libro.
 */
@WebServlet("/api/librosDisponibles")
public class LibrosDisponiblesService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Configuramos la respuesta como JSON con codificación UTF-8
        resp.setContentType("application/json;charset=UTF-8");
        JsonArray disponibles = new JsonArray();

        // Consulta SQL:
        // - Selecciona cada libro con su ID y título
        // - Calcula la cantidad disponible restando los préstamos activos (sin fecha de devolución)
        // - Solo devuelve libros con al menos 1 ejemplar disponible
        String sql = """
            SELECT l.id_libro, l.titulo,
                   (l.cantidad - COUNT(p.id_prestamo)) AS disponibles
            FROM libros l
            LEFT JOIN prestamos p 
                   ON l.id_libro = p.id_libro 
                   AND p.fecha_devuelto IS NULL
            GROUP BY l.id_libro, l.titulo, l.cantidad
            HAVING (l.cantidad - COUNT(p.id_prestamo)) > 0
        """;

        // Ejecutamos la consulta y construimos el JSON de respuesta
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                JsonObject libro = new JsonObject();
                libro.addProperty("id_libro", rs.getInt("id_libro"));
                libro.addProperty("titulo", rs.getString("titulo"));
                libro.addProperty("disponibles", rs.getInt("disponibles"));
                disponibles.add(libro);
            }

        } catch (Exception e) {
            // En caso de error, lo imprimimos en consola (se recomienda usar un logger en producción)
            e.printStackTrace();
        }

        // Enviamos la respuesta JSON al cliente
        resp.getWriter().write(disponibles.toString());
    }
}
