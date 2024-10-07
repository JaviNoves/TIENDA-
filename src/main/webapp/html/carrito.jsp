<%@ page language="java" contentType="text/html; charset=UTF-8" import="p2.*,java.util.ArrayList" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resumen Compra</title>
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
    <input type="hidden" name="url" value="./html/carrito.jsp">
    <label>Nombre de usuario:</label>
    <input type="text" name="usuario" required>
    <label>Contraseña:</label>
    <input type="password" name="clave" required>
    <input type="submit" value="Enviar">
</form>
<%
}
else {
%>
    <h1>Tu carrito</h1>
    
    <div id="cart-items">
    	<script>mostrarproductos();</script>
    </div>

	<div id="total-price">
		<script>preciototal();</script>
	</div>
    
    <div id="boton_compra">
    	<script>mostrarBoton();</script>
    </div>

    <footer>
        <p>&copy; 2023 GAMERS</p>
    </footer>
<%
}
 %>

</body>
</html>
