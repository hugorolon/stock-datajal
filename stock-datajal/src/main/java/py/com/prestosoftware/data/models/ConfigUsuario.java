package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_usuarios")
public class ConfigUsuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "es_vendedor")
	private int esVendedor;
	
	public ConfigUsuario() {}
	
	public ConfigUsuario(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getEsVendedor() {
		return esVendedor;
	}
	
	public void setEsVendedor(int esVendedor) {
		this.esVendedor = esVendedor;
	}
	
}