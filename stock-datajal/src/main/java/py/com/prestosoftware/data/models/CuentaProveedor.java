package py.com.prestosoftware.data.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cuenta_proveedores")
public class CuentaProveedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "proveedor_id")
	private Proveedor proveedor;
	
	@Column(name = "proveedor_nombre", length = 60)
	private String proveedorNombre;
	
	@Column(name = "documento", length = 30)
	private String documento;
	
	private String tipo;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date vencimiento;
	
	@Column(name = "debito", length = 15, scale = 3)
	private Double debito;
	
	@Column(name = "credito", length = 15, scale = 3)
	private Double credito;
	
	@Temporal(TemporalType.TIME)
	private Date hora;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "moneda_id")
	private Moneda moneda;
	
	private String obs;
	
	private String situacion;
	
	@Column(name = "usuario_id")
	private Long usuarioId;
	
	@Column(name = "valor_total", length = 15, scale = 3)
	private Double valorTotal;
	
	@Column(name = "valor_pagado", length = 15, scale = 3)
	private Double valorPagado;
	
	@Transient
	private List<CuentaProveedor> cuentas;
	
	public CuentaProveedor() { }

	public CuentaProveedor(@NotNull Proveedor proveedor, String proveedorNombre, String documento, String tipo,
			@NotNull Date fecha, @NotNull Date vencimiento, Double debito, Double credito, Date hora,
			@NotNull Moneda moneda, String obs, String situacion, Long usuarioId, Double valorTotal,
			Double valorPagado) {
		super();
		this.proveedor = proveedor;
		this.proveedorNombre = proveedorNombre;
		this.documento = documento;
		this.tipo = tipo;
		this.fecha = fecha;
		this.vencimiento = vencimiento;
		this.debito = debito;
		this.credito = credito;
		this.hora = hora;
		this.moneda = moneda;
		this.obs = obs;
		this.situacion = situacion;
		this.usuarioId = usuarioId;
		this.valorTotal = valorTotal;
		this.valorPagado = valorPagado;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getDebito() {
		return debito;
	}

	public void setDebito(Double debito) {
		this.debito = debito;
	}

	public Double getCredito() {
		return credito;
	}

	public void setCredito(Double credito) {
		this.credito = credito;
	}

	public Date getHora() {
		return hora;
	}
	
	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}
	
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getValorPagado() {
		return valorPagado;
	}

	public void setValorPagado(Double valorPagado) {
		this.valorPagado = valorPagado;
	}
	
	public List<CuentaProveedor> getCuentas() {
		return cuentas;
	}
	
	public void setCuentas(List<CuentaProveedor> cuentas) {
		this.cuentas = cuentas;
	}

}