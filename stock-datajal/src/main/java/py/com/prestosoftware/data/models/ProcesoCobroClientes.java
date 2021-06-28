package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "proceso_cobro_clientes")
public class ProcesoCobroClientes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "pcc_cobro")
	private Integer pccCobro;

	@Column(name = "pcc_proceso")
	private Integer pccProceso;
	
	@Column(name = "pcc_ingreso")
	private Integer pccIngreso;
	
	@Column(name = "pcc_tipoproceso")
	private Integer pccTipoproceso;
	
	@Column(name = "pcc_flag")
	private Integer pccFlag;
	
	public ProcesoCobroClientes() {}

	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getPccCobro() {
		return pccCobro;
	}

	public void setPccCobro(Integer pccCobro) {
		this.pccCobro = pccCobro;
	}

	public Integer getPccIngreso() {
		return pccIngreso;
	}

	public void setPccIngreso(Integer pccIngreso) {
		this.pccIngreso = pccIngreso;
	}

	public Integer getPccTipoproceso() {
		return pccTipoproceso;
	}

	public void setPccTipoproceso(Integer pccTipoproceso) {
		this.pccTipoproceso = pccTipoproceso;
	}

	public Integer getPccProceso() {
		return pccProceso;
	}

	public void setPccProceso(Integer pccProceso) {
		this.pccProceso = pccProceso;
	}

	public Integer getPccFlag() {
		return pccFlag;
	}

	public void setPccFlag(Integer pccFlag) {
		this.pccFlag = pccFlag;
	}

}