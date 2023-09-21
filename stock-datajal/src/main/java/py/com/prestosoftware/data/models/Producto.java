package py.com.prestosoftware.data.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "productos",
	indexes = { 
			@Index(name = "producto_index_descripcion", columnList="descripcion", unique = false),
			@Index(name = "producto_index_referencia", columnList="referencia", unique = false), 
			@Index(name = "producto_index_subreferencia", columnList="subreferencia", unique = false) 
	})
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@NotNull
	private String descripcion;
	
	@Column(name = "descripcion_fiscal")
	private String descripcionFiscal;
	
	@NotNull
	private String referencia;
	private String subreferencia;
	private Double peso;
	
	@Column(name = "cantidad_por_caja")
	private Double cantidadPorCaja;
	
	private String seccion;
	
	@Column(name = "modeloaplicacion")
	private String modeloAplicacion;
	
	@Column(name = "precio_costo")
	private Double precioCosto;
		
	@Column(name = "precio_costo_promedio")
	private Double precioCostoPromedio;
	
	@Column(name = "stock_actual", precision = 15, scale = 2)
	private Double stock;
	
	@Column(name = "imagen_url")
	private String imagenUrl;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "subgrupo_id")
	private Subgrupo subgrupo;
		
	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "impuesto_id")
	private Impuesto impuesto;
	
	@ManyToOne
	@JoinColumn(name = "marca_id")
	private Marca marca;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "tamanho_id")
	private Tamanho tamanho;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "umedida_id")
	private UnidadMedida unidadMedida;
	
	//Precios de ventas
	@ColumnDefault(value = "0")
	@Column(name = "precio_venta_a")
	private Double precioVentaA;
	
	@ColumnDefault(value = "0")
	@Column(name = "precio_venta_b")
	private Double precioVentaB;
	
	@ColumnDefault(value = "0")
	@Column(name = "precio_venta_c")
	private Double precioVentaC;
	
	@Column(name = "dep_01")
	private Double depO1;
	
	@Column(name = "dep_02")
	private Double depO2;
	
	@Column(name = "dep_01_bloq")
	private Double depO1Bloq;
	
	@Column(name = "dep_02_bloq")
	private Double depO2Bloq;
	
	@Column(name = "entrada_pend")
	private Double entPendiente;
	
	@Column(name = "salida_pend")
	private Double salidaPend;
	
	
	@ColumnDefault(value = "1")
	private int activo; 
	
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "codigo_sec")
	private String codigoSec;
	
	@Column(name = "codigofram")
	private String codigofram;
	
	@Column(name = "codigoman")
	private String codigoman;
	
	@Column(name = "viscocidad")
	private String viscocidad;
	
	@Column(name = "base")
	private String base;
	
	@Column(name = "origen")
	private String origen;
	
	@Column(name = "envase")
	private String envase;

	
	//AUDITORIA
	@ManyToOne
	@JoinColumn(name = "usuario_id_registro")
	@CreatedBy
	private Usuario userCreate; 
	
	@Column(name = "fecha_registro")
	@CreatedDate
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id_modif")
	@LastModifiedBy
	private Usuario userUpdate; 
	
	@Column(name = "fecha_modif")
	@LastModifiedDate
	private Date updatedDate;
	
	public Producto() {}
	
	public Producto(Long id) {
		this.id = id;
	}
	
	public Producto(String descripcion, String descriFiscal, String referencia, String subreferencia,
			Grupo grupo, Subgrupo subgrupo, Marca marca, Color color, Tamanho tamanho, UnidadMedida uom, Impuesto imp,
			Categoria categoria, Double precioCosto,Double precioCostoPromedio, Double precioA, Double precioB, Double precioC) {
		//this.nombre = nombre; String nombre, 
		this.descripcion = descripcion;
		this.descripcionFiscal = descriFiscal;
		this.referencia = referencia;
		this.subreferencia = subreferencia;
		this.grupo = grupo;
		this.subgrupo = subgrupo;
		this.marca = marca;
		this.color = color;
		this.tamanho = tamanho;
		this.unidadMedida = uom;
		this.impuesto = imp;
		this.categoria = categoria;
		this.precioCosto=precioCosto;
		this.precioCostoPromedio=precioCostoPromedio;
		this.precioVentaA = precioA;
		this.precioVentaB = precioB;
		this.precioVentaC = precioC;
		}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionFiscal() {
		return descripcionFiscal;
	}

	public void setDescripcionFiscal(String descripcionFiscal) {
		this.descripcionFiscal = descripcionFiscal;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getSubreferencia() {
		return subreferencia;
	}

	public void setSubreferencia(String subreferencia) {
		this.subreferencia = subreferencia;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getCantidadPorCaja() {
		return cantidadPorCaja;
	}

	public void setCantidadPorCaja(Double cantidadPorCaja) {
		this.cantidadPorCaja = cantidadPorCaja;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getModeloAplicacion() {
		return modeloAplicacion;
	}

	public void setModeloAplicacion(String modeloAplicacion) {
		this.modeloAplicacion = modeloAplicacion;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Impuesto getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public Subgrupo getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(Subgrupo subgrupo) {
		this.subgrupo = subgrupo;
	}

	public Double getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(Double precioCosto) {
		this.precioCosto = precioCosto;
	}

	public Double getPrecioCostoPromedio() {
		return precioCostoPromedio;
	}

	public void setPrecioCostoPromedio(Double precioCostoPromedio) {
		this.precioCostoPromedio = precioCostoPromedio;
	}
	
	public Usuario getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(Usuario userCreate) {
		this.userCreate = userCreate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Usuario getUserUpdate() {
		return userUpdate;
	}

	public void setUserUpdate(Usuario userUpdate) {
		this.userUpdate = userUpdate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	

	public Double getDepO1() {
		return depO1;
	}

	public void setDepO1(Double depO1) {
		this.depO1 = depO1;
	}

	public Double getDepO2() {
		return depO2;
	}

	public void setDepO2(Double depO2) {
		this.depO2 = depO2;
	}

	public Double getDepO1Bloq() {
		return depO1Bloq;
	}

	public void setDepO1Bloq(Double depO1Bloq) {
		this.depO1Bloq = depO1Bloq;
	}

	public Double getDepO2Bloq() {
		return depO2Bloq;
	}

	public void setDepO2Bloq(Double depO2Bloq) {
		this.depO2Bloq = depO2Bloq;
	}
	
	public Double getEntPendiente() {
		return entPendiente;
	}
	
	public void setEntPendiente(Double entPendiente) {
		this.entPendiente = entPendiente;
	}
	
	public Double getSalidaPend() {
		return salidaPend;
	}
	
	public void setSalidaPend(Double salidaPend) {
		this.salidaPend = salidaPend;
	}

	public Double getPrecioVentaA() {
		return precioVentaA;
	}

	public void setPrecioVentaA(Double precioVentaA) {
		this.precioVentaA = precioVentaA;
	}

	public Double getPrecioVentaB() {
		return precioVentaB;
	}

	public void setPrecioVentaB(Double precioVentaB) {
		this.precioVentaB = precioVentaB;
	}

	public Double getPrecioVentaC() {
		return precioVentaC;
	}

	public void setPrecioVentaC(Double precioVentaC) {
		this.precioVentaC = precioVentaC;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoSec() {
		return codigoSec;
	}

	public void setCodigoSec(String codigoSec) {
		this.codigoSec = codigoSec;
	}

	public String getCodigofram() {
		return codigofram;
	}

	public void setCodigofram(String codigofram) {
		this.codigofram = codigofram;
	}

	public String getCodigoman() {
		return codigoman;
	}

	public void setCodigoman(String codigoman) {
		this.codigoman = codigoman;
	}

	public String getViscocidad() {
		return viscocidad;
	}

	public void setViscocidad(String viscocidad) {
		this.viscocidad = viscocidad;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getEnvase() {
		return envase;
	}

	public void setEnvase(String envase) {
		this.envase = envase;
	}

	@Override
	public String toString() {
		return this.descripcion;
	}
	
}