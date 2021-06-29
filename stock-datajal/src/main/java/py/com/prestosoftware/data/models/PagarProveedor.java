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
@Table(name = "pago_proveedores")
public class PagarProveedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ppr_numero")
	private Integer pprNumero;
	
	@Column(name = "ppr_documento")
	private String pprDocumento;

	@Column(name = "ppr_entidad")
	private Integer pprEntidad;
	
	@Column(name = "ppr_tipoentidad")
	private Integer pprTipoEntidad;
	
	@Column(name = "ppr_cuota")
	private Integer pprCuota;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ppr_fecha")
	private Date fecha;

	@Temporal(TemporalType.TIME)
	@Column(name = "ppr_hora")
	private Date hora;
	
	@Column(name = "ppr_situacion")
	private Integer pprSituacion;

	@Column(name = "ppr_valor", length = 15, scale = 3)
	private Double pprValor;
	
	@Column(name = "ppr_descuento", length = 15, scale = 3)
	private Double pprDescuento;

	@Column(name = "ppr_recargo", length = 15, scale = 3)
	private Double pprRecargo;

	@Column(name = "ppr_monto", length = 15, scale = 3)
	private Double pprMonto;

	@Column(name = "ccl_cobrado", length = 15, scale = 3)
	private Double cclCobrado;

	@Column(name = "ccl_forma_pago")
	private Integer cclFormaPago;

	@Column(name = "ccl_moneda")
	private Integer cclMoneda;
	
	public PagarProveedor() { }
	
	
	

	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public Integer getPprNumero() {
		return pprNumero;
	}




	public void setPprNumero(Integer pprNumero) {
		this.pprNumero = pprNumero;
	}




	public String getPprDocumento() {
		return pprDocumento;
	}




	public void setPprDocumento(String pprDocumento) {
		this.pprDocumento = pprDocumento;
	}




	public Integer getPprEntidad() {
		return pprEntidad;
	}




	public void setPprEntidad(Integer pprEntidad) {
		this.pprEntidad = pprEntidad;
	}




	public Integer getPprTipoEntidad() {
		return pprTipoEntidad;
	}




	public void setPprTipoEntidad(Integer pprTipoEntidad) {
		this.pprTipoEntidad = pprTipoEntidad;
	}




	public Integer getPprCuota() {
		return pprCuota;
	}




	public void setPprCuota(Integer pprCuota) {
		this.pprCuota = pprCuota;
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




	public Integer getPprSituacion() {
		return pprSituacion;
	}




	public void setPprSituacion(Integer pprSituacion) {
		this.pprSituacion = pprSituacion;
	}




	public Double getPprValor() {
		return pprValor;
	}




	public void setPprValor(Double pprValor) {
		this.pprValor = pprValor;
	}




	public Double getPprDescuento() {
		return pprDescuento;
	}




	public void setPprDescuento(Double pprDescuento) {
		this.pprDescuento = pprDescuento;
	}




	public Double getPprRecargo() {
		return pprRecargo;
	}




	public void setPprRecargo(Double pprRecargo) {
		this.pprRecargo = pprRecargo;
	}




	public Double getPprMonto() {
		return pprMonto;
	}




	public void setPprMonto(Double pprMonto) {
		this.pprMonto = pprMonto;
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
		return "CuentaCorrienteProveedores[id=" + pprNumero+ "]";
	}

}