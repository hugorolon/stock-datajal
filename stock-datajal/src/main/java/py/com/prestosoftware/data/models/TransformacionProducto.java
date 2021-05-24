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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transformacion_productos")
public class TransformacionProducto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date fecha;
	private Integer cantidad;
	private String situacion;
	private String usuario;
	private String obs;	
	
	@ManyToOne
	@JoinColumn(name = "codigo_producto_origen")
	private Producto codigoProductoOrigen;
	
	@ManyToOne
	@JoinColumn(name = "deposito_origen")
	private Deposito depositoOrigen;
	
	@ManyToOne
	@JoinColumn(name = "deposito_destino")
	private Deposito depositoDestino;
	
	
	@ElementCollection
    @CollectionTable(name = "transformacion_producto_detalles", joinColumns = @JoinColumn(name = "transformacion_producto_id"))
	private List<TransformacionProductoDetalle> items = new ArrayList<>();
	
	
	public TransformacionProducto() {}

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
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Producto getCodigoProductoOrigen() {
		return codigoProductoOrigen;
	}

	public void setCodigoProductoOrigen(Producto codigoProductoOrigen) {
		this.codigoProductoOrigen = codigoProductoOrigen;
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

	public List<TransformacionProductoDetalle> getItems() {
		return items;
	}

	public void setItems(List<TransformacionProductoDetalle> items) {
		this.items = items;
	}

}