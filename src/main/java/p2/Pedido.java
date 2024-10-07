package p2;

import java.util.Date;

public class Pedido {
    private int codigo;
    private int persona;
    private Date fecha;
    private double importe;
    private int estado;
    
    public Pedido(int usuario, double total) {
		// TODO Auto-generated constructor stub
    	setPersona(usuario);
    	setImporte(total);
	}

	public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public int getPersona() {
        return persona;
    }
    
    public void setPersona(int persona) {
        this.persona = persona;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public double getImporte() {
        return importe;
    }
    
    public void setImporte(double importe) {
        this.importe = importe;
    }
    
    public int getEstado() {
        return estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }


}