package py.com.prestosoftware.data.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Embeddable
public class Lotes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_producto")
	private Long idProducto;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_final")
	private Date fechaFinal;
	
	@Column(name = "precio_compra")
	private Double precioCompra;

	@Column(name = "precio_venta")
	private Double precioVenta;
	
	@Column(name = "stock")
	private Double stock;

	
	public Lotes() {}
	public Lotes(Long id) {
		this.id = id;
	}
	
	public Lotes(Date fechaFinal, Double precioCompra, Double precioVenta, Double stock) {
		this.fechaFinal = fechaFinal;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.stock =stock;		
	}

	public Double getStock() {
		return stock;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public Double getPrecioCompra() {
		return precioCompra;
	}
	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}
	public Double getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}

}