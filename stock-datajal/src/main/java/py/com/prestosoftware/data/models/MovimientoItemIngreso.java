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
@Table(name = "item_movimientoingresos")
public class MovimientoItemIngreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "mii_numero")
	private Integer miiNumero;

	@Column(name = "mii_ingreso")
	private Integer miiIngreso;
	
	@Column(name = "mii_monto")
	private Double miiMonto;
	
	@Column(name = "mii_descripcion")
	private Double miiDescripcion;
	
	public MovimientoItemIngreso() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Integer getMiiNumero() {
		return miiNumero;
	}

	public void setMiiNumero(Integer miiNumero) {
		this.miiNumero = miiNumero;
	}

	public Integer getMiiIngreso() {
		return miiIngreso;
	}

	public void setMiiIngreso(Integer miiIngreso) {
		this.miiIngreso = miiIngreso;
	}

	public Double getMiiMonto() {
		return miiMonto;
	}

	public void setMiiMonto(Double miiMonto) {
		this.miiMonto = miiMonto;
	}

	public Double getMiiDescripcion() {
		return miiDescripcion;
	}

	public void setMiiDescripcion(Double miiDescripcion) {
		this.miiDescripcion = miiDescripcion;
	}
	
}