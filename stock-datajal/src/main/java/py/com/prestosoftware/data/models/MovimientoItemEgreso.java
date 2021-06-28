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
@Table(name = "item_movimientoegresos")
public class MovimientoItemEgreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "mie_numero")
	private Integer mieNumero;

	@Column(name = "mie_egreso")
	private Integer mieEgreso;
	
	@Column(name = "mie_monto")
	private Double mieMonto;
	
	@Column(name = "mie_descripcion")
	private String mieDescripcion;
	
	public MovimientoItemEgreso() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMieNumero() {
		return mieNumero;
	}

	public void setMieNumero(Integer mieNumero) {
		this.mieNumero = mieNumero;
	}

	public Integer getMieEgreso() {
		return mieEgreso;
	}

	public void setMieEgreso(Integer mieEgreso) {
		this.mieEgreso = mieEgreso;
	}

	public Double getMieMonto() {
		return mieMonto;
	}

	public void setMieMonto(Double mieMonto) {
		this.mieMonto = mieMonto;
	}

	public String getMieDescripcion() {
		return mieDescripcion;
	}

	public void setMieDescripcion(String mieDescripcion) {
		this.mieDescripcion = mieDescripcion;
	}
}