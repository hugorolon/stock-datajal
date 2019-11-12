package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_sistemas")
public class ConfigSistema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "formato_numero")
	private String formatoNumero;
	
	@Column(name = "formato_fecha")
	private String formatoFecha;
	
	
	public ConfigSistema() {}
	
	public ConfigSistema(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormatoFecha() {
		return formatoFecha;
	}
	
	public void setFormatoFecha(String formatoFecha) {
		this.formatoFecha = formatoFecha;
	}
	
	public String getFormatoNumero() {
		return formatoNumero;
	}
	
	public void setFormatoNumero(String formatoNumero) {
		this.formatoNumero = formatoNumero;
	}
	
}