package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PedidoDetalle {
	
	@Column(name = "producto_id")
	private Long productoId;
	private String producto;
	private Double cantidad;
	private Double precio;
	private Double subtotal;
	
	public PedidoDetalle() { }

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
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
	
}