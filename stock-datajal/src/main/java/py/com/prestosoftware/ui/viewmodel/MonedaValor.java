package py.com.prestosoftware.ui.viewmodel;

public class MonedaValor {
	
	private Long monedaId;
	private String sigla;
	private String operacion;
	private Double valorTotal;
	private Double valorRecibido;
	private Double valorVuelto;
	
	public MonedaValor(Long monedaId, String sigla, String operacion) {
		super();
		this.monedaId = monedaId;
		this.sigla = sigla;
		this.operacion = operacion;
	}

	public MonedaValor(String sigla, Double valorTotal, Double valorRecibido, Double valorVuelto) {
		super();
		this.sigla = sigla;
		this.valorTotal = valorTotal;
		this.valorRecibido = valorRecibido;
		this.valorVuelto = valorVuelto;
	}
	
	public Long getMonedaId() {
		return monedaId;
	}
	
	public void setMonedaId(Long monedaId) {
		this.monedaId = monedaId;
	}

	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public String getOperacion() {
		return operacion;
	}
	
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
	public Double getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Double getValorRecibido() {
		return valorRecibido;
	}
	
	public void setValorRecibido(Double valorRecibido) {
		this.valorRecibido = valorRecibido;
	}
	
	public Double getValorVuelto() {
		return valorVuelto;
	}
	
	public void setValorVuelto(Double valorVuelto) {
		this.valorVuelto = valorVuelto;
	}

}