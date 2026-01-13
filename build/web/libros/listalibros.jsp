<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Inventario de Libros</title>
  <!-- Importamos Bootstrap para estilos y diseño responsivo -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
  <!-- Título principal de la página -->
  <h1 class="mb-4 text-center">Inventario de Libros</h1>
  
  <!-- Barra de búsqueda y filtro por categoría -->
  <div class="row mb-3">
    <div class="col-md-4">
      <!-- Campo de texto para buscar por título o autor -->
      <input type="text" id="busqueda" class="form-control" placeholder="Buscar por título o autor">
    </div>
    <div class="col-md-4">
      <!-- Selector para filtrar por categoría -->
      <select id="filtroCategoria" class="form-select">
        <option value="">Todas las categorías</option>
        <option value="Infantil">Infantil</option>
        <option value="Ficción">Ficción</option>
        <option value="Ciencia">Ciencia</option>
        <!-- Se pueden agregar más categorías según la base de datos -->
      </select>
    </div>
    <div class="col-md-4 text-end">
      <!-- Botón para regresar al menú principal -->
      <a href="dashboard.jsp" class="btn btn-secondary w-100">⬅️ Volver al Menú</a>
    </div>
  </div>

  <!-- Tabla que mostrará los libros del inventario -->
  <table class="table table-bordered table-striped">
    <thead class="table-dark">
      <tr>
        <th>ID</th>
        <th>Título</th>
        <th>Autor</th>
        <th>Categoría</th>
        <th>Año</th>
        <th>Editorial</th>
        <th>Descripción</th>
        <th>Cantidad</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <!-- El cuerpo de la tabla se llenará dinámicamente con JavaScript -->
    <tbody id="tbody"></tbody>
  </table>
</div>

<script>
// URL de la API que devuelve los libros
const API = "../api/libros";
// Variable para guardar la lista original de libros (antes de aplicar filtros)
let librosOriginal = [];

// Función que carga los libros desde la API al iniciar la página
function cargarLibros() {
  fetch(API)
    .then(resp => resp.json()) // Convertimos la respuesta en JSON
    .then(lista => {
      librosOriginal = lista;   // Guardamos la lista original
      mostrarLibros(lista);     // Mostramos los libros en la tabla
    })
    .catch(err => console.error("Error cargando libros:", err));
}

// Función que inserta los libros en la tabla HTML
function mostrarLibros(lista) {
  const tbody = document.getElementById("tbody");
  tbody.innerHTML = ""; // Limpiamos contenido previo

  // Recorremos la lista de libros y generamos filas dinámicamente
  lista.forEach(l => {
    let fila = "<tr>" +
      "<td>" + l.id_libro + "</td>" +
      "<td>" + l.titulo + "</td>" +
      "<td>" + l.autor + "</td>" +
      "<td>" + l.categoria + "</td>" +
      "<td>" + l.anio + "</td>" +
      "<td>" + l.editorial + "</td>" +
      "<td>" + l.descripcion + "</td>" +
      "<td>" + l.cantidad + "</td>" +
      "<td class='d-flex gap-2'>" +
        // Botón Editar: pasa por el Servlet con action=editar
        "<form action='../api/libros' method='get'>" +
          "<input type='hidden' name='action' value='editar'>" +
          "<input type='hidden' name='id_libro' value='" + l.id_libro + "'>" +
          "<button type='submit' class='btn btn-warning btn-sm'>✏ Editar</button>" +
        "</form>" +
        // Botón Eliminar: llama a la función eliminar()
        "<button onclick='eliminar(" + l.id_libro + ")' class='btn btn-danger btn-sm'>🗑 Eliminar</button>" +
      "</td></tr>";
    tbody.insertAdjacentHTML("beforeend", fila);
  });
}

// Función para eliminar un libro por ID
function eliminar(id) {
  if (!confirm("¿Estás seguro de eliminar este libro?")) return;
  fetch(API + "/" + id, { method: "DELETE" })
    .then(() => cargarLibros()); // Recargamos la lista después de eliminar
}

// Eventos para aplicar filtros y búsqueda en tiempo real
document.getElementById("busqueda").addEventListener("input", filtrar);
document.getElementById("filtroCategoria").addEventListener("change", filtrar);

// Función que filtra la lista según texto y categoría
function filtrar() {
  const texto = document.getElementById("busqueda").value.toLowerCase();
  const categoria = document.getElementById("filtroCategoria").value;

  const filtrados = librosOriginal.filter(l => {
    const coincideTexto = l.titulo.toLowerCase().includes(texto) || l.autor.toLowerCase().includes(texto);
    const coincideCategoria = categoria === "" || l.categoria === categoria;
    return coincideTexto && coincideCategoria;
  });

  mostrarLibros(filtrados); // Mostramos la lista filtrada
}

// Ejecutamos la carga de libros automáticamente al abrir la página
window.onload = cargarLibros;
</script>

</body>
</html>
