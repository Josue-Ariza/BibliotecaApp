<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Agregar Libro</title>
  <!-- Importamos Bootstrap para estilos responsivos -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
  <h1 class="mb-4 text-center">📚 Agregar Nuevo Libro</h1>

  <!-- Formulario para registrar un nuevo libro en la base de datos -->
  <form action="../api/libros" method="post" class="card p-4">
    
    <!-- Campo para ingresar el título del libro -->
    <div class="mb-3">
      <label class="form-label">Título</label>
      <input type="text" name="titulo" class="form-control" required>
    </div>

    <!-- Campo para ingresar el autor -->
    <div class="mb-3">
      <label class="form-label">Autor</label>
      <input type="text" name="autor" class="form-control" required>
    </div>

    <!-- Campo para ingresar la categoría -->
    <div class="mb-3">
      <label class="form-label">Categoría</label>
      <input type="text" name="categoria" class="form-control" required>
    </div>

    <!-- Campo para ingresar el año de publicación -->
    <div class="mb-3">
      <label class="form-label">Año</label>
      <input type="number" name="anio" class="form-control" required>
    </div>

    <!-- Campo para ingresar la editorial -->
    <div class="mb-3">
      <label class="form-label">Editorial</label>
      <input type="text" name="editorial" class="form-control" required>
    </div>

    <!-- Campo para ingresar una descripción opcional -->
    <div class="mb-3">
      <label class="form-label">Descripción</label>
      <textarea name="descripcion" class="form-control" rows="3"></textarea>
    </div>

    <!-- Campo para ingresar la cantidad disponible -->
    <div class="mb-3">
      <label class="form-label">Cantidad</label>
      <input type="number" name="cantidad" class="form-control" required>
    </div>

    <!-- Botón para enviar el formulario y agregar el libro -->
    <button type="submit" class="btn btn-success w-100">➕ Agregar Libro</button>
  </form>

  <!-- Botón para regresar al panel de inventario -->
  <div class="mt-3 text-center">
    <a href="dashboard.jsp" class="btn btn-secondary">⬅️ Volver al Inventario</a>
  </div>
</div>

</body>
</html>
