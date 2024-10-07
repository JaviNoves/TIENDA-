package p2;

import java.sql.*;
import java.sql.Date;

import p2.UsuarioBD;


import java.util.ArrayList;
import java.util.List;
import java.util.*;

public final class AccesoBD {
	private static AccesoBD instanciaUnica = null;
	private Connection conexionBD = null;

	public static AccesoBD getInstance() {
		if (instanciaUnica == null) {
			instanciaUnica = new AccesoBD();
		}
		return instanciaUnica;
	}

	AccesoBD() {
		abrirConexionBD();
	}

	public void abrirConexionBD() {
		if (getConexionBD() == null) { 
			String nombreConexionBD = "jdbc:mysql://localhost/daw";
			try { 
				Class.forName("com.mysql.cj.jdbc.Driver");
				setConexionBD(DriverManager.getConnection(nombreConexionBD, "root", ""));
			} catch (Exception e) {
				System.err.println("No se ha podido conectar a la base de datos");
				System.err.println(e.getMessage());
			}
		}
	}

	public void cerrarConexionBD() {
		if (getConexionBD() != null) {
			try {
				getConexionBD().close();
				setConexionBD(null);
			} catch (Exception e) {
				System.err.println("No se ha podido desconectar de la base de datos");
				System.err.println(e.getMessage());
			}
		}
	}

	public boolean comprobarAcceso() {
		abrirConexionBD();
		boolean res = (getConexionBD() != null);
		cerrarConexionBD();
		return res;
	}
	public List<ProductoBD> obtenerProductosBD() {
		abrirConexionBD();
		ArrayList<ProductoBD> productos = new ArrayList<>();
		try {
		// hay que tener en cuenta las columnas de vuestra tabla de productos
		// también se puede utilizar una consulta del tipo:
		// con = "SELECT * FROM productos";
		String con = "SELECT codigo,titulo,descripcion,precio,existencias,imagen FROM productos";
		Statement s = getConexionBD().createStatement();
		ResultSet resultado = s.executeQuery(con);
		while(resultado.next()){
		ProductoBD producto = new ProductoBD();
		producto.setCodigo(resultado.getInt("codigo"));
		producto.setTitulo(resultado.getString("titulo"));
		producto.setDescripcion(resultado.getString("descripcion"));
		producto.setPrecio(resultado.getFloat("precio"));
		producto.setStock(resultado.getInt("existencias"));
		producto.setImagen(resultado.getString("imagen"));
		productos.add(producto);
		}
		}
		catch(Exception e) {
		System.err.println("Error ejecutando la consulta a la base de datos");
		System.err.println(e.getMessage());
		}
		return productos;
		}
	public int comprobarUsuarioBD(String usuario, String clave) {
		abrirConexionBD();
		try{
		String con = "SELECT codigo FROM usuarios WHERE usuario=? AND clave=?";
		PreparedStatement s = getConexionBD().prepareStatement(con);
		s.setString(1,usuario);
		s.setString(2,clave);
		 ResultSet resultado = s.executeQuery();
		if ( resultado.next() ) { // El usuario/clave se encuentra en la BD
		return resultado.getInt("codigo");
		}
		}
		catch(Exception e) { // Error en la conexión con la BD
		System.err.println("Error verificando usuario/clave");
		System.err.println(e.getMessage());
		}
		return -1;
		}

	public Connection getConexionBD() {
		return conexionBD;
	}

	public void setConexionBD(Connection conexionBD) {
		this.conexionBD = conexionBD;
		
	}
	private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	private void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

	public int insertarUsuarioBD(String usuario, String clave, String nombre, String apellidos, String domicilio, String poblacion, String provincia, String cp, String telefono) {
	    abrirConexionBD();
	    try {
	        String query = "INSERT INTO usuarios (activo, admin, usuario, clave, nombre, apellidos, domicilio, poblacion, provincia, cp, telefono) " +
	                       "VALUES (1, 0, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement statement = getConexionBD().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        statement.setString(1, usuario);
	        statement.setString(2, clave);
	        statement.setString(3, nombre);
	        statement.setString(4, apellidos);
	        statement.setString(5, domicilio);
	        statement.setString(6, poblacion);
	        statement.setString(7, provincia);
	        statement.setString(8, cp);
	        statement.setString(9, telefono);

	        int affectedRows = statement.executeUpdate();
	        if (affectedRows > 0) {
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int codigo = generatedKeys.getInt(1);
	                return codigo;
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error executing insert query");
	        System.err.println(e.getMessage());
	    }
	    return -1;
	}
	public UsuarioBD obtenerUsuarioBD(int codigoUsuario) {
	    abrirConexionBD();
	    UsuarioBD usuarioBD = null;
	    try {
	        String consulta = "SELECT * FROM usuarios WHERE codigo=?";
	        PreparedStatement statement = getConexionBD().prepareStatement(consulta);
	        statement.setInt(1, codigoUsuario);
	        ResultSet resultado = statement.executeQuery();
	        if (resultado.next()) {
	            usuarioBD = new UsuarioBD();
	            usuarioBD.setCodigo(resultado.getInt("codigo"));
	            usuarioBD.setActivo(resultado.getInt("activo"));
	            usuarioBD.setAdmin(resultado.getInt("admin"));
	            usuarioBD.setUsuario(resultado.getString("usuario"));
	            usuarioBD.setClave(resultado.getString("clave"));
	            usuarioBD.setNombre(resultado.getString("nombre"));
	            usuarioBD.setApellidos(resultado.getString("apellidos"));
	            usuarioBD.setDomicilio(resultado.getString("domicilio"));
	            usuarioBD.setPoblacion(resultado.getString("poblacion"));
	            usuarioBD.setProvincia(resultado.getString("provincia"));
	            usuarioBD.setCp(resultado.getString("cp"));
	            usuarioBD.setTelefono(resultado.getString("telefono"));
	        }
	    } catch (SQLException e) {
	        System.err.println("Error executing the database query");
	        System.err.println(e.getMessage());
	    } finally {
	        cerrarConexionBD();
	    }
	    return usuarioBD;
	}
	
	
	public boolean actualizarUsuarioBD(UsuarioBD usuario) {
        try {
            AccesoBD accesoBD = new AccesoBD(); // Crear una instancia de AccesoBD
            Connection conexion = accesoBD.getConexionBD(); // Llamar al método en la instancia
            
            String sql = "UPDATE usuarios SET  usuario = ?, clave = ?, nombre = ?, apellidos = ?, domicilio = ?, poblacion = ?, provincia = ?, cp = ?, telefono = ? WHERE codigo = ?";
            
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getClave());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellidos());
            stmt.setString(5, usuario.getDomicilio());
            stmt.setString(6, usuario.getPoblacion());
            stmt.setString(7, usuario.getProvincia());
            stmt.setString(8, usuario.getCp());
            stmt.setString(9, usuario.getTelefono());
            stmt.setInt(10, usuario.getCodigo());

            int filasAfectadas = stmt.executeUpdate();

            stmt.close();
            conexion.close();

            return (filasAfectadas > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	public int CantidadExiste(int codigo) {
		abrirConexionBD();
		try {
        
			String sql = "SELECT existencias FROM productos WHERE codigo=?";
			PreparedStatement stmt = getConexionBD().prepareStatement(sql);
			stmt.setInt(1,codigo);
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				return resultado.getInt("existencias");
			}
		}
		catch (Exception e) {
			System.err.println("Error ejecutando la consulta");
			System.err.println(e.getMessage());
		}
		
		return 0;
	}
	
	public int agregarPedidoBD(int user,float importe, int estado) {
        abrirConexionBD();
        try {
            String query = "INSERT INTO pedidos (persona, fecha, importe, estado) VALUES (?, now(), ?, ?)";
            PreparedStatement statement = getConexionBD().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user);
            statement.setDouble(2, importe);
            statement.setInt(3, estado);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int codigo = generatedKeys.getInt(1);
                    return codigo;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        } finally {
            cerrarConexionBD();
        }
        return -1;
    }
	
	
	
	public void guardarDetalle(Detalle detalle) {
        abrirConexionBD();
        
        try {
            String query = "INSERT INTO detalle (codigo_pedido, codigo_producto, unidades, precio_unitario) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = getConexionBD().prepareStatement(query);
            statement.setInt(1, detalle.getCodigoPedido());
            statement.setInt(2, detalle.getCodigoProducto());
            statement.setInt(3, detalle.getUnidades());
            statement.setDouble(4, detalle.getPrecioUnitario());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        } finally {
        cerrarConexionBD();
        }
    }
	public List<Pedido> obtenerPedidosBD() {
	    abrirConexionBD();
	    ArrayList<Pedido> pedidos = new ArrayList<>();
	    try {
	        String consulta = "SELECT codigo, persona, fecha, importe, estado FROM pedidos";
	        Statement statement = getConexionBD().createStatement();
	        ResultSet resultado = statement.executeQuery(consulta);
	        while (resultado.next()) {
	            int codigo = resultado.getInt("codigo");
	            int persona = resultado.getInt("persona");
	            Date fecha = resultado.getDate("fecha");
	            double importe = resultado.getDouble("importe");
	            int estado = resultado.getInt("estado");
	            Pedido pedido = new Pedido(persona, importe);
	            pedido.setCodigo(codigo);
	            pedido.setFecha(fecha);
	            pedido.setEstado(estado);
	            pedidos.add(pedido);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error ejecutando la consulta a la base de datos");
	        System.err.println(e.getMessage());
	    } finally {
	        cerrarConexionBD();
	    }
	    return pedidos;
	}
	
	
	public boolean cancelarPedido(int codigoPedido) {
	    abrirConexionBD();
	    try {
	        String sql = "UPDATE pedidos SET estado = 4 WHERE codigo = ?";
	        PreparedStatement stmt = conexionBD.prepareStatement(sql);
	        stmt.setInt(1, codigoPedido);

	        int affectedRows = stmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        System.err.println("Error al cancelar el pedido");
	        System.err.println(e.getMessage());
	        return false;
	    } finally {
	        cerrarConexionBD();
	    }
	}
	
	public List<Pedido> obtenerPedidosBD(int codigoUsuario) {
	    abrirConexionBD();
	    

	    List<Pedido> pedidos = new ArrayList<>();
	    try {
	        String consulta = "SELECT codigo, persona, fecha, importe, estado FROM pedidos WHERE persona = ?";
	        PreparedStatement statement = getConexionBD().prepareStatement(consulta);
	        statement.setInt(1, codigoUsuario);
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	            int codigo = resultado.getInt("codigo");
	            int persona = resultado.getInt("persona");
	            Date fecha = resultado.getDate("fecha");
	            double importe = resultado.getDouble("importe");
	            int estado = resultado.getInt("estado");
	            Pedido pedido = new Pedido(persona, importe);
	            pedido.setCodigo(codigo);
	            pedido.setFecha(fecha);
	            pedido.setEstado(estado);
	            pedidos.add(pedido);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error ejecutando la consulta a la base de datos");
	        System.err.println(e.getMessage());
	    } finally {
	        cerrarConexionBD();
	    }
	    return pedidos;
	}

	public Map<Integer, Detalle> obtenerDetallesPorPedido(int codigoPedido) {
		abrirConexionBD();
        Map<Integer, Detalle> detalles = new HashMap<>();
        try {
	        String consulta = "SELECT * FROM detalle WHERE codigo_pedido = ?";
	        PreparedStatement statement = getConexionBD().prepareStatement(consulta);
	        statement.setInt(1, codigoPedido);
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	        	 Detalle detalle = new Detalle();
	                detalle.setCodigoPedido(resultado.getInt("codigo_pedido"));
	                detalle.setCodigoProducto(resultado.getInt("codigo_producto"));
	                detalle.setUnidades(resultado.getInt("unidades"));
	                detalle.setPrecioUnitario(resultado.getDouble("precio_unitario"));

	                // Add the Detalle object to the map using the product code as the key
	                detalles.put(detalle.getCodigoProducto(), detalle);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error ejecutando la consulta a la base de datos");
	        System.err.println(e.getMessage());
	    } finally {
	        cerrarConexionBD();
	    }
	    return detalles;
        
	}




	
    
}
    
