package py.com.prestosoftware.data.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "transformacion_productos")
public class TransformacionProducto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date fecha;
	private Integer cantidad;
	private Integer situacion;
	private String usuario;
	private String obs;	
	
	@ManyToOne
	@JoinColumn(name = "producto_origen")
	private Producto productoOrigen;
	
	@ManyToOne
	@JoinColumn(name = "deposito_origen")
	private Deposito depositoOrigen;
	
	@ManyToOne
	@JoinColumn(name = "deposito_destino")
	private Deposito depositoDestino;
	
	
	@ElementCollection(fetch = FetchType.EAGER)
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

	public Producto getProductoOrigen() {
		return productoOrigen;
	}

	public void setProductoOrigen(Producto productoOrigen) {
		this.productoOrigen = productoOrigen;
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

	public Integer getSituacion() {
		return situacion;
	}

	public void setSituacion(Integer situacion) {
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