package py.com.prestosoftware.data.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transferencia_productos")
public class TransferenciaProducto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date fecha;
	private String comprobante;
	
	@ManyToOne
	@JoinColumn(name = "deposito_origen")
	private Deposito depositoOrigen;
	
	@ManyToOne
	@JoinColumn(name = "deposito_destino")
	private Deposito depositoDestino;
	
	private String obs;
	private String situacion;
	
	@ElementCollection
    @CollectionTable(name = "transferencia_producto_detalles", joinColumns = @JoinColumn(name = "transferencia_producto_id"))
	private List<TransferenciaProductoDetalle> items = new ArrayList<>();
	
	
	public TransferenciaProducto() {}

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

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public Deposito getDepositoOrigen() {
		return depositoOrigen;
	}

	public void setDepositoOrigen(Deposito depositoOrigen) {
		this.depositoOrigen = depositoOrigen;
	}

	public Deposito getDepositoDestino() {
		return depositoDestino;
	}

	public void setDepositoDestino(Deposito depositoDestino) {
		this.depositoDestino = depositoDestino;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public List<TransferenciaProductoDetalle> getItems() {
		return items;
	}

	public void setItems(List<TransferenciaProductoDetalle> items) {
		this.items = items;
	}

}