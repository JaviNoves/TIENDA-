<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="p2.*,java.util.ArrayList" pageEncoding="UTF-8"%>
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
	<h1><%=mensaje%></h1>
	<%
	}

	// Verificamos si el usuario está autenticado
	if (session.getAttribute("usuario") == null || (Integer) session.getAttribute("usuario") <= 0) {
	// Mostramos el formulario para la introducción del usuario y la clave
	%>
	<form method="post"
		onsubmit="ProcesarForm(this,'Login','cuerpo');return false">
		<input type="hidden" name="url" value="./html/resguardo.jsp">
		<label>Nombre de usuario:</label> <input type="text" name="usuario"
			required> <label>Contraseña:</label> <input type="password"
			name="clave" required> <input type="submit" value="Enviar">
	</form>
	<%
	} else {
	%>
	<div class="container">
		<%
		// Obtener el código de usuario de la sesión (asumiendo que se haya almacenado previamente)
		int codigoUsuario = (int) session.getAttribute("usuario");
		String message = (String) session.getAttribute("erroMensaje");

		// Obtener instancia de AccesoBD y UsuarioBD
		AccesoBD accesoBD = AccesoBD.getInstance();
		UsuarioBD usuario = accesoBD.obtenerUsuarioBD(codigoUsuario);
		if (message != null) {
		%>
		<h1>
			<%=message%>
		</h1>
		<%
		}
		%>

		<h1>Resumen Compra</h1>
		<h2>Editar información del usuario:</h2>
		<form method="post"
			onsubmit="ProcesarForm(this,'modficarUsuario','cuerpo');return false">
			<input type="hidden" name="codigoUsuario"
				value="<%=usuario.getCodigo()%>"> <label>Nombre:</label> <input
				type="text" name="nombre" value="<%=usuario.getNombre()%>"
				required> <label>Apellidos:</label> <input type="text"
				name="apellidos" value="<%=usuario.getApellidos()%>" required>
			<label>Domicilio:</label> <input type="text" name="domicilio"
				value="<%=usuario.getDomicilio()%>" required> <label>Poblacion:</label>
			<input type="text" name="poblacion"
				value="<%=usuario.getPoblacion()%>" required> <label>Provincia:</label>
			<input type="text" name="provincia"
				value="<%=usuario.getProvincia()%>" required> <label>Código
				Postal:</label> <input type="text" name="cpostal"
				value="<%=usuario.getCp()%>" required> <label>Teléfono:</label>
			<input type="text" name="telefono"
				value="<%=usuario.getTelefono()%>" required> <input
				type="submit" value="Actualizar">
		</form>
		<%
		// 获取carritoJSON变量
		ArrayList<Producto> carritoJSON = (ArrayList<Producto>) session.getAttribute("carritoJSON");
		%>

		<div class="col">
			<div class="container">
				<h2 class=>Carrito de Compras</h2>
				<%
				if (carritoJSON != null && !carritoJSON.isEmpty()) {
				%>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Código</th>
							<th>Nombre</th>
							<th>Precio</th>
							<th>Cantidad</th>
						</tr>
					</thead>
					<tbody>
						<%
						float total = 0; // Inicializar el total en 0
						for (Producto producto : carritoJSON) {
							float subtotal = producto.getPrecio() * producto.getCantidad();
							total += subtotal;
						%>
						<tr>
							<td><%=producto.getCodigo()%></td>
							<td><%=producto.getNombre()%></td>
							<td><%=producto.getPrecio()%></td>
							<td><%=producto.getCantidad()%></td>
						</tr>
						<%
						}
						%>

						<!-- Fila del total -->
						<tr>
							<td colspan="2" class="text-end fw-bold">Total:</td>
							<td class="fw-bold"><%=total%></td>
						</tr>

					</tbody>
				</table>
				<form>
			<label for="metodo-pago">Método de Pago:</label> <select
				id="metodo-pago" name="metodo-pago">
				<option value="contrareembolso">Contrareembolso</option>
				<option value="tarjeta">Tarjeta de Crédito</option>
			</select>

			<div id="datos-tarjeta" style="display: none;">
				<label for="numero-tarjeta">Número de Tarjeta:</label> <input
					type="text" id="numero-tarjeta" name="numero-tarjeta" required><br>

				<label for="nombre-tarjeta">Nombre en la Tarjeta:</label> <input
					type="text" id="nombre-tarjeta" name="nombre-tarjeta" required><br>

				<label for="fecha-expiracion">Fecha de Expiración:</label> <input
					type="text" id="fecha-expiracion" name="fecha-expiracion" required><br>

				<label for="cvv">CVV:</label> <input type="text" id="cvv" name="cvv"
					required><br>
			</div>

		</form>

		<form method="post"
			onsubmit="ProcesarForm(this,'Tramitacion','cuerpo'); return false">


			<button type="submit">Completar compra</button>

		</form>
		<button type="button" onclick="cancelarCompra()">Cancelar</button>
	</div>
				<%
				session.setAttribute("total", total);
				} else {
				%>
				<p>No hay productos en el carrito.</p>
				<%
				}
				}
				%>
			</div>
		</div>





		
	<script>
		function cancelarCompra() {
			Cargar('./html/tienda2.html', 'cuerpo');
		}

		var metodoPagoSelect = document.getElementById("metodo-pago");
		var datosTarjetaDiv = document.getElementById("datos-tarjeta");

		metodoPagoSelect.addEventListener("change", function() {
			if (metodoPagoSelect.value === "tarjeta") {
				datosTarjetaDiv.style.display = "block";
			} else {
				datosTarjetaDiv.style.display = "none";
			}
		});
	</script>
</body>
</html>
