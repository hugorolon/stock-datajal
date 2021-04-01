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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "clientes_pais")
public class ClientePais {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String tipo;
	
	@NotNull
	@Column(name = "nombre", length = 125)
	private String nombre;
	
	@Column(name = "razon_social", length = 125)
	private String razonSocial;
	
	private String ciruc;
	private String dvruc;
	private String direccion;
	private String email;
	private String telefono;
	private String celular;
	private String fax;
	private String skype;
	private String web;
	private String contacto;
	private String obs;
	private String clase;
	
	@Column(name = "celular_2")
	private String celular2;
	
	@Column(name = "tiene_credito")
	private int tieneCredito;

	@Column(name = "limite_credito")
	private Double limiteCredito;
	
	@Column(name = "dia_credito")
	private int diaCredito;
	
	@Column(name = "credito_saldo")
	private Double creditoSaldo;
	
	@Column(name = "credito_disponible")
	private Double creditoDisponible;
	
	private int plazo;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "ciudad_id")
	private Ciudad ciudad;
	
	@ManyToOne
    @JoinColumn(name = "departamento_id")
	private Departamento departamento;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "pais_id")
	private Pais pais;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "lista_precio_id")
	private ListaPrecio listaPrecio;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	private int activo;
	
	@Column(name = "fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault(value = "now()")
	private Date fechaRegistro;
	
	@Column(name = "total_debito")
	private Double totalDebito;
	
	@Column(name = "total_credito")
	private Double totalCredito;
	
	public ClientePais() {}
	
	public ClientePais(Long id) {
		this.id = id;
	}
	
	public ClientePais(String nombre, String razonSocial, String ruc, String dvruc, String direccion, Pais pais, 
			Departamento dep, Ciudad ciudad, Empresa empresa, Date fecha, String tipo, int activo, ListaPrecio lista) {
		this.nombre = nombre;
		this.razonSocial = razonSocial;
		this.ciruc = ruc;
		this.dvruc = dvruc;
		this.direccion = direccion;
		this.pais = pais;
		this.departamento = dep;
		this.ciudad = ciudad;
		this.empresa = empresa;
		this.fechaRegistro = fecha;
		this.tipo = tipo;
		this.activo = activo;
		this.listaPrecio = lista;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public int getPlazo() {
		return plazo;
	}

	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCiruc() {
		return ciruc;
	}

	public void setCiruc(String ciruc) {
		this.ciruc = ciruc;
	}

	public String getDvruc() {
		return dvruc;
	}

	public void setDvruc(String dvruc) {
		this.dvruc = dvruc;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public ListaPrecio getListaPrecio() {
		return listaPrecio;
	}

	public void setListaPrecio(ListaPrecio listaPrecio) {
		this.listaPrecio = listaPrecio;
	}

	public int getTieneCredito() {
		return tieneCredito;
	}

	public void setTieneCredito(int tieneCredito) {
		this.tieneCredito = tieneCredito;
	}

	public Double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public int getDiaCredito() {
		return diaCredito;
	}

	public void setDiaCredito(int diaCredito) {
		this.diaCredito = diaCredito;
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

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	
	public String getCelular2() {
		return celular2;
	}

	public void setCelular2(String celular2) {
		this.celular2 = celular2;
	}
	
}