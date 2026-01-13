package com.biblioteca.api;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.model.Libro;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

// IMPORTACIONES JAKARTA (Tomcat 10+)
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet que expone la API REST para gestionar libros.
 * Soporta operaciones CRUD (GET, POST, PUT, DELETE).
 */
@WebServlet(name = "LibroService", urlPatterns = {"/api/libros/*"})
public class LibroService extends HttpServlet {

    private final LibroDAO dao = new LibroDAO(); // DAO para acceder a la base de datos
    private final Gson gson = new Gson();        // Gson para convertir objetos a JSON

    /**
     * GET: listar libros, buscar por ID o mostrar formulario de edición.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        // Si la acción es "editar", cargamos el libro y lo mandamos al JSP
        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id_libro"));
            Libro libro = dao.buscar(id);
            req.setAttribute("libro", libro);
            req.getRequestDispatcher("/libros/editarLibro.jsp").forward(req, resp);
            return;
        }

        // Si no hay acción, seguimos con la API JSON
        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();  // / o /{id}

        if (pathInfo == null || "/".equals(pathInfo)) {
            // Listar todos los libros
            List<Libro> lista = dao.listar();
            resp.getWriter().write(gson.toJson(lista));
        } else {
            // Buscar libro por ID
            try {
                String idStr = pathInfo.replace("/", "");
                int id = Integer.parseInt(idStr);
                Libro libro = dao.buscar(id);

                if (libro != null) {
                    resp.getWriter().write(gson.toJson(libro));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Libro no encontrado\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"ID inválido\"}");
            }
        }
    }

    /**
     * POST: insertar o actualizar libro.
     * Puede venir desde un formulario JSP (x-www-form-urlencoded) o vía JSON (fetch).
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // Caso: formulario JSP
            if (req.getContentType() != null && req.getContentType().contains("application/x-www-form-urlencoded")) {
                int id = req.getParameter("id_libro") != null && !req.getParameter("id_libro").isEmpty()
                        ? Integer.parseInt(req.getParameter("id_libro"))
                        : 0;

                // Construimos objeto Libro con los datos del formulario
                Libro libro = new Libro();
                libro.setId_libro(id);
                libro.setTitulo(req.getParameter("titulo"));
                libro.setAutor(req.getParameter("autor"));
                libro.setCategoria(req.getParameter("categoria"));
                libro.setAnio(Integer.parseInt(req.getParameter("anio")));
                libro.setEditorial(req.getParameter("editorial"));
                libro.setDescripcion(req.getParameter("descripcion"));
                libro.setCantidad(Integer.parseInt(req.getParameter("cantidad")));

                boolean ok;
                if (id > 0) {
                    // Actualizar libro existente
                    ok = dao.actualizar(libro);
                    if (ok) {
                        resp.sendRedirect(req.getContextPath() + "/libros/listalibros.jsp?msg=actualizado");
                        return;
                    }
                } else {
                    // Insertar nuevo libro
                    ok = dao.insertar(libro);
                    if (ok) {
                        resp.sendRedirect(req.getContextPath() + "/libros/listalibros.jsp?msg=agregado");
                        return;
                    }
                }

                // Si falla la operación
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"No se pudo guardar el libro\"}");

            } else {
                // Caso: JSON enviado vía fetch
                resp.setContentType("application/json;charset=UTF-8");
                Libro libro = gson.fromJson(req.getReader(), Libro.class);
                boolean ok;
                if (libro.getId_libro() > 0) {
                    ok = dao.actualizar(libro);
                } else {
                    ok = dao.insertar(libro);
                }
                resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(libro));
            }

        } catch (Exception e) {
            // Manejo de errores genérico
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Datos inválidos\"}");
        }
    }

    /**
     * PUT: actualizar libro vía JSON.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();  // /{id}

        try {
            int id = Integer.parseInt(pathInfo.replace("/", ""));
            Libro libro = gson.fromJson(req.getReader(), Libro.class);
            libro.setId_libro(id);

            boolean ok = dao.actualizar(libro);
            resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(libro));

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }

    /**
     * DELETE: eliminar libro por ID.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();  // /{id}

        try {
            int id = Integer.parseInt(pathInfo.replace("/", ""));
            boolean ok = dao.eliminar(id);

            if (ok) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\":\"Libro eliminado correctamente\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Libro no encontrado\"}");
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }
}
