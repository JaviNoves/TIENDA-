<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.List, p2.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/estiloprod.css">


<title>&nbsp;</title>
</head>
<body>
	<%
	AccesoBD con = AccesoBD.getInstance();
	List<ProductoBD> productos = con.obtenerProductosBD();
	%>
	<div>
		<h1>Tus Productos</h1>
		<%
		for (ProductoBD producto : productos) {
			int codigo = producto.getCodigo();
			String descripcion = producto.getDescripcion();
			String titulo = producto.getTitulo();
			float precio = producto.getPrecio();
			int existencias = producto.getStock();
			String imagen = producto.getImagen();
		%>

		<div class="producto">
			<img src=" <%=imagen%>" alt="<%=titulo%>">
			<div class="informacion-producto">
				<h2><%=titulo%></h2>
				<p><%=descripcion%></p>
				<p>
					Precio:
					<%=precio%>
				</p>
			</div>
		</div>
		<%
		if (existencias > 0) {
		%>
		<button onclick="AnadirCarrito('<%=codigo%>','<%=descripcion%>','<%=titulo%>','<%=precio%>','<%=existencias%>','<%=imagen%>')">Añadir al Carrito</button>

		<%
		} else {
		%>
		&nbsp;
		<%
		}
		%>
		<%
		}
		%>
	</div>
	<footer>
		<p>&copy; 2023 GAMERS</p>
	</footer>
	
</body>
</html>
