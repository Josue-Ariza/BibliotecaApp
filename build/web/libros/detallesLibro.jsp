<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Detalles del Libro</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
  <h1 class="mb-4">📖 Detalles del Libro</h1>

  <div id="infoLibro" class="card">
    <div class="card-body">
      <h3 id="titulo"></h3>
      <p><strong>Autor:</strong> <span id="autor"></span></p>
      <p><strong>Categoría:</strong> <span id="categoria"></span></p>
      <p><strong>Año:</strong> <span id="anio"></span></p>
      <p><strong>Editorial:</strong> <span id="editorial"></span></p>
      <p><strong>Descripción:</strong> <span id="descripcion"></span></p>
      <p><strong>Cantidad total:</strong> <span id="cantidad"></span></p>
      <p><strong>Disponibles:</strong> <span id="disponibles"></span></p>
      <p><strong>Prestados:</strong> <span id="prestados"></span></p>
    </div>
  </div>

  <div class="mt-3">
    <a href="editarLibro.jsp?id=${param.id}" class="btn btn-warning">✏ Editar</a>
    <a href="listalibros.jsp" class="btn btn-secondary">⬅ Regresar</a>
  </div>
</div>

<script>
const idLibro = new URLSearchParams(window.location.search).get("id");

// API de libros (datos generales)
const API_LIBRO = "../api/libros/" + idLibro;
// API de préstamos (para contar ejemplares prestados)
const API_PRESTAMOS = "../api/prestamos?libro=" + idLibro;

Promise.all([
  fetch(API_LIBRO).then(resp => resp.json()),
  fetch(API_PRESTAMOS).then(resp => resp.json())
])
.then(([libro, prestamos]) => {
  document.getElementById("titulo").textContent = libro.titulo;
  document.getElementById("autor").textContent = libro.autor;
  document.getElementById("categoria").textContent = libro.categoria;
  document.getElementById("anio").textContent = libro.anio;
  document.getElementById("editorial").textContent = libro.editorial;
  document.getElementById("descripcion").textContent = libro.descripcion;
  document.getElementById("cantidad").textContent = libro.cantidad;

  // Calcular prestados y disponibles
  const prestados = prestamos.filter(p => p.estado === "Prestado").length;
  const disponibles = libro.cantidad - prestados;

  document.getElementById("disponibles").textContent = disponibles;
  document.getElementById("prestados").textContent = prestados;
})
.catch(err => console.error("Error cargando detalles:", err));
</script>

</body>
</html>