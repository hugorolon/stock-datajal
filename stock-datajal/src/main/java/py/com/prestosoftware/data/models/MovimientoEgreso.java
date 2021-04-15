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
@Table(name = "movimiento_egresos")
public class MovimientoEgreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meg_numero")
	private Integer megNumero;

	@Temporal(TemporalType.DATE)
	@Column(name = "meg_fecha")
	private Date fecha;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "meg_hora")
	private Date hora;
	
	@Column(name = "meg_documento")
	private String megDocumento;
	
	@Column(name = "meg_tipoproceso")
	private Integer megTipoProceso;

	@Column(name = "meg_proceso")
	private Integer megProceso;

	@Column(name = "meg_caja")
	private Integer megCaja;

	@Column(name = "meg_entidad")
	private String megEntidad;
	
	@Column(name = "meg_tipoentidad")
	private String megTipoEntidad;
	
	@Column(name = "meg_situacion")
	private String megSituacion;
	
	
	public MovimientoEgreso() {}

	
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

	public Integer getMegNumero() {
		return megNumero;
	}

	public void setMegNumero(Integer megNumero) {
		this.megNumero = megNumero;
	}

	public String getMegDocumento() {
		return megDocumento;
	}

	public void setMegDocumento(String megDocumento) {
		this.megDocumento = megDocumento;
	}

	public Integer getMegTipoProceso() {
		return megTipoProceso;
	}

	public void setMegTipoProceso(Integer megTipoProceso) {
		this.megTipoProceso = megTipoProceso;
	}

	public Integer getMegProceso() {
		return megProceso;
	}

	public void setMegProceso(Integer megProceso) {
		this.megProceso = megProceso;
	}

	public Integer getMegCaja() {
		return megCaja;
	}

	public void setMegCaja(Integer megCaja) {
		this.megCaja = megCaja;
	}

	public String getMegEntidad() {
		return megEntidad;
	}

	public void setMegEntidad(String megEntidad) {
		this.megEntidad = megEntidad;
	}

	public String getMegTipoEntidad() {
		return megTipoEntidad;
	}

	public void setMegTipoEntidad(String megTipoEntidad) {
		this.megTipoEntidad = megTipoEntidad;
	}

	public String getMegSituacion() {
		return megSituacion;
	}

	public void setMegSituacion(String megSituacion) {
		this.megSituacion = megSituacion;
	}
}