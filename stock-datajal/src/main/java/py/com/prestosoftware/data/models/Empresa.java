package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "empresas")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "nombre", nullable = false, length = 60)
	private String nombre;
	
	private String representante;
	private String ruc;
	private String dvruc;
	
	private String direccion;
	private String telefono;
	private String celular;
	
	@ColumnDefault(value = "1")
	private int activo;
	
	public Empresa() {}
	
	public Empresa(Long id) {
		this.id = id;
	}
	
	public Empresa(String nombre, String telefono, String celular, int activo) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.celular = celular;
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

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getDvruc() {
		return dvruc;
	}

	public void setDvruc(String dvruc) {
		this.dvruc = dvruc;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	@Override
	public String toString() {
		return  nombre;
	}
	
}