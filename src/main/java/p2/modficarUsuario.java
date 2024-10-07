package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class modficarUsuario
 */
@WebServlet("/modficarUsuario")
public class modficarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modficarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int codigoUsuario = Integer.parseInt(request.getParameter("codigoUsuario"));
	        String nombre = request.getParameter("nombre");
	        String apellidos = request.getParameter("apellidos");
	        String domicilio = request.getParameter("domicilio");
	        String poblacion = request.getParameter("poblacion");
	        String provincia = request.getParameter("provincia");
	        String cpostal = request.getParameter("cpostal");
	        String telefono = request.getParameter("telefono");
	        String clave = request.getParameter("clave");

	        // Actualizar la información del usuario en la base de datos
	        AccesoBD accesoBD = AccesoBD.getInstance();
	        UsuarioBD usuario = accesoBD.obtenerUsuarioBD(codigoUsuario);
	        usuario.setNombre(nombre);
	        usuario.setApellidos(apellidos);
	        usuario.setDomicilio(domicilio);
	        usuario.setPoblacion(poblacion);
	        usuario.setProvincia(provincia);
	        usuario.setCp(cpostal);
	        usuario.setTelefono(telefono);
	        usuario.setClave(clave);
	        accesoBD.actualizarUsuarioBD(usuario);

	        response.sendRedirect("./html/loginUsuario.jsp");

	}

}
