package py.com.prestosoftware.data.models;

//@Entity
public class ProductoDeposito {
	
	private String deposito;
	
	private Double stock;
	
//	@Temporal(TemporalType.DATE)
//	private Date fecha;
	
//	@ManyToOne
//	@JoinColumn(name = "producto_id")
//	private Producto producto;
//	
//	@ManyToOne
//	@JoinColumn(name = "deposito_id")
//	private Deposito deposito;
//	
//	private Double stock;
//	
//	@Column(name = "entrada_pendiente")
//	private Double entradaPend;
//	
//	@Column(name = "salida_pendiente")
//	private Double salidaPend;
//	
	public ProductoDeposito(String deposito, Double stock) {
		super();
		this.deposito = deposito;
		this.stock = stock;
	}

	public String getDeposito() {
		return deposito;
	}

	public void setDeposito(String deposito) {
		this.deposito = deposito;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "ProductoDeposito [deposito=" + deposito + ", stock=" + stock + "]";
	}

//	public Deposito getDeposito() {
//		return deposito;
//	}
//
//	public void setDeposito(Deposito deposito) {
//		this.deposito = deposito;
//	}
//
//	public Double getStock() {
//		return stock;
//	}
//
//	public void setStock(Double stock) {
//		this.stock = stock;
//	}
//	
//	public Date getFecha() {
//		return fecha;
//	}
//	
//	public void setFecha(Date fecha) {
//		this.fecha = fecha;
//	}
//
//	public Double getEntradaPend() {
//		return entradaPend;
//	}
//
//	public void setEntradaPend(Double entradaPend) {
//		this.entradaPend = entradaPend;
//	}
//
//	public Double getSalidaPend() {
//		return salidaPend;
//	}
//
//	public void setSalidaPend(Double salidaPend) {
//		this.salidaPend = salidaPend;
//	}
	
	

}