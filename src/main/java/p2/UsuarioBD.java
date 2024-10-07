
package p2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioBD {

    private int codigo;
    private int activo;
    private int admin;
    private String usuario;
    private String clave;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private String poblacion;
    private String provincia;
    private String cp;
    private String telefono;

    public UsuarioBD() {
    }

    public UsuarioBD(int codigo, int activo, int admin, String usuario, String clave, String nombre, String apellidos, String domicilio, String poblacion, String provincia, String cp, String telefono) {
        this.codigo = codigo;
        this.activo = activo;
        this.admin = admin;
        this.usuario = usuario;
        this.clave = clave;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.cp = cp;
        this.telefono = telefono;
    }

    // Getters y setters

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    
    
    
    
    public UsuarioBD obtenerUsuarioBD(int codigoUsuario) {
        AccesoBD accesoBD = AccesoBD.getInstance();
        accesoBD.abrirConexionBD();
        UsuarioBD usuario = null;
        try {
            String consulta = "SELECT * FROM usuarios WHERE codigo = ?";
            PreparedStatement statement = accesoBD.getConexionBD().prepareStatement(consulta);
            statement.setInt(1, codigoUsuario);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                usuario = new UsuarioBD();
                usuario.setCodigo(resultado.getInt("codigo"));
                usuario.setActivo(resultado.getInt("activo"));
                usuario.setAdmin(resultado.getInt("admin"));
                usuario.setUsuario(resultado.getString("usuario"));
                usuario.setClave(resultado.getString("clave"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellidos(resultado.getString("apellidos"));
                usuario.setDomicilio(resultado.getString("domicilio"));
                usuario.setPoblacion(resultado.getString("poblacion"));
                usuario.setProvincia(resultado.getString("provincia"));
                usuario.setCp(resultado.getString("cp"));
                usuario.setTelefono(resultado.getString("telefono"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el usuario de la base de datos");
            System.err.println(e.getMessage());
        } finally {
            accesoBD.cerrarConexionBD();
        }
        return usuario;
    }
}
	

