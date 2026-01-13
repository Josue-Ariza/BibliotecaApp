<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Lista de Préstamos</title>
  <!-- Importamos Bootstrap para estilos y diseño responsivo -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
  <!-- Título principal de la página -->
  <h1 class="mb-4 text-center">📖 Lista de Préstamos</h1>

  <!-- Tabla que mostrará la lista de préstamos -->
  <table class="table table-bordered table-striped">
    <thead class="table-dark">
      <tr>
        <th>ID Préstamo</th>
        <th>Título del Libro</th>
        <th>Usuario</th>
        <th>Fecha de Préstamo</th>
        <th>Observaciones</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <!-- El cuerpo de la tabla se llenará dinámicamente con JavaScript -->
    <tbody id="tbody"></tbody>
  </table>

  <!-- Botón para regresar al menú principal -->
  <div class="mt-3 text-center">
    <a href="../libros/dashboard.jsp" class="btn btn-secondary">⬅️ Volver al Menú</a>
  </div>
</div>

<script>
// URL de la API que devuelve los préstamos
const API = "../api/prestamos";
// Variable para guardar la lista original de préstamos
let prestamosOriginal = [];

// Función que carga los préstamos desde la API al iniciar la página
function cargarPrestamos() {
  fetch(API)
    .then(resp => resp.json()) // Convertimos la respuesta en JSON
    .then(lista => {
      prestamosOriginal = lista;   // Guardamos la lista original
      mostrarPrestamos(lista);     // Mostramos los préstamos en la tabla
    })
    .catch(err => console.error("Error cargando préstamos:", err));
}

// Función que inserta los préstamos en la tabla HTML
function mostrarPrestamos(lista) {
  const tbody = document.getElementById("tbody");
  tbody.innerHTML = ""; // Limpiamos contenido previo

  // Recorremos la lista de préstamos y generamos filas dinámicamente
  lista.forEach(p => {
    let fila = "<tr>" +
      "<td>" + p.id_prestamo + "</td>" +
      "<td>" + (p.titulolibro || "—") + "</td>" +
      "<td>" + (p.nombreUsuario || "—") + "</td>" +   
      "<td>" + (p.fecha_prestamo || "—") + "</td>" +
      "<td>" + (p.observaciones || "—") + "</td>" +
      // Botón para devolver/eliminar el préstamo
      "<td><button class='btn btn-success btn-sm' onclick='devolverPrestamo(" + p.id_prestamo + ")'>Devolver</button></td>" +
      "</tr>";
    tbody.insertAdjacentHTML("beforeend", fila);
  });
}

// Función para eliminar un préstamo por ID
function devolverPrestamo(id) {
  if (!confirm("¿Eliminar este préstamo definitivamente?")) return;

  fetch(API + "/" + id, { method: "DELETE" })
    .then(resp => {
      if (resp.ok) {
        alert("Préstamo eliminado correctamente");
        cargarPrestamos(); // Recargamos la lista después de eliminar
      } else {
        alert("Error al eliminar el préstamo");
      }
    })
    .catch(err => console.error("Error eliminando préstamo:", err));
}

// Ejecutamos la carga de préstamos automáticamente al abrir la página
window.onload = cargarPrestamos;
</script>

</body>
</html>
