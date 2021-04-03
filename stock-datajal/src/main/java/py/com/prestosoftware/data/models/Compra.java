package py.com.prestosoftware.data.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "compras")
public class Compra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int operacion;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_compra")
	private Date fechaCompra;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "vencimiento")
	private Date vencimiento;
	
	@Column(name = "tipo_compra")
	private String tipoCompra;
	
	@Column(name = "nro_despacho")
	private String nroDespacho;
	
	private String factura;
	private String tc;
	private String comprobante;
	private int condicion;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "proveedor_id")
	private Proveedor proveedor;
	
	@Column(name = "proveedor_nombre")
	private String proveedorNombre;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "moneda_id")
	private Moneda moneda;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "caja_id")
	private Caja caja;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "deposito_id")
	private Deposito deposito;
	
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
	
	@Column(name = "total_pagado")
	private Double totalPagado;
	
	@NotNull
	@Column(name = "total_fob")
	private Double totalFob;
	
	@Column(name = "total_cif")
	private Double totalCif;
	
	@Column(name = "total_item")
	private Double totalItem;
	
	@Column(name = "descuento")
	private Double descuento;
	
	@Column(name = "cuota_cant")
	private Integer cuotaCant;
	
	@Column(name = "gastos")
	private Double gastos;
	
	@Column(name = "obs")
	private String obs;
	
	@Column(name = "situacion")
	private String situacion;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "compra_detalles", joinColumns = @JoinColumn(name = "compra_id"))
	private List<CompraDetalle> items = new ArrayList<>();
	
	public Compra() {}

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

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public int getCondicion() {
		return condicion;
	}

	public void setCondicion(int condicion) {
		this.condicion = condicion;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
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
	
	public String getProveedorNombre() {
		return proveedorNombre;
	}
	
	public void setProveedorNombre(String proveedorNombre) {
		this.proveedorNombre = proveedorNombre;
	}
	
	public List<CompraDetalle> getItems() {
		return items;
	}
	
	public void setItems(List<CompraDetalle> items) {
		this.items = items;
	}

	public String getTipoCompra() {
		return tipoCompra;
	}

	public void setTipoCompra(String tipoCompra) {
		this.tipoCompra = tipoCompra;
	}

	public String getNroDespacho() {
		return nroDespacho;
	}

	public void setNroDespacho(String nroDespacho) {
		this.nroDespacho = nroDespacho;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}
	
	public Double getTotalItem() {
		return totalItem;
	}
	
	public void setTotalItem(Double totalItem) {
		this.totalItem = totalItem;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getGastos() {
		return gastos;
	}

	public void setGastos(Double gastos) {
		this.gastos = gastos;
	}

	public Double getTotalFob() {
		return totalFob;
	}

	public void setTotalFob(Double totalFob) {
		this.totalFob = totalFob;
	}

	public Double getTotalCif() {
		return totalCif;
	}

	public void setTotalCif(Double totalCif) {
		this.totalCif = totalCif;
	}
	
	public Caja getCaja() {
		return caja;
	}
	
	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
	public Date getVencimiento() {
		return vencimiento;
	}
	
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public int getOperacion() {
		return operacion;
	}
	
	public void setOperacion(int operacion) {
		this.operacion = operacion;
	}
	
	public Double getTotalPagado() {
		return totalPagado;
	}
	
	public void setTotalPagado(Double totalPagado) {
		this.totalPagado = totalPagado;
	}

	public Integer getCuotaCant() {
		return cuotaCant;
	}

	public void setCuotaCant(Integer cuotaCant) {
		this.cuotaCant = cuotaCant;
	}

	
}