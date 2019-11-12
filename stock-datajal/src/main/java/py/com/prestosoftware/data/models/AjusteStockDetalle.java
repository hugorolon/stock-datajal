package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AjusteStockDetalle {

    @Column(name = "producto_id")
	private Long productoId;
    
    private String producto;
	
	@Column(name = "cantidad_actual")
	private Double cantidadActual;
	
	@Column(name = "cantidad_nueva")
	private Double cantidadNueva;
	
	@Column(name = "cantidad_diferencia")
	private Double cantidadDiferencia;
	
	private Double costo;
	
	@Column(name = "subtotal_costo")
	private Double subtotalCosto;
	
	public AjusteStockDetalle() {}

	public Double getCantidadActual() {
		return cantidadActual;
	}

	public void setCantidadActual(Double cantidadActual) {
		this.cantidadActual = cantidadActual;
	}

	public Double getCantidadNueva() {
		return cantidadNueva;
	}

	public void setCantidadNueva(Double cantidadNueva) {
		this.cantidadNueva = cantidadNueva;
	}

	public Double getCantidadDiferencia() {
		return cantidadDiferencia;
	}

	public void setCantidadDiferencia(Double cantidadDiferencia) {
		this.cantidadDiferencia = cantidadDiferencia;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Double getSubtotalCosto() {
		return subtotalCosto;
	}

	public void setSubtotalCosto(Double subtotalCosto) {
		this.subtotalCosto = subtotalCosto;
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