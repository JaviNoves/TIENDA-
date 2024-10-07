package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CancelarPedido
 */
@WebServlet("/CancelarPedido")
public class CancelarPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession(true);
    		
        int codigoPedido = Integer.parseInt(request.getParameter("codigoPedido"));
        
        AccesoBD accesoBD = AccesoBD.getInstance();
        
        
        if( accesoBD.cancelarPedido(codigoPedido)){
        	session.setAttribute("message", "Pedido Cancelado");
        }else {
        	session.setAttribute("message", "no se puede cancelar");
		}
        
        response.sendRedirect("./html/loginUsuario.jsp");
    
	}

}