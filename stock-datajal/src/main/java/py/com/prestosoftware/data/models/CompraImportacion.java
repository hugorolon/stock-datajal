package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompraImportacion {
	
	@Column(name = "proveedor_id")
	private Long proveedorId;
	private String proveedor;
	private String descripcion;
	private Double valor;
	
	public CompraImportacion() {}

	public Long getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}