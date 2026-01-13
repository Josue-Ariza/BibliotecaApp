package com.biblioteca.model;

/**
 * Modelo que representa un Usuario dentro del sistema de biblioteca.
 * Contiene información básica como nombre, correo y teléfono.
 */
public class Usuario {
    private int id_usuario;   // Identificador único del usuario
    private String nombre;    // Nombre completo del usuario
    private String correo;    // Correo electrónico del usuario
    private String telefono;  // Teléfono de contacto del usuario

    /** Constructor vacío (necesario para frameworks y librerías como Gson/JPA). */
    public Usuario() {}

    // Getters y Setters

    /**
     * @return identificador único del usuario
     */
    public int getId_usuario() {
        return id_usuario;
    }

    /**
     * @param id_usuario identificador único del usuario
     */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /**
     * @return nombre completo del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre nombre completo del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return correo electrónico del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo correo electrónico del usuario
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return teléfono de contacto del usuario
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono teléfono de contacto del usuario
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Representación en texto del objeto Usuario.
     * @return cadena con los valores de los atributos
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
