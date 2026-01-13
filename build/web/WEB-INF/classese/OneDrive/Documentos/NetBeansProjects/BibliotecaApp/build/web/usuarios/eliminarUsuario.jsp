<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Eliminar Usuario</title>
  <!-- Importamos Bootstrap para estilos y diseño responsivo -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
  <!-- Título principal de la página -->
  <h1 class="mb-4 text-center">🗑 Eliminar Usuarios</h1>

  <!-- Tabla que mostrará la lista de usuarios -->
  <table class="table table-bordered table-striped">
    <thead class="table-dark">
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Correo</th>
        <th>Teléfono</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <!-- El cuerpo de la tabla se llenará dinámicamente con JavaScript -->
    <tbody id="tbody"></tbody>
  </table>

  <!-- Botón para regresar al dashboard -->
  <div class="mt-3 text-center">
    <a href="../libros/dashboard.jsp" class="btn btn-secondary">⬅️ Volver al Dashboard</a>
  </div>
</div>

<script>
// URL de la API que devuelve los usuarios
const API = "../api/usuarios";
// Variable para guardar la lista original de usuarios
let usuariosOriginal = [];

// Función que carga los usuarios desde la API al iniciar la página
function cargarUsuarios() {
  fetch(API)
    .then(resp => resp.json()) // Convertimos la respuesta en JSON
    .then(lista => {
      usuariosOriginal = lista;   // Guardamos la lista original
      mostrarUsuarios(lista);     // Mostramos los usuarios en la tabla
    })
    .catch(err => console.error("Error cargando usuarios:", err));
}

// Función que inserta los usuarios en la tabla HTML
function mostrarUsuarios(lista) {
  const tbody = document.getElementById("tbody");
  tbody.innerHTML = ""; // Limpiamos contenido previo

  // Recorremos la lista de usuarios y generamos filas dinámicamente
  lista.forEach(u => {
    let fila = "<tr>" +
      "<td>" + u.id_usuario + "</td>" +
      "<td>" + u.nombre + "</td>" +
      "<td>" + u.correo + "</td>" +
      "<td>" + (u.telefono || "") + "</td>" +
      "<td>" +
        // Botón para eliminar usuario
        "<button onclick='eliminar(" + u.id_usuario + ")' class='btn btn-danger btn-sm'>🗑 Eliminar</button>" +
      "</td></tr>";
    tbody.insertAdjacentHTML("beforeend", fila);
  });
}

// Función para eliminar un usuario por ID
function eliminar(id) {
  if (!confirm("¿Estás seguro de eliminar este usuario?")) return;

  fetch(API + "/" + id, { method: "DELETE" })
    .then(resp => {
      if (resp.ok) {
        alert("✅ Usuario eliminado correctamente");
        cargarUsuarios(); // Recargamos la lista después de eliminar
      } else {
        alert("❌ No se pudo eliminar el usuario");
      }
    })
    .catch(err => {
      console.error("Error al eliminar:", err);
      alert("⚠️ Error al conectar con el servidor");
    });
}

// Ejecutamos la carga de usuarios automáticamente al abrir la página
window.onload = cargarUsuarios;
</script>

</body>
</html>
