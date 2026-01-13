package com.biblioteca.dao;

import com.biblioteca.config.Conexion;
import com.biblioteca.model.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * DAO (Data Access Object) para la entidad Prestamo.
 * Contiene métodos CRUD y consultas específicas sobre la tabla "prestamos".
 */
public class PrestamoDAO {

    /**
     * Registrar un nuevo préstamo en la base de datos.
     * @param p objeto Prestamo con los datos a registrar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean registrarPrestamo(Prestamo p) {
        String sql = "INSERT INTO prestamos (id_libro, id_usuario, fecha_prestamo, fecha_devolucion, observaciones) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId_libro());
            ps.setInt(2, p.getId_usuario());

            // Fecha de préstamo (puede ser null)
            if (p.getFecha_prestamo() != null && !p.getFecha_prestamo().isEmpty()) {
                ps.setDate(3, java.sql.Date.valueOf(p.getFecha_prestamo()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            // Fecha de devolución (puede ser null al inicio)
            if (p.getFecha_devolucion() != null && !p.getFecha_devolucion().isEmpty()) {
                ps.setDate(4, java.sql.Date.valueOf(p.getFecha_devolucion()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, p.getObservaciones());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Registrar devolución de un préstamo.
     * @param idPrestamo identificador del préstamo
     * @param fechaDevuelto fecha en que se devolvió el libro
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean registrarDevolucion(int idPrestamo, String fechaDevuelto) {
        String sql = "UPDATE prestamos SET fecha_devolucion=? WHERE id_prestamo=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (fechaDevuelto != null && !fechaDevuelto.isEmpty()) {
                ps.setDate(1, java.sql.Date.valueOf(fechaDevuelto));
            } else {
                ps.setNull(1, java.sql.Types.DATE);
            }

            ps.setInt(2, idPrestamo);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Listar todos los préstamos (sin JOIN).
     * @return lista de préstamos con datos básicos
     */
    public List<Prestamo> listar() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setId_prestamo(rs.getInt("id_prestamo"));
                p.setId_libro(rs.getInt("id_libro"));
                p.setId_usuario(rs.getInt("id_usuario"));
                p.setFecha_prestamo(rs.getDate("fecha_prestamo") != null ? rs.getDate("fecha_prestamo").toString() : null);
                p.setFecha_devolucion(rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toString() : null);
                p.setFecha_devuelto(rs.getDate("fecha_devuelto") != null ? rs.getDate("fecha_devuelto").toString() : null);
                p.setObservaciones(rs.getString("observaciones"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Listar préstamos con título de libro y nombre de usuario (JOIN).
     * @return lista de préstamos con información enriquecida
     */
    public List<Prestamo> listarConLibroYUsuario() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id_prestamo, p.id_libro, p.id_usuario, p.fecha_prestamo, " +
                     "p.fecha_devolucion, p.fecha_devuelto, p.observaciones, l.titulo, u.nombre " +
                     "FROM prestamos p " +
                     "INNER JOIN libros l ON p.id_libro = l.id_libro " +
                     "INNER JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "ORDER BY p.fecha_prestamo DESC, p.id_prestamo DESC";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setId_prestamo(rs.getInt("id_prestamo"));
                p.setId_libro(rs.getInt("id_libro"));
                p.setId_usuario(rs.getInt("id_usuario"));
                p.setFecha_prestamo(rs.getDate("fecha_prestamo") != null ? rs.getDate("fecha_prestamo").toString() : null);
                p.setFecha_devolucion(rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toString() : null);
                p.setFecha_devuelto(rs.getDate("fecha_devuelto") != null ? rs.getDate("fecha_devuelto").toString() : null);
                p.setObservaciones(rs.getString("observaciones"));
                // título del libro
                p.setTituloLibro(rs.getString("titulo"));
                // nombre del usuario
                p.setNombreUsuario(rs.getString("nombre"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Contar préstamos activos por libro.
     * @return mapa con id_libro como clave y cantidad de préstamos activos como valor
     */
    public Map<Integer, Integer> contarPrestadosPorLibro() {
        Map<Integer, Integer> mapa = new HashMap<>();
        String sql = "SELECT id_libro, COUNT(*) AS prestados " +
                     "FROM prestamos WHERE fecha_devuelto IS NULL " +
                     "GROUP BY id_libro";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                mapa.put(rs.getInt("id_libro"), rs.getInt("prestados"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapa;
    }

    /**
     * Eliminar préstamo por ID.
     * @param idPrestamo identificador del préstamo
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarPrestamo(int idPrestamo) {
        String sql = "DELETE FROM prestamos WHERE id_prestamo=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
