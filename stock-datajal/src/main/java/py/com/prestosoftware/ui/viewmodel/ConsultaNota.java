package py.com.prestosoftware.ui.viewmodel;

import java.util.Date;

public class ConsultaNota {
	
	private String operacion;
	private Date fecha;
	private String nota;
	private Long notaNro;
	private Long notaRefCod; //cli cod / prov cod
	private String notaRef; //cli nom / prov nom
	private Double precioProducto; 
	private Double cantidad;
	private Long usuarioId;
	private Long depositoId;	
	
	public String getOperacion() {
		return operacion;
	}
	
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Long getNotaNro() {
		return notaNro;
	}

	public void setNotaNro(Long notaNro) {
		this.notaNro = notaNro;
	}

	public String getNotaRef() {
		return notaRef;
	}

	public void setNotaRef(String notaRef) {
		this.notaRef = notaRef;
	}

	public Long getNotaRefCod() {
		return notaRefCod;
	}

	public void setNotaRefCod(Long notaRefCod) {
		this.notaRefCod = notaRefCod;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(Double precioProducto) {
		this.precioProducto = precioProducto;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Long getDepositoId() {
		return depositoId;
	}

	public void setDepositoId(Long depositoId) {
		this.depositoId = depositoId;
	}

}
