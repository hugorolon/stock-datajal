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
@Table(name = "item_cuenta_a_recibir")
public class ItemCuentaARecibir {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ica_secuencia")
	private Integer icaSecuencia;
	
	@Column(name = "ica_cuenta")
	private Integer icaCuenta;

	@Column(name = "ica_dias")
	private Integer icaDias;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ica_vencimiento")
	private Date icaVencimiento;
	
	@Column(name = "ica_monto")
	private Double icaMonto;
	
	@Column(name = "ica_iva")
	private Double icaIva;

	@Column(name = "ica_documento")
	private String icaDocumento;

	@Column(name = "ica_situacion")
	private Integer icaSituacion;

	public ItemCuentaARecibir() { }
	
	

	public Integer getIcaCuenta() {
		return icaCuenta;
	}


	public void setIcaCuenta(Integer icaCuenta) {
		this.icaCuenta = icaCuenta;
	}


	public Integer getIcaSecuencia() {
		return icaSecuencia;
	}


	public void setIcaSecuencia(Integer icaSecuencia) {
		this.icaSecuencia = icaSecuencia;
	}


	public Integer getIcaDias() {
		return icaDias;
	}


	public void setIcaDias(Integer icaDias) {
		this.icaDias = icaDias;
	}


	public Date getIcaVencimiento() {
		return icaVencimiento;
	}


	public void setIcaVencimiento(Date icaVencimiento) {
		this.icaVencimiento = icaVencimiento;
	}


	public Double getIcaMonto() {
		return icaMonto;
	}


	public void setIcaMonto(Double icaMonto) {
		this.icaMonto = icaMonto;
	}


	public Double getIcaIva() {
		return icaIva;
	}


	public void setIcaIva(Double icaIva) {
		this.icaIva = icaIva;
	}


	public String getIcaDocumento() {
		return icaDocumento;
	}


	public void setIcaDocumento(String icaDocumento) {
		this.icaDocumento = icaDocumento;
	}


	public Integer getIcaSituacion() {
		return icaSituacion;
	}


	public void setIcaSituacion(Integer icaSituacion) {
		this.icaSituacion = icaSituacion;
	}

}