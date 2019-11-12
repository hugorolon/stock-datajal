package py.com.prestosoftware.data.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ajuste_stock")
public class AjusteStock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tipo_ajuste")
	private String tipoAjuste;
	
	@NotNull
	@Column(name = "fecha")
	private Date fecha;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "deposito_id")
	private Deposito deposito;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ElementCollection
	@CollectionTable(name = "ajuste_stock_detalles", joinColumns = @JoinColumn(name = "ajuste_id"))
	private List<AjusteStockDetalle> items = new ArrayList<>();
	
	private String obs;
	
	private String situacion;
	
	public AjusteStock() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSituacion() {
		return situacion;
	}
	
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	
	public List<AjusteStockDetalle> getItems() {
		return items;
	}
	
	public void setItems(List<AjusteStockDetalle> items) {
		this.items = items;
	}
	
	public String getTipoAjuste() {
		return tipoAjuste;
	}
	
	public void setTipoAjuste(String tipoAjuste) {
		this.tipoAjuste = tipoAjuste;
	}

}