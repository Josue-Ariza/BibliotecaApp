package com.biblioteca.api;

import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Usuario;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet que expone la API REST para gestionar usuarios.
 * Soporta operaciones CRUD: listar, insertar, actualizar y eliminar.
 */
@WebServlet(name = "UsuarioService", urlPatterns = {"/api/usuarios/*"})
public class UsuarioService extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO(); // DAO para acceder a la base de datos
    private final Gson gson = new Gson();            // Gson para convertir objetos a JSON

    /**
     * GET: listar todos los usuarios o buscar por ID.
     * - /api/usuarios → devuelve lista completa
     * - /api/usuarios/{id} → devuelve un usuario específico
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo(); // / o /{id}

        if (pathInfo == null || "/".equals(pathInfo)) {
            // Listar todos los usuarios
            List<Usuario> lista = dao.listar();
            resp.getWriter().write(gson.toJson(lista));
        } else {
            // Buscar usuario por ID
            try {
                int id = Integer.parseInt(pathInfo.replace("/", ""));
                Usuario u = dao.buscar(id);
                if (u != null) {
                    resp.getWriter().write(gson.toJson(u));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Usuario no encontrado\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"ID inválido\"}");
            }
        }
    }

    /**
     * POST: insertar usuario.
     * Puede venir desde formulario JSP (x-www-form-urlencoded) o vía JSON (fetch).
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if (req.getContentType() != null && req.getContentType().contains("application/x-www-form-urlencoded")) {
                // Caso: formulario JSP
                Usuario u = new Usuario();
                u.setNombre(req.getParameter("nombre"));
                u.setCorreo(req.getParameter("correo"));
                u.setTelefono(req.getParameter("telefono"));

                boolean ok = dao.insertar(u);
                if (ok) {
                    resp.sendRedirect(req.getContextPath() + "/libros/dashboard.jsp?msg=agregado");
                    return;
                } else {
                    resp.sendRedirect(req.getContextPath() + "/libros/dashboard.jsp?msg=error");
                    return;
                }
            } else {
                // Caso: JSON enviado vía fetch
                Usuario u = gson.fromJson(req.getReader(), Usuario.class);
                boolean ok = dao.insertar(u);
                resp.setStatus(ok ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(u));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Datos inválidos\"}");
        }
    }

    /**
     * PUT: actualizar usuario vía JSON.
     * Recibe la ruta /{id} y los datos en el cuerpo JSON.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            int id = Integer.parseInt(pathInfo.replace("/", ""));
            Usuario u = gson.fromJson(req.getReader(), Usuario.class);
            u.setId_usuario(id);

            boolean ok = dao.actualizar(u);
            resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(u));

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }

    /**
     * DELETE: eliminar usuario por ID.
     * Recibe la ruta /{id} y elimina el registro correspondiente.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            int id = Integer.parseInt(pathInfo.replace("/", ""));
            boolean ok = dao.eliminar(id);

            if (ok) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\":\"Usuario eliminado correctamente\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Usuario no encontrado\"}");
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }
}
