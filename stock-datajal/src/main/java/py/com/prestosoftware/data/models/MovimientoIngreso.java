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
@Table(name = "movimiento_ingresos")
public class MovimientoIngreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "min_numero")
	private Integer minNumero;

	@Temporal(TemporalType.DATE)
	@Column(name = "min_fecha")
	private Date fecha;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "min_hora")
	private Date hora;
	
	@Column(name = "min_documento")
	private String minDocumento;
	
	@Column(name = "min_tipoproceso")
	private Integer minTipoProceso;

	@Column(name = "min_proceso")
	private Integer minProceso;

	@Column(name = "min_caja")
	private Integer minCaja;

	@Column(name = "min_entidad")
	private String minEntidad;
	
	@Column(name = "min_tipoentidad")
	private Integer minTipoEntidad;
	
	@Column(name = "min_situacion")
	private Integer minSituacion;
	
	
	public MovimientoIngreso() {}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
	public Date getHora() {
		return hora;
	}
	
	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Integer getMinNumero() {
		return minNumero;
	}

	public void setMinNumero(Integer minNumero) {
		this.minNumero = minNumero;
	}

	public String getMinDocumento() {
		return minDocumento;
	}

	public void setMinDocumento(String minDocumento) {
		this.minDocumento = minDocumento;
	}

	public Integer getMinTipoProceso() {
		return minTipoProceso;
	}

	public void setMinTipoProceso(Integer minTipoProceso) {
		this.minTipoProceso = minTipoProceso;
	}

	public Integer getMinProceso() {
		return minProceso;
	}

	public void setMinProceso(Integer minProceso) {
		this.minProceso = minProceso;
	}

	public Integer getMinCaja() {
		return minCaja;
	}

	public void setMinCaja(Integer minCaja) {
		this.minCaja = minCaja;
	}

	public String getMinEntidad() {
		return minEntidad;
	}

	public void setMinEntidad(String minEntidad) {
		this.minEntidad = minEntidad;
	}

	public Integer getMinTipoEntidad() {
		return minTipoEntidad;
	}

	public void setMinTipoEntidad(Integer minTipoEntidad) {
		this.minTipoEntidad = minTipoEntidad;
	}

	public Integer getMinSituacion() {
		return minSituacion;
	}

	public void setMinSituacion(Integer minSituacion) {
		this.minSituacion = minSituacion;
	}
	
	
}