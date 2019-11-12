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
@Table(name = "apertura_cierre_cajas")
public class AperturaCierreCaja {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "usuario_id")
	private Long usuario;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "caja_id")
	private Caja caja;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_apertura")
	private Date fechaApertura;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hora_apertura")
	private Date horaApertura;
	
	@Column(name = "monto_apertura")
	private Double montoApertura;
	
	@Column(name = "monto_apertura_m01")
	private Double montoAperturaM1;
	
	@Column(name = "monto_apertura_m02")
	private Double montoAperturaM2;
	
	@Column(name = "monto_apertura_m03")
	private Double montoAperturaM3;
	
	@Column(name = "monto_apertura_m04")
	private Double montoAperturaM4;
	
	@Column(name = "monto_apertura_m05")
	private Double montoAperturaM5;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_cierre")
	private Date fechaCierre;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hora_cierre")
	private Date horaCierre;
	
	@Column(name = "monto_cierre")
	private Double montoCierre;
	
	@Column(name = "monto_cierre_m01")
	private Double montoCierreM1;
	
	@Column(name = "monto_cierre_m02")
	private Double montoCierreM2;
	
	@Column(name = "monto_cierre_m03")
	private Double montoCierreM3;
	
	@Column(name = "monto_cierre_m04")
	private Double montoCierreM4;
	
	@Column(name = "monto_cierre_m05")
	private Double montoCierreM5;
	
	private int activo;
	
	public AperturaCierreCaja() {}
	
	public AperturaCierreCaja(Caja caja, Date fechaApertura, Double montoApertura) {
		this.caja = caja;
		this.fechaApertura = fechaApertura;
		this.montoApertura = montoApertura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Caja getCaja() {
		return caja;
	}
	
	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Double getMontoApertura() {
		return montoApertura;
	}

	public void setMontoApertura(Double montoApertura) {
		this.montoApertura = montoApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Double getMontoCierre() {
		return montoCierre;
	}

	public void setMontoCierre(Double montoCierre) {
		this.montoCierre = montoCierre;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Date getHoraApertura() {
		return horaApertura;
	}

	public void setHoraApertura(Date horaApertura) {
		this.horaApertura = horaApertura;
	}

	public Double getMontoAperturaM1() {
		return montoAperturaM1;
	}

	public void setMontoAperturaM1(Double montoAperturaM1) {
		this.montoAperturaM1 = montoAperturaM1;
	}

	public Double getMontoAperturaM2() {
		return montoAperturaM2;
	}

	public void setMontoAperturaM2(Double montoAperturaM2) {
		this.montoAperturaM2 = montoAperturaM2;
	}

	public Double getMontoAperturaM3() {
		return montoAperturaM3;
	}

	public void setMontoAperturaM3(Double montoAperturaM3) {
		this.montoAperturaM3 = montoAperturaM3;
	}

	public Double getMontoAperturaM4() {
		return montoAperturaM4;
	}

	public void setMontoAperturaM4(Double montoAperturaM4) {
		this.montoAperturaM4 = montoAperturaM4;
	}

	public Double getMontoAperturaM5() {
		return montoAperturaM5;
	}

	public void setMontoAperturaM5(Double montoAperturaM5) {
		this.montoAperturaM5 = montoAperturaM5;
	}

	public Date getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
	}

	public Double getMontoCierreM1() {
		return montoCierreM1;
	}

	public void setMontoCierreM1(Double montoCierreM1) {
		this.montoCierreM1 = montoCierreM1;
	}

	public Double getMontoCierreM2() {
		return montoCierreM2;
	}

	public void setMontoCierreM2(Double montoCierreM2) {
		this.montoCierreM2 = montoCierreM2;
	}

	public Double getMontoCierreM3() {
		return montoCierreM3;
	}

	public void setMontoCierreM3(Double montoCierreM3) {
		this.montoCierreM3 = montoCierreM3;
	}

	public Double getMontoCierreM4() {
		return montoCierreM4;
	}

	public void setMontoCierreM4(Double montoCierreM4) {
		this.montoCierreM4 = montoCierreM4;
	}

	public Double getMontoCierreM5() {
		return montoCierreM5;
	}

	public void setMontoCierreM5(Double montoCierreM5) {
		this.montoCierreM5 = montoCierreM5;
	}

	@Override
	public String toString() {
		return "AperturaCierreCaja [id=" + id + ", usuario=" + usuario + ", caja=" + caja + ", fechaApertura="
				+ fechaApertura + ", horaApertura=" + horaApertura + ", montoApertura=" + montoApertura
				+ ", montoAperturaM1=" + montoAperturaM1 + ", montoAperturaM2=" + montoAperturaM2 + ", montoAperturaM3="
				+ montoAperturaM3 + ", montoAperturaM4=" + montoAperturaM4 + ", montoAperturaM5=" + montoAperturaM5
				+ ", fechaCierre=" + fechaCierre + ", horaCierre=" + horaCierre + ", montoCierre=" + montoCierre
				+ ", montoCierreM1=" + montoCierreM1 + ", montoCierreM2=" + montoCierreM2 + ", montoCierreM3="
				+ montoCierreM3 + ", montoCierreM4=" + montoCierreM4 + ", montoCierreM5=" + montoCierreM5 + ", activo="
				+ activo + "]";
	}
	
}