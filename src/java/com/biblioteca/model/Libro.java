package com.biblioteca.model;

/**
 * Modelo que representa un Libro dentro del sistema de biblioteca.
 * Contiene atributos básicos como título, autor, categoría, año, editorial,
 * descripción, cantidad total y cantidad disponible.
 */
public class Libro {
    private int id_libro;       // Identificador único del libro
    private String titulo;      // Título del libro
    private String autor;       // Autor del libro
    private String categoria;   // Categoría o género
    private int anio;           // Año de publicación
    private String editorial;   // Editorial del libro
    private String descripcion; // Breve descripción
    private int cantidad;       // Cantidad total de ejemplares
    private int disponibles;    // Cantidad de ejemplares disponibles (usado en listarDisponibles)

    /** Constructor vacío (necesario para frameworks y librerías como Gson/JPA). */
    public Libro() {}

    /**
     * Constructor sin campo 'disponibles'.
     * @param id_libro identificador del libro
     * @param titulo título del libro
     * @param autor autor del libro
     * @param categoria categoría o género
     * @param anio año de publicación
     * @param editorial editorial del libro
     * @param descripcion breve descripción
     * @param cantidad cantidad total de ejemplares
     */
    public Libro(int id_libro, String titulo, String autor, String categoria,
                 int anio, String editorial, String descripcion, int cantidad) {
        this.id_libro = id_libro;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anio = anio;
        this.editorial = editorial;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    /**
     * Constructor con campo 'disponibles'.
     * @param id_libro identificador del libro
     * @param titulo título del libro
     * @param autor autor del libro
     * @param categoria categoría o género
     * @param anio año de publicación
     * @param editorial editorial del libro
     * @param descripcion breve descripción
     * @param cantidad cantidad total de ejemplares
     * @param disponibles cantidad de ejemplares disponibles
     */
    public Libro(int id_libro, String titulo, String autor, String categoria,
                 int anio, String editorial, String descripcion,
                 int cantidad, int disponibles) {
        this.id_libro = id_libro;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anio = anio;
        this.editorial = editorial;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.disponibles = disponibles;
    }

    // Getters y Setters
    public int getId_libro() { return id_libro; }
    public void setId_libro(int id) { this.id_libro = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String t) { this.titulo = t; }

    public String getAutor() { return autor; }
    public void setAutor(String a) { this.autor = a; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String c) { this.categoria = c; }

    public int getAnio() { return anio; }
    public void setAnio(int y) { this.anio = y; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String e) { this.editorial = e; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int q) { this.cantidad = q; }

    public int getDisponibles() { return disponibles; }
    public void setDisponibles(int d) { this.disponibles = d; }

    /**
     * Representación en texto del objeto Libro.
     * @return cadena con los valores de los atributos
     */
    @Override
    public String toString() {
        return "Libro{" +
                "id_libro=" + id_libro +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", categoria='" + categoria + '\'' +
                ", anio=" + anio +
                ", editorial='" + editorial + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", disponibles=" + disponibles +
                '}';
    }
}
