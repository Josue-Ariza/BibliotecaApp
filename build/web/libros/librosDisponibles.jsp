<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Libros disponibles para préstamo</title>
  <!-- Importamos Bootstrap para estilos y diseño responsivo -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
  <!-- Título principal de la página -->
  <h1 class="mb-4 text-center">📚 Libros disponibles para préstamo</h1>

  <!-- Tabla que mostrará la lista de libros disponibles -->
  <table class="table table-bordered table-striped">
    <thead class="table-dark">
      <tr>
        <th>ID</th>
        <th>Título</th>
        <th>Disponibles</th>
        <th>Acción</th>
      </tr>
    </thead>
    <!-- El cuerpo de la tabla se llenará dinámicamente con JavaScript -->
    <tbody id="tbody"></tbody>
  </table>
</div>

  <!-- Botón para regresar al menú principal -->
  <div class="mt-3 text-center">
    <a href="../libros/dashboard.jsp" class="btn btn-secondary">
      ⬅️ Volver al Menú
    </a>
  </div>
</div>

<script>
// URL de la API que devuelve los libros disponibles
const API = "../api/librosDisponibles";

// Función que carga los libros desde la API al iniciar la página
function cargarLibrosDisponibles() {
  fetch(API)
    .then(resp => resp.json()) // Convertimos la respuesta en JSON
    .then(lista => mostrarLibros(lista)) // Mostramos los libros en la tabla
    .catch(err => console.error("Error cargando libros disponibles:", err));
}

// Función que inserta los libros en la tabla HTML
function mostrarLibros(lista) {
  const tbody = document.getElementById("tbody");
  tbody.innerHTML = ""; // Limpiamos contenido previo

  // Si no hay libros disponibles, mostramos un mensaje en la tabla
  if (lista.length === 0) {
    tbody.innerHTML = `<tr><td colspan="4" class="text-center text-warning">No hay libros disponibles</td></tr>`;
    return;
  }

  // Recorremos la lista de libros y generamos filas dinámicamente
  lista.forEach(l => {
    let fila = "<tr>" +
      "<td>" + l.id_libro + "</td>" +
      "<td>" + l.titulo + "</td>" +
      "<td>" + l.disponibles + "</td>" +
      "<td>" +
        // Botón para registrar un préstamo del libro seleccionado
        "<a href='../prestamos/registrarPrestamo.jsp?id_libro=" + l.id_libro + "' class='btn btn-primary btn-sm'>📖 Prestar</a>" +
      "</td></tr>";
    tbody.insertAdjacentHTML("beforeend", fila);
  });
}

// Ejecutamos la carga de libros automáticamente al abrir la página
window.onload = cargarLibrosDisponibles;
</script>

</body>
</html>
