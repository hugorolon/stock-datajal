package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "proceso_cobro_ventas")
public class ProcesoCobroVentas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "pve_venta")
	private Integer pveVenta;

	@Column(name = "pve_ingresoegreso")
	private Integer pveIngresoegreso;
	
	@Column(name = "pve_tipoproceso")
	private Integer pveTipoproceso;
	
	@Column(name = "pve_proceso")
	private Integer pveProceso;
	
	@Column(name = "pve_flag")
	private Integer pveFlag;
	
	public ProcesoCobroVentas() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPveVenta() {
		return pveVenta;
	}

	public void setPveVenta(Integer pveVenta) {
		this.pveVenta = pveVenta;
	}

	public Integer getPveIngresoegreso() {
		return pveIngresoegreso;
	}

	public void setPveIngresoegreso(Integer pveIngresoegreso) {
		this.pveIngresoegreso = pveIngresoegreso;
	}

	public Integer getPveTipoproceso() {
		return pveTipoproceso;
	}

	public void setPveTipoproceso(Integer pveTipoproceso) {
		this.pveTipoproceso = pveTipoproceso;
	}

	public Integer getPveProceso() {
		return pveProceso;
	}

	public void setPveProceso(Integer pveProceso) {
		this.pveProceso = pveProceso;
	}

	public Integer getPveFlag() {
		return pveFlag;
	}

	public void setPveFlag(Integer pveFlag) {
		this.pveFlag = pveFlag;
	}

}