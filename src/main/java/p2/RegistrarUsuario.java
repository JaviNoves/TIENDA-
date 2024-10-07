package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; // Para acceder al entorno de sesión

/**
 * Servlet implementation class RegistrarUsuario
 */
@WebServlet("/RegistrarUsuario")
public class RegistrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("clave");
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String domicilio = request.getParameter("domicilio");
		String poblacion = request.getParameter("poblacion");
		String provincia = request.getParameter("provincia");
		String codigoPostal = request.getParameter("codigoPostal");
		String telefono = request.getParameter("telefono");

		HttpSession session = request.getSession(true); // Accedemos al entorno de sesión
		AccesoBD con = AccesoBD.getInstance();
		int codigo = con.insertarUsuarioBD(usuario, clave, nombre, apellidos, domicilio, poblacion, provincia, codigoPostal, telefono);

		if (codigo > 0) {
		    session.setAttribute("mensaje", "Usuario creado exitosamente. Por favor, inicia sesión.");
		    response.sendRedirect("./html/loginUsuario.jsp");
		} else {
		    session.setAttribute("mensaje", "Error al crear el usuario. Por favor, intenta nuevamente.");
		    response.sendRedirect("./html/regstrar.jsp");
		}
	}

}
