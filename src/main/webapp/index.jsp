<%@ page language="java" contentType="text/html; charset=UTF-8" import="p2.*" pageEncoding="UTF8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Página de comprobación</title>
</head>
<body>
<%
AccesoBD con = AccesoBD.getInstance();
boolean res = con.comprobarAcceso();
con.cerrarConexionBD();
%>
<h1><%=res%></h1>
</body>
</html>
