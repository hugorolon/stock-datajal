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

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "subgrupos")
public class Subgrupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "nombre", length = 60)
	private String nombre;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;
	
	@Column(name = "porcentaje_precio_a", precision = 15, scale = 2)
	private Double porcentajePrecioA;
	
	@Column(name = "porcentaje_precio_b", precision = 15, scale = 2)
	private Double porcentajePrecioB;
	
	@Column(name = "porcentaje_precio_c", precision = 15, scale = 2)
	private Double porcentajePrecioC;
	
	@Column(name = "porcentaje_precio_d", precision = 15, scale = 2)
	private Double porcentajePrecioD;
	
	@Column(name = "porcentaje_precio_e", precision = 15, scale = 2)
	private Double porcentajePrecioE;
	
	@Column(name = "tipo", length = 1)
	private String tipo; //S = servicio / M = mercaderia
	
	@ColumnDefault(value = "1")
	private int activo;
	
	public Subgrupo() {}
	
	public Subgrupo(Long id) {
		this.id = id;
	}
	
	public Subgrupo(String nombre, Grupo grupo, String tipo, int activo) {
		this.nombre = nombre;
		this.grupo = grupo;
		this.tipo = tipo;
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

	public Grupo getGrupo() {
		return grupo;
	}
	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public Double getPorcentajePrecioA() {
		return porcentajePrecioA;
	}

	public void setPorcentajePrecioA(Double porcentajePrecioA) {
		this.porcentajePrecioA = porcentajePrecioA;
	}

	public Double getPorcentajePrecioB() {
		return porcentajePrecioB;
	}

	public void setPorcentajePrecioB(Double porcentajePrecioB) {
		this.porcentajePrecioB = porcentajePrecioB;
	}

	public Double getPorcentajePrecioC() {
		return porcentajePrecioC;
	}

	public void setPorcentajePrecioC(Double porcentajePrecioC) {
		this.porcentajePrecioC = porcentajePrecioC;
	}

	public Double getPorcentajePrecioD() {
		return porcentajePrecioD;
	}

	public void setPorcentajePrecioD(Double porcentajePrecioD) {
		this.porcentajePrecioD = porcentajePrecioD;
	}

	public Double getPorcentajePrecioE() {
		return porcentajePrecioE;
	}

	public void setPorcentajePrecioE(Double porcentajePrecioE) {
		this.porcentajePrecioE = porcentajePrecioE;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

}