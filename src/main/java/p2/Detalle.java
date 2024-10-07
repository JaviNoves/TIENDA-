package p2;

public class Detalle {
    private int codigo_pedido;
    private int codigo_producto;
    private int unidades;
    private double precio_unitario;
    

    
    public int getCodigoPedido() {
        return codigo_pedido;
    }
    
    public void setCodigoPedido(int cogidoPedido) {
        this.codigo_pedido = cogidoPedido;
    }
    
    public int getCodigoProducto() {
        return codigo_producto;
    }
    
    public void setCodigoProducto(int codigoProducto) {
        this.codigo_producto = codigoProducto;
    }
    
    public int getUnidades() {
        return unidades;
    }
    
    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
    
    public double getPrecioUnitario() {
        return precio_unitario;
    }
    
    public void setPrecioUnitario(double precioUnitario) {
        this.precio_unitario = precioUnitario;
    }
}

