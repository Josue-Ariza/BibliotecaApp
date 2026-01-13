<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*,com.biblioteca.config.Conexion"%>

<%
    // Obtenemos el parámetro id_libro desde la URL
    String idLibro = request.getParameter("id_libro");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Préstamo</title>
    <!-- Importamos Bootstrap para estilos y diseño responsivo -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
    <!-- Título principal -->
    <h1 class="mb-4 text-center">📘 Registrar Préstamo</h1>

    <!-- Formulario para registrar un préstamo -->
    <form id="formPrestamo" class="card p-4">
        
        <!-- Campo que muestra el libro seleccionado (solo lectura) -->
        <div class="mb-3">
            <label class="form-label">Libro seleccionado</label>
            <input type="text" id="titulo" class="form-control" disabled>
            <!-- Campo oculto con el ID del libro -->
            <input type="hidden" id="libro" name="id_libro" value="<%= idLibro%>">
        </div>

        <!-- Tabla con lista de usuarios disponibles -->
        <div class="mb-3">
            <label class="form-label">Seleccione un usuario</label>
            <table class="table table-bordered">
                <thead class="table-light">
                    <tr>
                        <th>Seleccionar</th>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Consultamos los usuarios desde la base de datos
                        try (Connection con = Conexion.getConnection(); 
                             PreparedStatement ps = con.prepareStatement("SELECT id_usuario, nombre, correo, telefono FROM usuarios"); 
                             ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                    %>
                    <tr>
                        <td>
                            <!-- Radio button para seleccionar un usuario -->
                            <input type="radio" name="id_usuario" value="<%= rs.getInt("id_usuario")%>" required>
                        </td>
                        <td><%= rs.getString("nombre")%></td>
                        <td><%= rs.getString("correo")%></td>
                        <td><%= rs.getString("telefono")%></td>
                    </tr>
                    <%
                            }
                        } catch (Exception e) {
                            // En caso de error mostramos mensaje en la tabla
                            out.println("<tr><td colspan='4'>Error cargando usuarios</td></tr>");
                            e.printStackTrace();
                        }
                    %>
                </tbody>
            </table>
        </div>

        <!-- Campo para seleccionar la fecha del préstamo -->
        <div class="mb-3">
            <label for="fecha" class="form-label">Fecha de préstamo</label>
            <input type="date" id="fecha" name="fecha_prestamo" class="form-control" required>
        </div>

        <!-- Campo para observaciones opcionales -->
        <div class="mb-3">
            <label for="obs" class="form-label">Observaciones</label>
            <textarea id="obs" name="observaciones" class="form-control" rows="3"></textarea>
        </div>

        <!-- Botón para registrar el préstamo -->
        <button type="submit" class="btn btn-success w-100">📘 Registrar</button>
    </form>
</div>

<!-- Botón para regresar al menú principal -->
<div class="mt-3 text-center">
    <a href="../libros/dashboard.jsp" class="btn btn-secondary">⬅️ Volver al Menú</a>
</div>

<!-- Toast flotante para mostrar mensaje de éxito -->
<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
    <div id="toastExito" class="toast align-items-center text-bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body">
                ✅ Préstamo registrado correctamente.
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Cerrar"></button>
        </div>
    </div>
</div>

<!-- Scripts de Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const idLibro = document.getElementById("libro").value;

    // Cargar título del libro seleccionado desde la API
    fetch("../api/librosDisponibles")
        .then(resp => resp.json())
        .then(lista => {
            const libro = lista.find(l => l.id_libro == idLibro);
            if (libro)
                document.getElementById("titulo").value = libro.titulo;
        })
        .catch(err => console.error("Error cargando libro:", err));

    // Evento para registrar préstamo al enviar el formulario
    document.getElementById("formPrestamo").addEventListener("submit", e => {
        e.preventDefault();

        // Validamos que se haya seleccionado un usuario
        const usuarioSeleccionado = document.querySelector("input[name='id_usuario']:checked");
        if (!usuarioSeleccionado) {
            alert("❌ Debe seleccionar un usuario");
            return;
        }

        // Construimos el objeto con los datos del préstamo
        const data = {
            id_libro: parseInt(document.getElementById("libro").value),
            id_usuario: parseInt(usuarioSeleccionado.value),
            fecha_prestamo: document.getElementById("fecha").value,
            observaciones: document.getElementById("obs").value
        };

        // Enviamos la solicitud POST a la API de préstamos
        fetch("../api/prestamos", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
        .then(resp => {
            if (resp.ok) {
                // Mostramos el toast de éxito
                const toast = new bootstrap.Toast(document.getElementById("toastExito"));
                toast.show();

                // Redirigimos al dashboard después de 3 segundos
                setTimeout(() => {
                    window.location.href = "../libros/dashboard.jsp";
                }, 3000);

                // Reseteamos el formulario
                document.getElementById("formPrestamo").reset();
            } else {
                alert("❌ Error al registrar el préstamo");
            }
        })
        .catch(err => console.error("Error registrando préstamo:", err));
    });
});
</script>

</body>
</html>
