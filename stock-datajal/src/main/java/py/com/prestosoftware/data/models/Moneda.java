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
@Table(name = "monedas")
public class Moneda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nombre;
	private String sigla;
	
	@NotNull
    @Column(name = "es_base")
	private int esBase;
   
    private String mascara;
    
    private String operacion;
    
    @ColumnDefault(value = "1")
	private int activo;
	
	public Moneda() {}
	
	public Moneda(Long id) {
		this.id = id;
	}
	
	public Moneda(String nombre, String sigla, String operacion, int esBase, int activo) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.operacion = operacion;
		this.esBase = esBase;
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public int getEsBase() {
		return esBase;
	}

	public void setEsBase(int esBase) {
		this.esBase = esBase;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
	public String getMascara() {
		return mascara;
	}
	
	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

}