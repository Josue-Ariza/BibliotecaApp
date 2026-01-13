package com.biblioteca.dao;

import com.biblioteca.config.Conexion;
import com.biblioteca.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Usuario.
 * Contiene métodos CRUD para interactuar con la tabla "usuarios".
 */
public class UsuarioDAO {

    /**
     * Listar todos los usuarios registrados en la base de datos.
     * @return lista de objetos Usuario
     */
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));
                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Insertar un nuevo usuario en la base de datos.
     * @param u objeto Usuario con los datos a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO usuarios(nombre, correo, telefono) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getTelefono());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Actualizar un usuario existente en la base de datos.
     * @param u objeto Usuario con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, telefono=? WHERE id_usuario=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getTelefono());
            ps.setInt(4, u.getId_usuario());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Eliminar un usuario por su ID.
     * @param id identificador del usuario
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";

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
     * Buscar un usuario por su ID.
     * @param id identificador del usuario
     * @return objeto Usuario si se encuentra, null en caso contrario
     */
    public Usuario buscar(int id) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId_usuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setCorreo(rs.getString("correo"));
                    u.setTelefono(rs.getString("telefono"));
                    return u;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
