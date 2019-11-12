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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "devoluciones")
public class Devolucion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Temporal(TemporalType.DATE)
	private Date vencimiento;
	
	private String comprobante;
	private String credito;
	
	@Column(name = "tipo_devolucion")
	private String tipo;
	
	@Column(name = "cant_items")
	private Double cantItem;
	
	@NotNull
    @Column(name = "ref_id")
	private Long refId;
	
    @NotNull
    @Column(name = "referencia")
	private String referencia;
	
    @NotNull
	@ManyToOne
    @JoinColumn(name = "moneda_id")
	private Moneda moneda;
	
    @NotNull
	@ManyToOne
    @JoinColumn(name = "deposito_id")
	private Deposito deposito;
	
    @NotNull
	@ManyToOne
    @JoinColumn(name = "vendedor_id")
	private Usuario vendedor;
	
	private Double cotizacion;
	
	@Column(name = "total_exenta")
	private Double totalExenta;
	
	@Column(name = "total_gravada5")
	private Double totalGravada5;
	
	@Column(name = "total_gravada10")
	private Double totalGravada10;
	
	@Column(name = "total_iva5")
	private Double totalIva5;
	
	@Column(name = "total_iva10")
	private Double totalIva10;
	
	@NotNull
	@Column(name = "total_general")
	private Double totalGeneral;
	
	private String obs;
	
	private String situacion;
	
	@ElementCollection
	@CollectionTable(name = "devolucion_detalles", joinColumns = @JoinColumn(name = "devolucion_id"))
	private List<DevolucionDetalle> items = new ArrayList<>();
	
	public Devolucion() {}

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

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public String getCredito() {
		return credito;
	}

	public void setCredito(String credito) {
		this.credito = credito;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getRefId() {
		return refId;
	}
	
	public void setRefId(Long refId) {
		this.refId = refId;
	}
	
	public String getReferencia() {
		return referencia;
	}
	
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Double getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Double cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Double getTotalExenta() {
		return totalExenta;
	}

	public void setTotalExenta(Double totalExenta) {
		this.totalExenta = totalExenta;
	}

	public Double getTotalGravada5() {
		return totalGravada5;
	}

	public void setTotalGravada5(Double totalGravada5) {
		this.totalGravada5 = totalGravada5;
	}

	public Double getTotalGravada10() {
		return totalGravada10;
	}

	public void setTotalGravada10(Double totalGravada10) {
		this.totalGravada10 = totalGravada10;
	}

	public Double getTotalIva5() {
		return totalIva5;
	}

	public void setTotalIva5(Double totalIva5) {
		this.totalIva5 = totalIva5;
	}

	public Double getTotalIva10() {
		return totalIva10;
	}

	public void setTotalIva10(Double totalIva10) {
		this.totalIva10 = totalIva10;
	}

	public Double getTotalGeneral() {
		return totalGeneral;
	}

	public void setTotalGeneral(Double totalGeneral) {
		this.totalGeneral = totalGeneral;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	
	public Deposito getDeposito() {
		return deposito;
	}
	
	public Double getCantItem() {
		return cantItem;
	}
	
	public void setCantItem(Double cantItem) {
		this.cantItem = cantItem;
	}
	
	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public List<DevolucionDetalle> getItems() {
		return items;
	}

	public void setItems(List<DevolucionDetalle> items) {
		this.items = items;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

}