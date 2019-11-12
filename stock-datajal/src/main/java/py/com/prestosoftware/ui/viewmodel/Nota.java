package py.com.prestosoftware.ui.viewmodel;

import java.util.Date;

public class Nota {
	
	private String operacion;
	private String nota;
	private Long notaNro;
	private Long notaRefCod;
	private String notaRef;
	private Double notaValor;
	private Long usuarioId;
	private String usuario;
	private Long empresId;
	private String empresa;
	private Date fecha;
	
	public Nota(String nota, Long notaNro, String notaRef, Double notaValor) {
		this.nota = nota;
		this.notaNro = notaNro;
		this.notaRef = notaRef;
		this.notaValor = notaValor;
	}
	
	public Nota(String operacion, String nota, Long notaNro, Long notaRefCod, String notaRef, 
			Double notaValor, Long usuarioId, String usuario, String empresa, Date fecha) {
		this.operacion = operacion;
		this.nota = nota;
		this.notaNro = notaNro;
		this.notaRefCod = notaRefCod;
		this.notaRef = notaRef;
		this.notaValor = notaValor;
		this.usuarioId = usuarioId;
		this.usuario = usuario;
		this.empresa = empresa;
		this.fecha = fecha;
	}
	
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

	public Double getNotaValor() {
		return notaValor;
	}

	public void setNotaValor(Double notaValor) {
		this.notaValor = notaValor;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	public Long getEmpresId() {
		return empresId;
	}

	public void setEmpresId(Long empresId) {
		this.empresId = empresId;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Nota [operacion=" + operacion + ", nota=" + nota + ", notaNro=" + notaNro + ", notaRefCod=" + notaRefCod
				+ ", notaRef=" + notaRef + ", notaValor=" + notaValor + ", usuarioId=" + usuarioId + ", usuario="
				+ usuario + ", empresId=" + empresId + ", empresa=" + empresa + ", fecha=" + fecha + "]";
	}

}
