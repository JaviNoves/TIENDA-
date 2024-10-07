package p2;

import java.io.IOException;

import java.io.InputStreamReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RecogerCarrito")
public class RecogerCarrito extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList <Producto> carritoJSON = new ArrayList<Producto>();
		
		HttpSession session = request.getSession(true);
		
		JsonReader jsonReader = Json.createReader(new InputStreamReader(request.getInputStream()));
		JsonArray jobj = jsonReader.readArray();
		AccesoBD con = AccesoBD.getInstance();

		for (int i = 0; i < jobj.size(); i++) {
			JsonObject prod = jobj.getJsonObject(i);
			Producto nuevo = new Producto();
			nuevo.setCodigo(prod.getInt("codigo"));
			nuevo.setNombre(prod.getString("titulo"));
			nuevo.setPrecio(Float.parseFloat(prod.get("precio").toString()));
			int pedido = prod.getInt("cantidad");
			int actual = con.CantidadExiste(nuevo.getCodigo());
			if(pedido>actual) {
				pedido=actual;
			}	
			nuevo.setCantidad(pedido);
			carritoJSON.add(nuevo);
		}
		
		session.setAttribute("carritoJSON",carritoJSON);

		response.sendRedirect("./html/resguardo.jsp");
	}
	
}