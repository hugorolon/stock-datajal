package py.com.prestosoftware.data.models;

//import javax.persistence.Embeddable;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;

//@Embeddable
public class ProductoPrecio {
	
//	@ManyToOne
//	@JoinColumn(name = "nivel_precio_id")
//	private ListaPrecio nivelPrecioId;
	
	private String listaPrecio;
	private Double valor;
	
	public ProductoPrecio(String lista, Double precio) {
		this.listaPrecio = lista;
		this.valor = precio;
	}

//	public ListaPrecio getNivelPrecioId() {
//		return nivelPrecioId;
//	}
//	
//	public void setNivelPrecioId(ListaPrecio nivelPrecioId) {
//		this.nivelPrecioId = nivelPrecioId;
//	}
	
	public String getListaPrecio() {
		return listaPrecio;
	}
	
	public void setListaPrecio(String listaPrecio) {
		this.listaPrecio = listaPrecio;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return this.valor + "";
	}

}