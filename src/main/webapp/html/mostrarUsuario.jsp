<!-- mostrarUsuario.jsp -->

<%@ page import="p2.AccesoBD" %>
<%@ page import="p2.UsuarioBD" %>
<%
    // Obtener el c�digo de usuario de la sesi�n (asumiendo que se haya almacenado previamente)
    int codigoUsuario = (int) session.getAttribute("usuario");

    // Obtener instancia de AccesoBD y UsuarioBD
    AccesoBD accesoBD = AccesoBD.getInstance();
    UsuarioBD usuario = accesoBD.obtenerUsuarioBD(codigoUsuario);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Mostrar Usuario</title>
    <link rel="stylesheet" type="text/css" href="style4.css">
</head>
<body>
    <div class="container">
        <h1>Datos del Usuario</h1>
        <table>
            <tr>
                <th>C�digo</th>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>Domicilio</th>
                <th>Poblaci�n</th>
                <th>Provincia</th>
                <th>C�digo Postal</th>
                <th>Tel�fono</th>
            </tr>
            <tr>
                <td><%= usuario.getCodigo() %></td>
                <td><%= usuario.getNombre() %></td>
                <td><%= usuario.getApellidos() %></td>
                <td><%= usuario.getDomicilio() %></td>
                <td><%= usuario.getPoblacion() %></td>
                <td><%= usuario.getProvincia() %></td>
                <td><%= usuario.getCp() %></td>
                <td><%= usuario.getTelefono() %></td>
            </tr>
        </table>
        <button type="button" onclick="location.href='./html/editarUsuario.jsp'">Editar</button>
        <button type="button" onclick="location.href='./html/logout.jsp'">Cerrar Sesi�n</button>
    </div>
</body>
</html>
