package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "egresos")
public class Egreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "egr_egreso")
	private String egrEgreso;

	@Column(name = "egr_descripcion")
	private String egrDescripcion;
	
	@Column(name = "egr_abreviatura")
	private String egrAbreviatura;


	
	
	public Egreso() {}
	
	public Egreso(Long id) {
		this.id = id;
	}
	
	public Egreso(String egrEgreso, String egrDescripcion, String egrAbreviatura) {
		this.egrEgreso = egrEgreso;
		this.egrDescripcion= egrDescripcion;
		this.egrAbreviatura= egrAbreviatura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEgrEgreso() {
		return egrEgreso;
	}

	public void setEgrEgreso(String egrEgreso) {
		this.egrEgreso = egrEgreso;
	}

	public String getEgrDescripcion() {
		return egrDescripcion;
	}

	public void setEgrDescripcion(String egrDescripcion) {
		this.egrDescripcion = egrDescripcion;
	}

	public String getEgrAbreviatura() {
		return egrAbreviatura;
	}

	public void setEgrAbreviatura(String egrAbreviatura) {
		this.egrAbreviatura = egrAbreviatura;
	}

	
}