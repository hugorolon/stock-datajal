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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cobro_clientes")
public class CobroCliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ccl_numero")
	private Integer cclNumero;
	
	@Column(name = "ccl_documento")
	private String cclDocumento;

	@Column(name = "ccl_entidad")
	private Integer cclEntidad;
	
	@Column(name = "ccl_tipoentidad")
	private Integer cclTipoEntidad;
	
	@Column(name = "ccl_cuota")
	private Integer cclCuota;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ccl_fecha")
	private Date fecha;

	@Temporal(TemporalType.TIME)
	@Column(name = "ccl_hora")
	private Date hora;
	
	@Column(name = "ccl_situacion")
	private Integer cclSituacion;

	@Column(name = "ccl_valor", length = 15, scale = 3)
	private Double cclValor;
	
	@Column(name = "ccl_descuento", length = 15, scale = 3)
	private Double cclDescuento;

	@Column(name = "ccl_recargo", length = 15, scale = 3)
	private Double cclRecargo;

	@Column(name = "ccl_monto", length = 15, scale = 3)
	private Double cclMonto;

	@Column(name = "ccl_cobrado", length = 15, scale = 3)
	private Double cclCobrado;

	@Column(name = "ccl_forma_pago")
	private Integer cclFormaPago;

	@Column(name = "ccl_moneda")
	private Integer cclMoneda;
	
	public CobroCliente() { }
	
	
	public Integer getCclNumero() {
		return cclNumero;
	}


	public void setCclNumero(Integer cclNumero) {
		this.cclNumero = cclNumero;
	}


	public String getCclDocumento() {
		return cclDocumento;
	}


	public void setCclDocumento(String cclDocumento) {
		this.cclDocumento = cclDocumento;
	}


	public Integer getCclEntidad() {
		return cclEntidad;
	}


	public void setCclEntidad(Integer cclEntidad) {
		this.cclEntidad = cclEntidad;
	}


	public Integer getCclTipoEntidad() {
		return cclTipoEntidad;
	}


	public void setCclTipoEntidad(Integer cclTipoEntidad) {
		this.cclTipoEntidad = cclTipoEntidad;
	}


	public Integer getCclCuota() {
		return cclCuota;
	}


	public void setCclCuota(Integer cclCuota) {
		this.cclCuota = cclCuota;
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


	public Integer getCclSituacion() {
		return cclSituacion;
	}


	public void setCclSituacion(Integer cclSituacion) {
		this.cclSituacion = cclSituacion;
	}


	public Double getCclValor() {
		return cclValor;
	}


	public void setCclValor(Double cclValor) {
		this.cclValor = cclValor;
	}


	public Double getCclDescuento() {
		return cclDescuento;
	}


	public void setCclDescuento(Double cclDescuento) {
		this.cclDescuento = cclDescuento;
	}


	public Double getCclRecargo() {
		return cclRecargo;
	}


	public void setCclRecargo(Double cclRecargo) {
		this.cclRecargo = cclRecargo;
	}


	public Double getCclMonto() {
		return cclMonto;
	}


	public void setCclMonto(Double cclMonto) {
		this.cclMonto = cclMonto;
	}


	public Double getCclCobrado() {
		return cclCobrado;
	}


	public void setCclCobrado(Double cclCobrado) {
		this.cclCobrado = cclCobrado;
	}


	public Integer getCclFormaPago() {
		return cclFormaPago;
	}


	public void setCclFormaPago(Integer cclFormaPago) {
		this.cclFormaPago = cclFormaPago;
	}


	public Integer getCclMoneda() {
		return cclMoneda;
	}


	public void setCclMoneda(Integer cclMoneda) {
		this.cclMoneda = cclMoneda;
	}

	@Override
	public String toString() {
		return "CuentaCorrienteCliente [id=" + cclNumero+ "]";
	}

}