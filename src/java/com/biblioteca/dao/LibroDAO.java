package com.biblioteca.dao;

import com.biblioteca.config.Conexion;
import com.biblioteca.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Libro.
 * Contiene métodos CRUD y consultas específicas sobre la tabla "libros".
 */
public class LibroDAO {

    /**
     * LISTAR TODOS LOS LIBROS
     * @return lista completa de libros registrados en la base de datos
     */
    public List<Libro> listar() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro l = new Libro();
                l.setId_libro(rs.getInt("id_libro"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setCategoria(rs.getString("categoria"));
                l.setAnio(rs.getInt("anio"));
                l.setEditorial(rs.getString("editorial"));
                l.setDescripcion(rs.getString("descripcion"));
                l.setCantidad(rs.getInt("cantidad"));
                lista.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * LISTAR SOLO LIBROS DISPONIBLES
     * Devuelve libros con al menos un ejemplar disponible (no prestado).
     * @return lista de libros disponibles
     */
    public List<Libro> listarDisponibles() {
        List<Libro> lista = new ArrayList<>();
        String sql = """
            SELECT l.id_libro, l.titulo, l.autor, l.categoria, l.anio, l.editorial, l.descripcion, l.cantidad,
                   (l.cantidad - COUNT(p.id_prestamo)) AS disponibles
            FROM libros l
            LEFT JOIN prestamos p 
                   ON l.id_libro = p.id_libro 
                   AND p.fecha_devuelto IS NULL   -- solo prestamos activos
            GROUP BY l.id_libro, l.titulo, l.autor, l.categoria, l.anio, l.editorial, l.descripcion, l.cantidad
            HAVING (l.cantidad - COUNT(p.id_prestamo)) > 0
        """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro l = new Libro();
                l.setId_libro(rs.getInt("id_libro"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setCategoria(rs.getString("categoria"));
                l.setAnio(rs.getInt("anio"));
                l.setEditorial(rs.getString("editorial"));
                l.setDescripcion(rs.getString("descripcion"));
                l.setCantidad(rs.getInt("cantidad"));
                l.setDisponibles(rs.getInt("disponibles")); // campo extra en modelo
                lista.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * INSERTAR LIBRO
     * @param l objeto Libro con los datos a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Libro l) {
        String sql = "INSERT INTO libros(titulo, autor, categoria, anio, editorial, descripcion, cantidad) "
                   + "VALUES (?,?,?,?,?,?,?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setString(3, l.getCategoria());
            ps.setInt(4, l.getAnio());
            ps.setString(5, l.getEditorial());
            ps.setString(6, l.getDescripcion());
            ps.setInt(7, l.getCantidad());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ACTUALIZAR LIBRO
     * @param l objeto Libro con datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Libro l) {
        String sql = "UPDATE libros SET titulo=?, autor=?, categoria=?, anio=?, editorial=?, descripcion=?, cantidad=? "
                   + "WHERE id_libro=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setString(3, l.getCategoria());
            ps.setInt(4, l.getAnio());
            ps.setString(5, l.getEditorial());
            ps.setString(6, l.getDescripcion());
            ps.setInt(7, l.getCantidad());
            ps.setInt(8, l.getId_libro());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ELIMINAR LIBRO
     * @param id identificador del libro
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id_libro=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * BUSCAR LIBRO POR ID
     * @param id identificador del libro
     * @return objeto Libro si se encuentra, null en caso contrario
     */
    public Libro buscar(int id) {
        String sql = "SELECT * FROM libros WHERE id_libro=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Libro l = new Libro();
                    l.setId_libro(rs.getInt("id_libro"));
                    l.setTitulo(rs.getString("titulo"));
                    l.setAutor(rs.getString("autor"));
                    l.setCategoria(rs.getString("categoria"));
                    l.setAnio(rs.getInt("anio"));
                    l.setEditorial(rs.getString("editorial"));
                    l.setDescripcion(rs.getString("descripcion"));
                    l.setCantidad(rs.getInt("cantidad"));
                    return l;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
