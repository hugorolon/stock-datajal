package py.com.prestosoftware.data.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "proveedores")
public class Proveedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tipo;
	private String nombre;
	
	@Column(name = "razon_social")
	private String razonSocial;
	
	@ManyToOne
	@JoinColumn(name = "ciudad_id")
	private Ciudad ciudad;
	
	@ManyToOne
	@JoinColumn(name = "departamento_id")
	private Departamento departmento;
	
	@ManyToOne
	@JoinColumn(name = "pais_id")
	private Pais pais;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	private String ruc;
	private String dvruc;
	private String direccion;
	private String celular;
	private String telefono;
	private String email;
	private String fax;
	private String web;
	private String skype;
	private String contacto;
	private String clase;
	
	private int plazo;
	private int activo;
	
	@Column(name = "credito_saldo")
	private Double creditoSaldo;
	
	@Column(name = "credito_disponible")
	private Double creditoDisponible;
	
	@Column(name = "dia_credito")
	private Integer diaCredito;
	
	@Column(name = "fecha_registro")
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	@Column(name = "total_debito")
	private Double totalDebito;
	
	@Column(name = "total_credito")
	private Double totalCredito;
	
	public Proveedor() {}
	
	public Proveedor(Long id) {
		this.id = id;
	}
	
	public Proveedor(String nombre, String razonSocial, String tipo, String ruc, String dvruc,
			String direccion, String clase, Pais pais, Departamento dep, Ciudad ciudad, 
			Empresa empresa, String celular, String email) {
		this.nombre = nombre;
		this.razonSocial = razonSocial;
		this.tipo = tipo;
		this.ruc = ruc;
		this.dvruc = dvruc;
		this.direccion = direccion;
		this.clase = clase;
		this.pais = pais;
		this.departmento = dep;
		this.ciudad = ciudad;
		this.empresa = empresa;
		this.celular = celular;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getDvruc() {
		return dvruc;
	}

	public void setDvruc(String dvruc) {
		this.dvruc = dvruc;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public Departamento getDepartmento() {
		return departmento;
	}

	public void setDepartmento(Departamento departmento) {
		this.departmento = departmento;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getPlazo() {
		return plazo;
	}

	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public Double getCreditoSaldo() {
		return creditoSaldo;
	}

	public void setCreditoSaldo(Double creditoSaldo) {
		this.creditoSaldo = creditoSaldo;
	}

	public Double getCreditoDisponible() {
		return creditoDisponible;
	}

	public void setCreditoDisponible(Double creditoDisponible) {
		this.creditoDisponible = creditoDisponible;
	}

	public Integer getDiaCredito() {
		return diaCredito;
	}

	public void setDiaCredito(Integer diaCredito) {
		this.diaCredito = diaCredito;
	}

	public Double getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(Double totalDebito) {
		this.totalDebito = totalDebito;
	}

	public Double getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(Double totalCredito) {
		this.totalCredito = totalCredito;
	}

	@Override
	public String toString() {
		return this.razonSocial;
	}
	
}