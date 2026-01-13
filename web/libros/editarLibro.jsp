<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.biblioteca.model.Libro"%>

<%
    // Obtenemos el objeto 'libro' enviado desde el controlador
    // Este objeto contiene los datos actuales del libro que se desea editar
    Libro libro = (Libro) request.getAttribute("libro");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Editar Libro</title>
  <!-- Importamos Bootstrap para estilos y diseño responsivo -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
  <h1 class="mb-4 text-center">✏ Editar Libro</h1>

  <!-- Formulario para actualizar los datos de un libro existente -->
  <!-- Se envía al endpoint ../api/libros mediante método POST -->
  <form action="../api/libros" method="post" class="card p-4">

    <!-- Campo oculto que guarda el ID del libro para identificarlo en la actualización -->
    <input type="hidden" name="id_libro" value="<%= libro.getId_libro() %>">

    <!-- Campo para editar el título del libro -->
    <div class="mb-3">
      <label class="form-label">Título</label>
      <input type="text" name="titulo" class="form-control" value="<%= libro.getTitulo() %>" required>
    </div>

    <!-- Campo para editar el autor -->
    <div class="mb-3">
      <label class="form-label">Autor</label>
      <input type="text" name="autor" class="form-control" value="<%= libro.getAutor() %>" required>
    </div>

    <!-- Campo para editar la categoría -->
    <div class="mb-3">
      <label class="form-label">Categoría</label>
      <input type="text" name="categoria" class="form-control" value="<%= libro.getCategoria() %>" required>
    </div>

    <!-- Campo para editar el año de publicación -->
    <div class="mb-3">
      <label class="form-label">Año</label>
      <input type="number" name="anio" class="form-control" value="<%= libro.getAnio() %>" required>
    </div>

    <!-- Campo para editar la editorial -->
    <div class="mb-3">
      <label class="form-label">Editorial</label>
      <input type="text" name="editorial" class="form-control" value="<%= libro.getEditorial() %>" required>
    </div>

    <!-- Campo para editar la descripción -->
    <div class="mb-3">
      <label class="form-label">Descripción</label>
      <textarea name="descripcion" class="form-control" rows="3"><%= libro.getDescripcion() %></textarea>
    </div>

    <!-- Campo para editar la cantidad disponible -->
    <div class="mb-3">
      <label class="form-label">Cantidad</label>
      <input type="number" name="cantidad" class="form-control" value="<%= libro.getCantidad() %>" required>
    </div>

    <!-- Botón para enviar el formulario y actualizar el libro -->
    <button type="submit" class="btn btn-primary w-100">🔄 Actualizar</button>
  </form>
</div>

</body>
</html>
