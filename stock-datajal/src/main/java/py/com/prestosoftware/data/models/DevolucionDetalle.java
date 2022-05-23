package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DevolucionDetalle {

    @Column(name = "producto_id")
	private Long productoId;
    private String producto;
	private Double cantidad;
	private Double costo;
	private Double subtotal;
	private Double cantidaddev;
	
	public DevolucionDetalle() {}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Long getProductoId() {
		return productoId;
	}

	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Double getCantidaddev() {
		return cantidaddev;
	}

	public void setCantidaddev(Double cantidaddev) {
		this.cantidaddev = cantidaddev;
	}
	

	
}