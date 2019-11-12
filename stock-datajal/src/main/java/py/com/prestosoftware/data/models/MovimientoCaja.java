package py.com.prestosoftware.data.models;

import java.util.Date;
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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "movimiento_cajas")
public class MovimientoCaja {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date hora;
	
	@Column(name = "usuario_id")
	private Long usuario;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "caja_id")
	private Caja caja;
	
	@Column(name = "plan_cuenta_id")
	private int planCuentaId;
	
	@ManyToOne
	@JoinColumn(name = "moneda_id")
	private Moneda moneda;

	@Column(name = "cotizacion")
	private Double cotizacion;
	
	@Column(name = "tipo_operacion")
	private String tipoOperacion;
	
	@Column(name = "nota_nro")
	private String notaNro;
	
	@Column(name = "id_ref")
	private Long idReferencia;
	
	@Column(name = "nota_referencia")
	private String notaReferencia;
	
	@Column(name = "nota_valor")
	private Double notaValor;
	
	@Column(name = "valor_consolidado")
	private Double consolidado;
	
	private String obs;
	
	private String situacion;
	
	@Column(name = "valor_m01")
	private Double valorM01;
	
	@Column(name = "valor_vuelto01")
	private Double valorVuelto01;
	
	@Column(name = "valor_m02")
	private Double valorM02;
	
	@Column(name = "valor_vuelto02")
	private Double valorVuelto02;
	
	@Column(name = "valor_m03")
	private Double valorM03;
	
	@Column(name = "valor_vuelto03")
	private Double valorVuelto03;
	
	@Column(name = "valor_m04")
	private Double valorM04;
	
	@Column(name = "valor_vuelto04")
	private Double valorVuelto04;
	
	@Column(name = "valor_m05")
	private Double valorM05;
	
	@Column(name = "valor_vuelto05")
	private Double valorVuelto05;
	
	@Column(name = "usuario_id_modif")
	private int usuarioIdMod;
	
	@Column(name = "usuario_fecha_modif")
	private Date usuarioModFecha;
	
	public MovimientoCaja() {}

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

	public Caja getCaja() {
		return caja;
	}
	
	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public int getPlanCuentaId() {
		return planCuentaId;
	}

	public void setPlanCuentaId(int planCuentaId) {
		this.planCuentaId = planCuentaId;
	}

	public String getNotaNro() {
		return notaNro;
	}

	public void setNotaNro(String notaNro) {
		this.notaNro = notaNro;
	}

	public Double getNotaValor() {
		return notaValor;
	}

	public void setNotaValor(Double notaValor) {
		this.notaValor = notaValor;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	
	public String getNotaReferencia() {
		return notaReferencia;
	}
	
	public void setNotaReferencia(String notaReferencia) {
		this.notaReferencia = notaReferencia;
	}
	
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
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
	
	public Double getConsolidado() {
		return consolidado;
	}
	
	public void setConsolidado(Double consolidado) {
		this.consolidado = consolidado;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public int getUsuarioIdMod() {
		return usuarioIdMod;
	}

	public void setUsuarioIdMod(int usuarioIdMod) {
		this.usuarioIdMod = usuarioIdMod;
	}

	public Date getUsuarioModFecha() {
		return usuarioModFecha;
	}

	public void setUsuarioModFecha(Date usuarioModFecha) {
		this.usuarioModFecha = usuarioModFecha;
	}

	public Double getValorM01() {
		return valorM01;
	}

	public void setValorM01(Double valorM01) {
		this.valorM01 = valorM01;
	}

	public Double getValorM02() {
		return valorM02;
	}

	public void setValorM02(Double valorM02) {
		this.valorM02 = valorM02;
	}

	public Double getValorM03() {
		return valorM03;
	}

	public void setValorM03(Double valorM03) {
		this.valorM03 = valorM03;
	}

	public Double getValorM04() {
		return valorM04;
	}

	public void setValorM04(Double valorM04) {
		this.valorM04 = valorM04;
	}

	public Double getValorM05() {
		return valorM05;
	}

	public void setValorM05(Double valorM05) {
		this.valorM05 = valorM05;
	}
	
	public Date getHora() {
		return hora;
	}
	
	public void setHora(Date hora) {
		this.hora = hora;
	}
	
	public Long getIdReferencia() {
		return idReferencia;
	}
	
	public void setIdReferencia(Long idReferencia) {
		this.idReferencia = idReferencia;
	}

	public Double getValorVuelto01() {
		return valorVuelto01;
	}

	public void setValorVuelto01(Double valorVuelto01) {
		this.valorVuelto01 = valorVuelto01;
	}

	public Double getValorVuelto02() {
		return valorVuelto02;
	}

	public void setValorVuelto02(Double valorVuelto02) {
		this.valorVuelto02 = valorVuelto02;
	}

	public Double getValorVuelto03() {
		return valorVuelto03;
	}

	public void setValorVuelto03(Double valorVuelto03) {
		this.valorVuelto03 = valorVuelto03;
	}

	public Double getValorVuelto04() {
		return valorVuelto04;
	}

	public void setValorVuelto04(Double valorVuelto04) {
		this.valorVuelto04 = valorVuelto04;
	}

	public Double getValorVuelto05() {
		return valorVuelto05;
	}

	public void setValorVuelto05(Double valorVuelto05) {
		this.valorVuelto05 = valorVuelto05;
	}
	
}