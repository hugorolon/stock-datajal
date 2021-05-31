package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ingresos")
public class Ingreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ing_ingreso")
	private String ingIngreso;

	@Column(name = "ing_descripcion")
	private String ingDescripcion;
	
	@Column(name = "ing_abreviatura")
	private String ingAbreviatura;


	
	
	public Ingreso() {}
	
	public Ingreso(Long id) {
		this.id = id;
	}
	
	public Ingreso(String ingIngreso, String ingDescripcion, String ingAbreviatura) {
		this.ingIngreso = ingIngreso;
		this.ingDescripcion= ingDescripcion;
		this.ingAbreviatura= ingAbreviatura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIngIngreso() {
		return ingIngreso;
	}

	public void setIngIngreso(String ingIngreso) {
		this.ingIngreso = ingIngreso;
	}

	public String getIngDescripcion() {
		return ingDescripcion;
	}

	public void setIngDescripcion(String ingDescripcion) {
		this.ingDescripcion = ingDescripcion;
	}

	public String getIngAbreviatura() {
		return ingAbreviatura;
	}

	public void setIngAbreviatura(String ingAbreviatura) {
		this.ingAbreviatura = ingAbreviatura;
	}


	
}