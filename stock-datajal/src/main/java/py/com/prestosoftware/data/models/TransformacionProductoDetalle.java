package py.com.prestosoftware.data.models;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TransformacionProductoDetalle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "codigo_producto_destino")
	private Producto codigoProductoDestino;
	private Double cantidad;
	private Double precio;

	public TransformacionProductoDetalle() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Producto getCodigoProductoDestino() {
		return codigoProductoDestino;
	}


	public void setCodigoProductoDestino(Producto codigoProductoDestino) {
		this.codigoProductoDestino = codigoProductoDestino;
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