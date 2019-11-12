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
@Table(name = "presupuestos")
public class Presupuesto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date fecha;
	private String comprobante;
	private String situacion;
	
	@Column(name = "cant_item")
	private int cantItem;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Usuario vendedor;
	
	@Column(name = "total_general")
	private Double totalGeneral;
	
	@ElementCollection
    @CollectionTable(name = "presupuesto_detalles", joinColumns = @JoinColumn(name = "presupuesto_id"))
	private List<PresupuestoDetalle> items = new ArrayList<>();
	
	
	public Presupuesto() {}

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

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Double getTotalGeneral() {
		return totalGeneral;
	}

	public void setTotalGeneral(Double totalGeneral) {
		this.totalGeneral = totalGeneral;
	}

	public List<PresupuestoDetalle> getItems() {
		return items;
	}

	public void setItems(List<PresupuestoDetalle> items) {
		this.items = items;
	}

	public int getCantItem() {
		return cantItem;
	}

	public void setCantItem(int cantItem) {
		this.cantItem = cantItem;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}
	
}