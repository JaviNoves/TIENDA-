<%@ page language="java" contentType="text/html; charset=UTF-8" import="p2.*"  pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/reg.css">
    <title>Crear Usuario</title>
</head>
<body>
    <h1>Crear Usuario</h1>
    
    <%
    String mensaje = (String) session.getAttribute("mensaje");
    if (mensaje != null) {
        out.println("<p>" + mensaje + "</p>");
        session.removeAttribute("mensaje");
    }
    %>
    
    <form method="post" onsubmit="ProcesarForm(this,'RegistrarUsuario','cuerpo');return false">
        <label>Nombre de usuario:</label>
        <input type="text" name="usuario" required>
        
        <label>Contraseña:</label>
        <input type="password" name="clave" required>
        
        <label>Nombre:</label>
        <input type="text" name="nombre" required>
        
        <label>Apellidos:</label>
        <input type="text" name="apellidos" required>
        
        <label>Domicilio:</label>
        <input type="text" name="domicilio" required>
        
        <label>Población:</label>
        <input type="text" name="poblacion" required>
        
        <label>Provincia:</label>
        <input type="text" name="provincia" required>
        
        <label>Código Postal:</label>
        <input type="text" name="codigoPostal" required>
        
        <label>Teléfono:</label>
        <input type="text" name="telefono" required>
        
        <input type="submit" value="Crear Usuario">
    </form>
</body>
</html>
