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
@Table(name = "cuenta_a_recibir")
public class CuentaARecibir {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_numero")
	private Integer carNumero;

	@Temporal(TemporalType.DATE)
	@Column(name = "car_fecha")
	private Date fecha;

	@Temporal(TemporalType.TIME)
	@Column(name = "car_hora")
	private Date hora;
	
	@Column(name = "car_boleta", length = 60)
	private String nroBoleta;
	
	@Column(name = "car_entidad")
	private Long idEntidad;
	
	@Column(name = "car_tipoentidad")
	private Integer tipoEntidad;

	@Column(name = "car_monto", length = 15, scale = 3)
	private Double monto;
	
	@Column(name = "car_proceso")
	private Integer carProceso;
	
	@Column(name = "car_situacion")
	private Integer carSituacion;
	
	public CuentaARecibir() { }
	
	public CuentaARecibir(Integer carNumero,Date fecha, Date hora, String nroBoleta, Long idEntidad, Integer tipoEntidad, Double monto, Integer carProceso,
			Integer carSituacion) {
		super();
		this.carNumero=carNumero;
		this.fecha=fecha;
		this.hora=hora;
		this.nroBoleta=nroBoleta;
		this.idEntidad=idEntidad;
		this.tipoEntidad=tipoEntidad;
		this.monto=monto;
		this.carProceso=carProceso;
		this.carSituacion=carSituacion;
	}

	
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
	
	public Integer getCarNumero() {
		return carNumero;
	}

	public void setCarNumero(Integer carNumero) {
		this.carNumero = carNumero;
	}

	public String getNroBoleta() {
		return nroBoleta;
	}

	public void setNroBoleta(String nroBoleta) {
		this.nroBoleta = nroBoleta;
	}

	public Long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	public Integer getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(Integer tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Integer getCarProceso() {
		return carProceso;
	}

	public void setCarProceso(Integer carProceso) {
		this.carProceso = carProceso;
	}

	public Integer getCarSituacion() {
		return carSituacion;
	}

	public void setCarSituacion(Integer carSituacion) {
		this.carSituacion = carSituacion;
	}

	@Override
	public String toString() {
		return "CuentaCorrienteCliente [id=" + carNumero + "]";
	}

}