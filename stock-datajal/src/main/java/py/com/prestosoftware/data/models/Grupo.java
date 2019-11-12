package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "grupos")
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nombre;
	
	private String descripcion;
	
	@Column(name = "porc_incremento_precio_a")
	private Double porcIncrementoPrecioA;
	
	@Column(name = "porc_incremento_precio_b")
	private Double porcIncrementoPrecioB;
	
	@Column(name = "porc_incremento_precio_c")
	private Double porcIncrementoPrecioC;
	
	@Column(name = "porc_incremento_precio_d")
	private Double porcIncrementoPrecioD;
	
	@Column(name = "porc_incremento_precio_e")
	private Double porcIncrementoPrecioE; 
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "tamanho_id")
	private Tamanho tamanho;
	
	private int activo;
	
	public Grupo() {}
	
	public Grupo(Long id) {
		this.id = id;
	}
	
	public Grupo(String nombre, String descripcion, Tamanho tamanho, int activo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tamanho = tamanho;
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public Double getPorcIncrementoPrecioA() {
		return porcIncrementoPrecioA;
	}

	public void setPorcIncrementoPrecioA(Double porcIncrementoPrecioA) {
		this.porcIncrementoPrecioA = porcIncrementoPrecioA;
	}

	public Double getPorcIncrementoPrecioB() {
		return porcIncrementoPrecioB;
	}

	public void setPorcIncrementoPrecioB(Double porcIncrementoPrecioB) {
		this.porcIncrementoPrecioB = porcIncrementoPrecioB;
	}

	public Double getPorcIncrementoPrecioC() {
		return porcIncrementoPrecioC;
	}

	public void setPorcIncrementoPrecioC(Double porcIncrementoPrecioC) {
		this.porcIncrementoPrecioC = porcIncrementoPrecioC;
	}

	public Double getPorcIncrementoPrecioD() {
		return porcIncrementoPrecioD;
	}

	public void setPorcIncrementoPrecioD(Double porcIncrementoPrecioD) {
		this.porcIncrementoPrecioD = porcIncrementoPrecioD;
	}

	public Double getPorcIncrementoPrecioE() {
		return porcIncrementoPrecioE;
	}

	public void setPorcIncrementoPrecioE(Double porcIncrementoPrecioE) {
		this.porcIncrementoPrecioE = porcIncrementoPrecioE;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

}