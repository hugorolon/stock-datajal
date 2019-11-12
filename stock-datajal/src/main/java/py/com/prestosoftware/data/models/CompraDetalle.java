package py.com.prestosoftware.data.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompraDetalle {
	
	@Column(name = "producto_id")
	private Long productoId;
	private String producto;
	private Double cantidad;
	private Double precio;
	private Double subtotal;
	private Double gastos;
	@Column(name = "costo_cif")
	private Double costoCif;
	private Date fecha;
	@Column(name = "precio_fob")
	private Double precioFob;
	
	public CompraDetalle() {}

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

	public Double getGastos() {
		return gastos;
	}

	public void setGastos(Double gastos) {
		this.gastos = gastos;
	}

	public Double getCostoCif() {
		return costoCif;
	}

	public void setCostoCif(Double costoCif) {
		this.costoCif = costoCif;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getPrecioFob() {
		return precioFob;
	}

	public void setPrecioFob(Double precioFob) {
		this.precioFob = precioFob;
	}
	
}