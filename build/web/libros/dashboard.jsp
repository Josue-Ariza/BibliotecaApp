<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Dashboard - Biblioteca</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
    }
    .menu-card {
      padding: 30px;
      border-radius: 10px;
      transition: transform 0.2s;
    }
    .menu-card:hover {
      transform: scale(1.05);
    }
    .menu-icon {
      font-size: 2rem;
      margin-bottom: 10px;
    }
  </style>
</head>
<body class="p-4">

<div class="container">
  <h1 class="text-center mb-5">📚 Menú Principal</h1>

  <!-- Menú principal en tarjetas -->
  <div class="row justify-content-center g-4">

    <div class="col-md-4">
      <div class="card menu-card text-center shadow-sm">
        <div class="menu-icon">📖</div>
        <h5>Ver Inventario</h5>
        <a href="${pageContext.request.contextPath}/libros/listalibros.jsp" 
           class="btn btn-outline-primary mt-3 w-100">Acceder</a>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card menu-card text-center shadow-sm">
        <div class="menu-icon">➕</div>
        <h5>Registrar Libro</h5>
        <a href="${pageContext.request.contextPath}/libros/agregarLibro.jsp" 
           class="btn btn-outline-success mt-3 w-100">Acceder</a>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card menu-card text-center shadow-sm">
        <div class="menu-icon">📋</div>
        <h5>Ver Préstamos</h5>
        <a href="${pageContext.request.contextPath}/prestamos/listaPrestamos.jsp" 
           class="btn btn-outline-secondary mt-3 w-100">Acceder</a>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card menu-card text-center shadow-sm">
        <div class="menu-icon">📝</div>
        <h5>Registrar Préstamo</h5>
        <a href="${pageContext.request.contextPath}/libros/librosDisponibles.jsp" 
           class="btn btn-outline-warning mt-3 w-100">Acceder</a>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card menu-card text-center shadow-sm">
        <div class="menu-icon">👤</div>
        <h5>Registrar Usuario</h5>
        <a href="${pageContext.request.contextPath}/usuarios/agregarUsuario.jsp" 
           class="btn btn-outline-success mt-3 w-100">Acceder</a>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card menu-card text-center shadow-sm">
        <div class="menu-icon">🗑️</div>
        <h5>Eliminar Usuario</h5>
        <a href="${pageContext.request.contextPath}/usuarios/eliminarUsuario.jsp" 
           class="btn btn-outline-danger mt-3 w-100">Acceder</a>
      </div>
    </div>

  </div>
</div>

</body>
</html>