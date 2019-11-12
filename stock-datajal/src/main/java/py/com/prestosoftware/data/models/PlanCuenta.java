package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plan_cuentas")
public class PlanCuenta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private String tipo;
	
	@Column(name = "operacion_codigo")
	private String operacionCodigo;

	@Column(name = "emite_recibo")
	private String emiteRecibo;
	
	private String recibo;
	
	private int activo;
	
	public PlanCuenta() {}
	
	public PlanCuenta(Long id) {
		this.id = id;
	}
	
	public PlanCuenta(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public PlanCuenta(String nombre, String operacionCodigo, String tipo, int activo) {
		this.nombre = nombre;
		this.operacionCodigo = operacionCodigo;
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

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getCuenta() {
		return operacionCodigo;
	}

	public void setCuenta(String cuenta) {
		this.operacionCodigo = cuenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getOperacionCodigo() {
		return operacionCodigo;
	}

	public void setOperacionCodigo(String operacionCodigo) {
		this.operacionCodigo = operacionCodigo;
	}

	public String getEmiteRecibo() {
		return emiteRecibo;
	}

	public void setEmiteRecibo(String emiteRecibo) {
		this.emiteRecibo = emiteRecibo;
	}

	public String getRecibo() {
		return recibo;
	}

	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

}