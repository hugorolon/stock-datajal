package py.com.prestosoftware.data.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date fecha;
	
	@ManyToOne
    @JoinColumn(name = "proveedor_id")
	private Proveedor proveedor;
	
	@ManyToOne
    @JoinColumn(name = "moneda_id")
	private Moneda moneda;
	
	private String comprobante;
	private String condicion;
	private Double cotizacion;
	
	@Column(name = "total_general")
	private Double totalGeneral;
	
	@Column(name = "total_item")
	private Double totalItem;
	
	private String situacion;
	private String obs;
	
	@ElementCollection
    @CollectionTable(name = "pedido_detalles", joinColumns = @JoinColumn(name = "pedido_id"))
	private List<PedidoDetalle> items = new ArrayList<>();
	
	
	public Pedido() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Double getTotalGeneral() {
		return totalGeneral;
	}

	public void setTotalGeneral(Double totalGeneral) {
		this.totalGeneral = totalGeneral;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public Double getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Double cotizacion) {
		this.cotizacion = cotizacion;
	}

	public List<PedidoDetalle> getItems() {
		return items;
	}

	public void setItems(List<PedidoDetalle> items) {
		this.items = items;
	}

	public Double getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Double totalItem) {
		this.totalItem = totalItem;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
	
}