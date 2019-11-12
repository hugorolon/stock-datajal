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
@Table(name = "cuenta_clientes")
public class CuentaCliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column(name = "cliente_nombre", length = 60)
	private String clienteNombre;
	
	private String tipo;
	
	@Column(name = "documento", length = 30)
	private String documento;
	
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
	private Double valorAPagar;
	
	@Transient
	private List<CuentaCliente> cuentas;
	
	public CuentaCliente() { }
	
	public CuentaCliente(Cliente cliente, String clienteNombre, String tipo, String documento,
			Date fecha, Double debito, Double credito, Date hora, @NotNull Moneda moneda, String obs,
			String situacion, Long usuarioId, Double valorTotal, Double valorPagado) {
		super();
		this.cliente = cliente;
		this.clienteNombre = clienteNombre;
		this.tipo = tipo;
		this.documento = documento;
		this.fecha = fecha;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
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
	
	public Double getValorPagado() {
		return valorPagado;
	}
	
	public void setValorPagado(Double valorPagado) {
		this.valorPagado = valorPagado;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Double getValorAPagar() {
		return valorAPagar;
	}
	
	public void setValorAPagar(Double valorAPagar) {
		this.valorAPagar = valorAPagar;
	}
	
	public List<CuentaCliente> getCuentas() {
		return cuentas;
	}
	
	public void setCuentas(List<CuentaCliente> cuentas) {
		this.cuentas = cuentas;
	}

	@Override
	public String toString() {
		return "CuentaCorrienteCliente [id=" + id + ", cliente=" + cliente + ", clienteNombre=" + clienteNombre
				+ ", tipo=" + tipo + ", documento=" + documento + ", fecha=" + fecha + ", vencimiento=" + vencimiento
				+ ", debito=" + debito + ", credito=" + credito + ", hora=" + hora + ", moneda=" + moneda + ", obs="
				+ obs + ", situacion=" + situacion + ", usuarioId=" + usuarioId + ", valorTotal=" + valorTotal
				+ ", valorPagado=" + valorPagado + ", valorAPagar=" + valorAPagar + "]";
	}

}