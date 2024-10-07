<%@ page language="java" contentType="text/html; charset=UTF-8" import="p2.*" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Map" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>Iniciar Sesión</title>
    
    
    <script>
        function loginSubmit(formulario) {
            ProcesarForm(formulario, 'Login', 'cuerpo');
            return false;
        }
        function mostrarMensaje() {
            alert("Datos actualizados");
        }
       


    </script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
</head>
<body>
<%
// Utilizamos una variable en la sesión para informar de los mensajes de Error
String mensaje = (String) session.getAttribute("mensaje");
if (mensaje != null) {
    // Eliminamos el mensaje consumido
    session.removeAttribute("mensaje");
%>
<h1><%= mensaje %></h1>
<%
}

// Verificamos si el usuario está autenticado
if (session.getAttribute("usuario") == null || (Integer) session.getAttribute("usuario") <= 0) {
    // Mostramos el formulario para la introducción del usuario y la clave
%>
<form method="post" onsubmit="ProcesarForm(this,'Login','cuerpo');return false" >
    <input type="hidden" name="url" value="./html/loginUsuario.jsp">
    <label>Nombre de usuario:</label>
    <input type="text" name="usuario" required>
    <label>Contraseña:</label>
    <input type="password" name="clave" required>
    <input type="submit" value="Enviar">
</form>
<%
} 

 else {
    // El usuario ha iniciado sesión correctamente
    
    // Obtener el código de usuario de la sesión (asumiendo que se haya almacenado previamente)
    int codigoUsuario = (int) session.getAttribute("usuario");
    
    // Obtener instancia de AccesoBD y UsuarioBD
    AccesoBD accesoBD = AccesoBD.getInstance();
    UsuarioBD usuario = accesoBD.obtenerUsuarioBD(codigoUsuario);
%>

<!-- Aquí puedes agregar el código HTML para mostrar la información del usuario -->
<div class="user-info">
    <h1>Bienvenido, <%= usuario.getNombre() %></h1>
    <p>Información del usuario:</p>
    <ul>
        <li>Nombre: <%= usuario.getNombre() %></li>
        <li>Apellidos: <%= usuario.getApellidos() %></li>
        <li>Domicilio: <%= usuario.getDomicilio() %></li>
        <li>Poblacion: <%= usuario.getPoblacion() %></li>
        <li>Provincia: <%= usuario.getProvincia() %></li>
        <li>CPostal: <%= usuario.getCp() %></li>
        <li>Telefono: <%= usuario.getTelefono() %></li>
        <li>Contraseña: *********</li> <!-- Se muestra la contraseña censurada -->
        <!-- Agrega más detalles del usuario según sea necesario -->
    </ul>
    

    <h2>Editar información del usuario:</h2>
    <form method="post" onsubmit="ProcesarForm(this,'modficarUsuario','cuerpo');return false">
        <input type="hidden" name="codigoUsuario" value="<%= usuario.getCodigo() %>">
        <label>Nombre:</label>
        <input type="text" name="nombre" value="<%= usuario.getNombre() %>" required>
        <label>Apellidos:</label>
        <input type="text" name="apellidos" value="<%= usuario.getApellidos() %>" required>
        <label>Domicilio:</label>
        <input type="text" name="domicilio" value="<%= usuario.getDomicilio() %>" required>
        <label>Poblacion:</label>
        <input type="text" name="poblacion" value="<%= usuario.getPoblacion() %>" required>
        <label>Provincia:</label>
        <input type="text" name="provincia" value="<%= usuario.getProvincia() %>" required>
        <label>Código Postal:</label>
        <input type="text" name="cpostal" value="<%= usuario.getCp() %>" required>
        <label>Teléfono:</label>
        <input type="text" name="telefono" value="<%= usuario.getTelefono() %>" required>
        <label>Contraseña:</label>
        <input type="password" name="clave" value="<%= usuario.getClave() %>" required>
        <input type="submit" value="Actualizar" onclick="mostrarMensaje()">
    </form>
    

   <!-- Mostrar Pedidos -->
    <h2>Pedidos:</h2>
    <table>
        <tr>
            <th>Código</th>
            <th>Persona</th>
            <th>Fecha</th>
            <th>Importe</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        <%
        int codigoUsuario1 = (int) session.getAttribute("usuario");
        List<Pedido> pedidos = accesoBD.obtenerPedidosBD(codigoUsuario1);

        for (Pedido pedido : pedidos) {
        %>
        <tr>
            <td><%= pedido.getCodigo() %></td>
            <td><%= pedido.getPersona() %></td>
            <td><%= pedido.getFecha() %></td>
            <td><%= pedido.getImporte() %></td>
            <td><%= pedido.getEstado() %></td>
            <td>
    <% if (pedido.getEstado() == 1) { %>
        <form method="post" onsubmit="ProcesarForm(this,'CancelarPedido','cuerpo');return false" >
        <input type="hidden" name="codigoPedido" value="<%= pedido.getCodigo() %>">
        <input class="btn btn-warning" type="submit" value="Cancelar">
        </form>
    <% } %>
</td>
         <td>
                <%-- Retrieve the details for the current order --%>
                <% Map<Integer, Detalle> detalles = accesoBD.obtenerDetallesPorPedido(pedido.getCodigo()); %>
                <%-- Check if there are any details for the current order --%>
                <% if (detalles != null && !detalles.isEmpty()) { %>
                    <ul>
                        <%-- Iterate over the details and display them --%>
                        <% for (Detalle detalle : detalles.values()) { %>
                            <li>CodigoProducto: <%= detalle.getCodigoProducto()     %>,   Unidades: <%= detalle.getUnidades() %>, Precio Unitario: <%= detalle.getPrecioUnitario() %></li>
                        <% } %>
                    </ul>
                <% } else { %>
                    No hay detalles disponibles.
                <% } %>
            </td>   
            
        </tr>
        <%
        }
        %>
    </table>
    
</div>

<!-- Aquí puedes agregar el resto del contenido de la página -->
<!-- Por ejemplo, un botón para cerrar sesión -->
<button type="button" onclick="Cargar('logout','cuerpo')">Cerrar Sesión</button>
<% }
%>
</body>
</html>