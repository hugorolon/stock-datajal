package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "item_cobro_clientes")
public class ItemCobroCliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "icl_numero")
	private Integer iclNumero;
	
	@Column(name = "icl_secuencia_cuenta")
	private Integer iclSecuenciaCuenta;
	
	@Column(name = "icl_monto")
	private Double iclMonto;
		
	public ItemCobroCliente() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getIclNumero() {
		return iclNumero;
	}



	public void setIclNumero(Integer iclNumero) {
		this.iclNumero = iclNumero;
	}



	public Integer getIclSecuenciaCuenta() {
		return iclSecuenciaCuenta;
	}


	public void setIclSecuenciaCuenta(Integer iclSecuenciaCuenta) {
		this.iclSecuenciaCuenta = iclSecuenciaCuenta;
	}


	public Double getIclMonto() {
		return iclMonto;
	}

	public void setIclMonto(Double iclMonto) {
		this.iclMonto = iclMonto;
	}

	
	
}