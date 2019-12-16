package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VentaDetalle {
	
	@Column(name = "producto_id")
	private Long productoId;
	private String producto;
	private Double cantidad;
	private Double precio;
	private Double subtotal;
	private Double descuento;
	
	@Column(name = "precio_fob")
	private Double precioFob;
	
	@Column(name = "precio_cif")
	private Double precioCif;
	
	@Column(name = "costo_fob")
	private Double costoFob;
	
	@Column(name = "costo_cif")
	private Double costoCif;
	
	@Column(name = "medio_fob")
	private Double medioFob;
	
	@Column(name = "medio_cif")
	private Double medioCif;
	
	public VentaDetalle() {}

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

	public Double getPrecioFob() {
		return precioFob;
	}

	public void setPrecioFob(Double precioFob) {
		this.precioFob = precioFob;
	}

	public Double getPrecioCif() {
		return precioCif;
	}

	public void setPrecioCif(Double precioCif) {
		this.precioCif = precioCif;
	}

	public Double getCostoFob() {
		return costoFob;
	}

	public void setCostoFob(Double costoFob) {
		this.costoFob = costoFob;
	}

	public Double getCostoCif() {
		return costoCif;
	}

	public void setCostoCif(Double costoCif) {
		this.costoCif = costoCif;
	}

	public Double getMedioFob() {
		return medioFob;
	}

	public void setMedioFob(Double medioFob) {
		this.medioFob = medioFob;
	}

	public Double getMedioCif() {
		return medioCif;
	}

	public void setMedioCif(Double medioCif) {
		this.medioCif = medioCif;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}
	
}