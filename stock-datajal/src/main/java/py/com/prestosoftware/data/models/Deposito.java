package py.com.prestosoftware.data.models;

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
@Table(name = "depositos")
public class Deposito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nombre;
	
	@ColumnDefault(value = "1")
	private int activo;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	public Deposito() {}
	
	public Deposito(Long id) {
		this.id = id;
	}
	
	public Deposito(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public Deposito(String nombre, int activo, Empresa empresa) {
		this.nombre = nombre;
		this.activo = activo;
		this.empresa = empresa;
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

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
	
}
