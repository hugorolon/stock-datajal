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

@Entity
@Table(name = "ventas")
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int operacion;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hora")
	private Date hora;
	
	private String comprobante;
	private int condicion;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@Column(name = "cliente_nombre")
	private String clienteNombre;
	
	@Column(name = "cliente_ruc")
	private String clienteRuc;
	
	@Column(name = "cliente_direccion")
	private String clienteDireccion;
	
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Usuario vendedor;
	
	@ManyToOne
	@JoinColumn(name = "caja_id")
	private Caja caja;
	
	@ManyToOne
	@JoinColumn(name = "deposito_id")
	private Deposito deposito;
	
	@Column(name = "total_exenta")
	private Double totalExentas;
	
	@Column(name = "total_gravada5")
	private Double totalGravada5;
	
	@Column(name = "total_gravada10")
	private Double totalGravada10;
	
	@Column(name = "total_general")
	private Double totalGeneral;
	
	@Column(name = "total_pagado")
	private Double totalPagado;
	
	@Column(name = "total_iva5")
	private Double totalIva5;
	
	@Column(name = "total_iva10")
	private Double totalIva10;
	
	@Column(name = "total_descuento")
	private Double totalDescuento;
	
	@Column(name = "total_flete")
	private Double totalFlete;
	
	@Column(name = "total_item")
	private Double cantItem;
	
	private Date vencimiento;
	private String obs;
	private String situacion;
	
	@ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "venta_detalles", joinColumns = @JoinColumn(name = "venta_id"))
	private List<VentaDetalle> items = new ArrayList<>();
	
	public Venta() {}

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

	public int getCondicion() {
		return condicion;
	}

	public void setCondicion(int condicion) {
		this.condicion = condicion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Double getTotalExentas() {
		return totalExentas;
	}

	public void setTotalExentas(Double totalExentas) {
		this.totalExentas = totalExentas;
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

	public Date getHora() {
		return hora;
	}
	
	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
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
	
	public String getClienteNombre() {
		return clienteNombre;
	}
	
	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public List<VentaDetalle> getItems() {
		return items;
	}

	public void setItems(List<VentaDetalle> items) {
		this.items = items;
	}

	public Double getTotalDescuento() {
		return totalDescuento;
	}

	public void setTotalDescuento(Double totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	public Double getTotalFlete() {
		return totalFlete;
	}

	public void setTotalFlete(Double totalFlete) {
		this.totalFlete = totalFlete;
	}

	public Double getCantItem() {
		return cantItem;
	}

	public void setCantItem(Double cantItem) {
		this.cantItem = cantItem;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public String getClienteRuc() {
		return clienteRuc;
	}

	public void setClienteRuc(String clienteRuc) {
		this.clienteRuc = clienteRuc;
	}

	public String getClienteDireccion() {
		return clienteDireccion;
	}

	public void setClienteDireccion(String clienteDireccion) {
		this.clienteDireccion = clienteDireccion;
	}
	
	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
	public Caja getCaja() {
		return caja;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
	
}