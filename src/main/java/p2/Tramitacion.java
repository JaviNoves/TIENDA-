package p2;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Tramitacion
 */
@WebServlet("/Tramitacion")
public class Tramitacion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        int codigoUsuario = (int) session.getAttribute("usuario");
        float total = (float) session.getAttribute("total");
        int estado=1;
        // Obtener instancia de AccesoBD y UsuarioBD
        AccesoBD accesoBD = AccesoBD.getInstance();
        int cogidoPedido = accesoBD.agregarPedidoBD(codigoUsuario,total,estado);
    
 ArrayList<Producto> carritoJSON = (ArrayList<Producto>) session.getAttribute("carritoJSON");
        
        // Guardar el detalle del pedido
        for (Producto producto : carritoJSON) {
            Detalle detalle = new Detalle();
            detalle.setCodigoPedido(cogidoPedido);
            detalle.setCodigoProducto(producto.getCodigo());
            detalle.setUnidades(producto.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            
            // Guardar el detalle en la base de datos
            accesoBD.guardarDetalle(detalle);
        }
        
         
        
        
        
        
         
        UsuarioBD usuario = accesoBD.obtenerUsuarioBD(codigoUsuario);

        if(usuario.getPoblacion()==null||usuario.getDomicilio()==null||usuario.getProvincia()==null||usuario.getTelefono()==null||usuario.getCp()==null||usuario.getNombre()==null||usuario.getApellidos()==null)
        {
        	
			session.setAttribute("erroMensaje","falta datos de envio" );
			
			response.sendRedirect("./html/resguardo.jsp");
        }
        else{
        // Limpiar el carrito y la variable de sesión
        session.removeAttribute("carritoJSON");
        session.removeAttribute("total");        
        response.sendRedirect("./html/pedidofinalizado.jsp");
        }
    }
	
	
	
}