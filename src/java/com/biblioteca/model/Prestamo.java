package com.biblioteca.model;

/**
 * Modelo que representa un préstamo dentro del sistema de biblioteca.
 * Contiene información sobre el libro prestado, el usuario que lo solicita,
 * las fechas relevantes, estado del préstamo y observaciones.
 */
public class Prestamo {
    private int id_prestamo;        // Identificador único del préstamo
    private int id_libro;           // Identificador del libro prestado
    private int id_usuario;         // Identificador del usuario que solicita el préstamo
    private String fecha_prestamo;  // Fecha en que se realizó el préstamo
    private String fecha_devolucion;// Fecha programada para devolver el libro
    private String fecha_devuelto;  // Fecha real en que se devolvió el libro
    private String estado;          // Estado del préstamo (ej. "Prestado", "Devuelto")
    private String observaciones;   // Observaciones adicionales sobre el préstamo

    // Campo adicional para mostrar el título del libro (usado en consultas con JOIN)
    private String titulolibro;

    // Campo adicional para mostrar el nombre del usuario (usado en consultas con JOIN)
    private String nombreUsuario;

    /** Constructor vacío (necesario para frameworks y librerías como Gson/JPA). */
    public Prestamo() {}

    // Getters y Setters
    public int getId_prestamo() { return id_prestamo; }
    public void setId_prestamo(int id_prestamo) { this.id_prestamo = id_prestamo; }

    public int getId_libro() { return id_libro; }
    public void setId_libro(int id_libro) { this.id_libro = id_libro; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getFecha_prestamo() { return fecha_prestamo; }
    public void setFecha_prestamo(String fecha_prestamo) { this.fecha_prestamo = fecha_prestamo; }

    public String getFecha_devolucion() { return fecha_devolucion; }
    public void setFecha_devolucion(String fecha_devolucion) { this.fecha_devolucion = fecha_devolucion; }

    public String getFecha_devuelto() { return fecha_devuelto; }
    public void setFecha_devuelto(String fecha_devuelto) { this.fecha_devuelto = fecha_devuelto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getTituloLibro() { return titulolibro; }
    public void setTituloLibro(String titulolibro) { this.titulolibro = titulolibro; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    /**
     * Representación en texto del objeto Prestamo.
     * @return cadena con los valores de los atributos principales
     */
    @Override
    public String toString() {
        return "Prestamo{" +
                "id_prestamo=" + id_prestamo +
                ", id_libro=" + id_libro +
                ", id_usuario=" + id_usuario +
                ", fecha_prestamo='" + fecha_prestamo + '\'' +
                ", fecha_devolucion='" + fecha_devolucion + '\'' +
                ", fecha_devuelto='" + fecha_devuelto + '\'' +
                ", estado='" + estado + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", tituloLibro='" + titulolibro + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                '}';
    }
}
