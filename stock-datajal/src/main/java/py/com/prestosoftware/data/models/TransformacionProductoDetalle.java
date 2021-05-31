package py.com.prestosoftware.data.models;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TransformacionProductoDetalle {
	
	
	@ManyToOne
	@JoinColumn(name = "producto_destino")
	private Producto productoDestino;
	private Double cantidad;
	private Double precio;

	public TransformacionProductoDetalle() {}


	public Producto getProductoDestino() {
		return productoDestino;
	}


	public void setProductoDestino(Producto productoDestino) {
		this.productoDestino = productoDestino;
	}


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


}