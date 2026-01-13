<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Registrar Usuario</title>
  <!-- Importamos Bootstrap para estilos y diseño responsivo -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">

<div class="container">
  <!-- Título principal de la página -->
  <h1 class="mb-4 text-center">👤 Registrar Nuevo Usuario</h1>

  <!-- Formulario para registrar un nuevo usuario -->
  <!-- Se envía al endpoint ../api/usuarios mediante método POST -->
  <form action="../api/usuarios" method="post" class="card p-4 shadow-sm">
    
    <!-- Campo para ingresar el nombre completo -->
    <div class="mb-3">
      <label class="form-label">Nombre completo</label>
      <input type="text" name="nombre" class="form-control" required>
    </div>

    <!-- Campo para ingresar el correo electrónico -->
    <div class="mb-3">
      <label class="form-label">Correo electrónico</label>
      <input type="email" name="correo" class="form-control" required>
    </div>

    <!-- Campo para ingresar el teléfono (opcional) -->
    <div class="mb-3">
      <label class="form-label">Teléfono</label>
      <input type="text" name="telefono" class="form-control">
    </div>

    <!-- Botón para enviar el formulario y registrar el usuario -->
    <button type="submit" class="btn btn-success w-100">➕ Registrar Usuario</button>
  </form>

  <!-- Botón para regresar al menú principal -->
  <div class="mt-3 text-center">
    <a href="../libros/dashboard.jsp" class="btn btn-secondary">
      ⬅️ Volver al Menú
    </a>
  </div>
</div>

</body>
</html>
