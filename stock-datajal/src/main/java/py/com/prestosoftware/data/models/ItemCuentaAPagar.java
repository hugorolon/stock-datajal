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
@Table(name = "item_cuenta_a_pagar")
public class ItemCuentaAPagar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "icp_secuencia")
	private Integer icpSecuencia;
	
	@Column(name = "icp_cuenta")
	private Integer icpCuenta;

	@Column(name = "icp_dias")
	private Integer icpDias;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "icp_vencimiento")
	private Date icpVencimiento;
	
	@Column(name = "icp_monto")
	private Double icpMonto;
	
	@Column(name = "icp_documento")
	private String icpDocumento;

	@Column(name = "icp_situacion")
	private Integer icpSituacion;


	public ItemCuentaAPagar() { }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getIcpSecuencia() {
		return icpSecuencia;
	}


	public void setIcpSecuencia(Integer icpSecuencia) {
		this.icpSecuencia = icpSecuencia;
	}


	public Integer getIcpCuenta() {
		return icpCuenta;
	}


	public void setIcpCuenta(Integer icpCuenta) {
		this.icpCuenta = icpCuenta;
	}


	public Integer getIcpDias() {
		return icpDias;
	}


	public void setIcpDias(Integer icpDias) {
		this.icpDias = icpDias;
	}


	public Date getIcpVencimiento() {
		return icpVencimiento;
	}


	public void setIcpVencimiento(Date icpVencimiento) {
		this.icpVencimiento = icpVencimiento;
	}


	public Double getIcpMonto() {
		return icpMonto;
	}


	public void setIcpMonto(Double icpMonto) {
		this.icpMonto = icpMonto;
	}


	public String getIcpDocumento() {
		return icpDocumento;
	}


	public void setIcpDocumento(String icpDocumento) {
		this.icpDocumento = icpDocumento;
	}


	public Integer getIcpSituacion() {
		return icpSituacion;
	}


	public void setIcpSituacion(Integer icpSituacion) {
		this.icpSituacion = icpSituacion;
	}
	
	

	
}