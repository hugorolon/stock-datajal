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
@Table(name = "ventas_temporales")
public class VentaTemporal {
	
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
		
	@Column(name = "cliente_nombre")
	private String clienteNombre;
	
	@Column(name = "cliente_ruc")
	private String clienteRuc;
	
	@Column(name = "cliente_direccion")
	private String clienteDireccion;
	
	@Column(name = "total_general")
	private Double totalGeneral;
	
	@Column(name = "total_pagado")
	private Double totalPagado;
	
	
	@Column(name = "total_item")
	private Double cantItem;
	
	@Column(name = "timbrado")
	private Long timbrado;
	
	private String situacion;
	
	@ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "venta_detalles_temporales", joinColumns = @JoinColumn(name = "venta_id"))
	private List<VentaDetalleTemporal> items = new ArrayList<>();
	
	public VentaTemporal() {}

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

	public Date getHora() {
		return hora;
	}
	
	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}
	
	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public List<VentaDetalleTemporal> getItems() {
		return items;
	}

	public void setItems(List<VentaDetalleTemporal> items) {
		this.items = items;
	}

	public Double getCantItem() {
		return cantItem;
	}

	public void setCantItem(Double cantItem) {
		this.cantItem = cantItem;
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

	public Long getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(Long timbrado) {
		this.timbrado = timbrado;
	}
	
	
	
}