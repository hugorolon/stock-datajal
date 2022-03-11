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
	
	@Column(name = "es_servicio")
	private int esServicio;
	
	@Column(name = "es_fraccionado")
	private int esFraccionado;

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
	
	@Column(name = "regimen")
	private String regimen;
	
	@Column(name = "precio_costo")
	private Double precioCosto;
	
	@Column(name = "costo_fob")
	private Double costoFob;
	
	@Column(name = "costo_cif")
	private Double costoCif;
	
	@Column(name = "precio_costo_promedio")
	private Double precioCostoPromedio;
	
	@Column(name = "stock_actual", precision = 15, scale = 2)
	private Double stock;
	
	@Column(name = "es_promo")
	private String esPromo;
	
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
	@JoinColumn(name = "ncm_id")
	private Ncm ncm;
	
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
	
	@ColumnDefault(value = "0")
	@Column(name = "precio_venta_d")
	private Double precioVentaD;
	
	@ColumnDefault(value = "0")
	@Column(name = "precio_venta_e")
	private Double precioVentaE;
	
	@Column(name = "dep_01")
	private Double depO1;
	
	@Column(name = "dep_02")
	private Double depO2;
	
	@Column(name = "dep_03")
	private Double depO3;
	
	@Column(name = "dep_04")
	private Double depO4;
	
	@Column(name = "dep_05")
	private Double depO5;
	
	@Column(name = "dep_01_bloq")
	private Double depO1Bloq;
	
	@Column(name = "dep_02_bloq")
	private Double depO2Bloq;
	
	@Column(name = "dep_03_bloq")
	private Double depO3Bloq;
	
	@Column(name = "dep_04_bloq")
	private Double depO4Bloq;
	
	@Column(name = "dep_05_bloq")
	private Double depO5Bloq;
	
	@Column(name = "entrada_pend")
	private Double entPendiente;
	
	@Column(name = "salida_pend")
	private Double salidaPend;
	
	@Column(name = "total_cif")
	private Double totalCif;
	
	@Column(name = "total_fob")
	private Double totalFob;
	
	@ColumnDefault(value = "1")
	private int activo; 
	
	//PROMOCION
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_promo_inicial")
	private Date fechaPromoInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_promo_final")
	private Date fechaPromoFin;
	
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
			Categoria categoria, Ncm ncm, Double precioA, Double precioB, Double precioC, 
			Double precioD, Double precioE) {
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
		this.ncm = ncm;
		this.precioVentaA = precioA;
		this.precioVentaB = precioB;
		this.precioVentaC = precioC;
		this.precioVentaD = precioD;
		this.precioVentaE = precioE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getEsServicio() {
		return esServicio;
	}

	public void setEsServicio(int esServicio) {
		this.esServicio = esServicio;
	}

//	public String getNombre() {
//		return nombre;
//	}
//
//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}

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

	public String getRegimen() {
		return regimen;
	}

	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public String getEsPromo() {
		return esPromo;
	}

	public void setEsPromo(String esPromo) {
		this.esPromo = esPromo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Ncm getNcm() {
		return ncm;
	}

	public void setNcm(Ncm ncm) {
		this.ncm = ncm;
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
	
	public int getEsFraccionado() {
		return esFraccionado;
	}
	
	public void setEsFraccionado(int esFraccionado) {
		this.esFraccionado = esFraccionado;
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

	public Double getDepO3() {
		return depO3;
	}

	public void setDepO3(Double depO3) {
		this.depO3 = depO3;
	}

	public Double getDepO4() {
		return depO4;
	}

	public void setDepO4(Double depO4) {
		this.depO4 = depO4;
	}

	public Double getDepO5() {
		return depO5;
	}

	public void setDepO5(Double depO5) {
		this.depO5 = depO5;
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

	public Double getDepO3Bloq() {
		return depO3Bloq;
	}

	public void setDepO3Bloq(Double depO3Bloq) {
		this.depO3Bloq = depO3Bloq;
	}

	public Double getDepO4Bloq() {
		return depO4Bloq;
	}

	public void setDepO4Bloq(Double depO4Bloq) {
		this.depO4Bloq = depO4Bloq;
	}

	public Double getDepO5Bloq() {
		return depO5Bloq;
	}

	public void setDepO5Bloq(Double depO5Bloq) {
		this.depO5Bloq = depO5Bloq;
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

	public Double getTotalCif() {
		return totalCif;
	}

	public void setTotalCif(Double totalCif) {
		this.totalCif = totalCif;
	}

	public Double getTotalFob() {
		return totalFob;
	}

	public void setTotalFob(Double totalFob) {
		this.totalFob = totalFob;
	}
	
	public Double getCostoCif() {
		return costoCif;
	}
	
	public void setCostoCif(Double costoCif) {
		this.costoCif = costoCif;
	}
	
	public Double getCostoFob() {
		return costoFob;
	}
	
	public void setCostoFob(Double costoFob) {
		this.costoFob = costoFob;
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

	public Double getPrecioVentaD() {
		return precioVentaD;
	}

	public void setPrecioVentaD(Double precioVentaD) {
		this.precioVentaD = precioVentaD;
	}

	public Double getPrecioVentaE() {
		return precioVentaE;
	}

	public void setPrecioVentaE(Double precioVentaE) {
		this.precioVentaE = precioVentaE;
	}

	public Date getFechaPromoInicio() {
		return fechaPromoInicio;
	}

	public void setFechaPromoInicio(Date fechaPromoInicio) {
		this.fechaPromoInicio = fechaPromoInicio;
	}

	public Date getFechaPromoFin() {
		return fechaPromoFin;
	}

	public void setFechaPromoFin(Date fechaPromoFin) {
		this.fechaPromoFin = fechaPromoFin;
	}

	@Override
	public String toString() {
		return this.descripcion;
	}
	
}