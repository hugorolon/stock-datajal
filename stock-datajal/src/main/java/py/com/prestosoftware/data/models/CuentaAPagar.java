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
@Table(name = "cuenta_a_pagar")
public class CuentaAPagar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cap_numero")
	private Integer capNumero;

	@Temporal(TemporalType.DATE)
	@Column(name = "cap_fecha")
	private Date fecha;

	@Temporal(TemporalType.TIME)
	@Column(name = "cap_hora")
	private Date hora;
	
	@Column(name = "cap_boleta", length = 60)
	private String nroBoleta;
	
	@Column(name = "id_proveedor")
	private Long idEntidad;
	
	@Column(name = "cap_tipoentidad")
	private Integer tipoEntidad;

	@Column(name = "cap_monto", length = 15, scale = 3)
	private Double monto;
	
	@Column(name = "cap_proceso")
	private Integer capProceso;
	
	@Column(name = "cap_situacion")
	private Integer capSituacion;
	
	public CuentaAPagar() { }
	
	public CuentaAPagar(Integer capNumero,Date fecha, Date hora, String nroBoleta, Long idEntidad, Integer tipoEntidad, Double monto, Integer capProceso,
			Integer capSituacion) {
		super();
		this.capNumero=capNumero;
		this.fecha=fecha;
		this.hora=hora;
		this.nroBoleta=nroBoleta;
		this.idEntidad=idEntidad;
		this.tipoEntidad=tipoEntidad;
		this.monto=monto;
		this.capProceso=capProceso;
		this.capSituacion=capSituacion;
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
	

	public Integer getCapNumero() {
		return capNumero;
	}

	public void setCapNumero(Integer capNumero) {
		this.capNumero = capNumero;
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

	public Integer getCapProceso() {
		return capProceso;
	}

	public void setCapProceso(Integer capProceso) {
		this.capProceso = capProceso;
	}

	public Integer getCapSituacion() {
		return capSituacion;
	}

	public void setCapSituacion(Integer capSituacion) {
		this.capSituacion = capSituacion;
	}

	@Override
	public String toString() {
		return "CuentaCorrienteCliente [id=" + capNumero + "]";
	}

}