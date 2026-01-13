package com.biblioteca.api;

import com.biblioteca.dao.PrestamoDAO;
import com.biblioteca.model.Prestamo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet que expone la API REST para gestionar préstamos.
 * Soporta operaciones CRUD: listar, registrar, eliminar y registrar devoluciones.
 */
@WebServlet(name = "PrestamoService", urlPatterns = {"/api/prestamos/*"})
public class PrestamoService extends HttpServlet {

    private final PrestamoDAO dao = new PrestamoDAO(); // DAO para acceder a la base de datos
    private final Gson gson = new Gson();              // Gson para convertir objetos a JSON

    /**
     * GET: listar todos los préstamos o contar préstamos por libro.
     * - action=contar → devuelve un mapa con cantidad de préstamos por libro.
     * - sin action → devuelve lista de préstamos con título del libro y nombre del usuario.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        String action = req.getParameter("action");

        if ("contar".equals(action)) {
            Map<Integer, Integer> mapa = dao.contarPrestadosPorLibro();
            resp.getWriter().write(gson.toJson(mapa));
        } else {
            // Usamos método con JOIN para traer título del libro y nombre del usuario
            List<Prestamo> lista = dao.listarConLibroYUsuario();
            resp.getWriter().write(gson.toJson(lista));
        }
    }

    /**
     * DELETE: eliminar préstamo por ID.
     * Recibe la ruta /{idPrestamo} y elimina el registro correspondiente.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();  // /{idPrestamo}
        String[] datos = path.split("/");
        int idPrestamo = Integer.parseInt(datos[1]);

        boolean ok = dao.eliminarPrestamo(idPrestamo);

        resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * POST: registrar un nuevo préstamo.
     * Puede venir en formato JSON (fetch) o desde formulario JSP (x-www-form-urlencoded).
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Prestamo p;
        String contentType = req.getContentType();

        if (contentType != null && contentType.contains("application/json")) {
            // Caso: JSON enviado vía fetch
            p = gson.fromJson(req.getReader(), Prestamo.class);
        } else {
            // Caso: formulario JSP
            p = new Prestamo();
            p.setId_libro(Integer.parseInt(req.getParameter("id_libro")));
            p.setId_usuario(Integer.parseInt(req.getParameter("id_usuario")));
            p.setFecha_prestamo(req.getParameter("fecha_prestamo"));
            p.setFecha_devolucion(req.getParameter("fecha_devolucion"));
            p.setObservaciones(req.getParameter("observaciones"));
        }

        boolean ok = dao.registrarPrestamo(p);

        if (ok) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\":\"Préstamo registrado correctamente\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"Error al registrar el préstamo\"}");
        }
    }

    /**
     * PUT: registrar devolución de un préstamo.
     * Recibe la ruta /{idPrestamo} y la fecha de devolución en el cuerpo JSON.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();  // /{idPrestamo}
        String[] datos = path.split("/");

        int idPrestamo = Integer.parseInt(datos[1]);
        Prestamo p = gson.fromJson(req.getReader(), Prestamo.class);

        boolean ok = dao.registrarDevolucion(idPrestamo, p.getFecha_devuelto());

        resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
    }
}
