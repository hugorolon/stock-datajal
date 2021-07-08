package py.com.prestosoftware.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "configuraciones")
public class Configuracion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresaId;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuarioId;
	
	//opciones basica
	@ColumnDefault(value = "0")
	@Column(name = "calculo_precio_Venta")
	private int calculoPrecioVenta;
	
	@ColumnDefault(value = "0")
	@Column(name = "registro_producto_iva")
	private int registroProductoIVA;
	
	@ColumnDefault(value = "0")
	@Column(name = "tipo_comision_venta")
	private int tipoComisionVenta;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_disp_nota_presu")
	private int pideDispNotaPresu;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_disp_nota_trans")
	private int pideDispNotaTrans;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_disp_nota_ajuste_entrada")
	private int pideDispAjusteEntrada;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_disp_nota_ajuste_salida")
	private int pideDispNotaAjusteSalida;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_disp_nota_devol_compra")
	private int pideDispNotaDevolCompra;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_disp_nota_devol_venta")
	private int pideDispNotaDevolVenta;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_disp_nota_ajuste_cuenta")
	private int pideDispNotaAjusteCuenta;
	
	//opciones de venta
	@ColumnDefault(value = "0")
	@Column(name = "pide_vendedor")
	private int pideVendedor;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_deposito")
	private int pideDeposito;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_descuento")
	private int pideDescuento;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_flete")
	private int pideFlete;
	
	@ColumnDefault(value = "0")
	@Column(name = "impresion_moneda_nota")
	private int imprimeMoneda;
	
	@ColumnDefault(value = "0")
	@Column(name = "impresion_vendedor_nota")
	private int imprimeVendedor;
	
	@ColumnDefault(value = "0")
	@Column(name = "imprime_orden_alfabetico")
	private int imprimePorOrdenAlfabetico;
	
	@ColumnDefault(value = "0")
	@Column(name = "permite_venta_referencia")
	private int permiteVentaPorReferencia;
	
	@ColumnDefault(value = "0")
	@Column(name = "permite_item_dupicado")
	private int permiteItemDuplicado;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_dispositivo_venta")
	private int pideDispositivoVenta;
	
	@ColumnDefault(value = "0")
	@Column(name = "bloquea_producto_negativo")
	private int bloqueaProductoNegativo;
	
	@ColumnDefault(value = "0")
	@Column(name = "permiete_precio_por_cliente")
	private int permitePrecioPorCliente;
	
	//opciones de compra
	@ColumnDefault(value = "0")
	@Column(name = "pide_moneda_compra")
	private int pideMoneda;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_deposito_compra")
	private int pideDepositoCompra;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_gasto_item_compra")
	private int pideGastoItemCompra;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_flete_compra_local")
	private int pideFleteCompraLocal;
	
	@Column(name = "imprime_compra_resumida")
	private int imprimeCompraResumida;
	
	@ColumnDefault(value = "0")
	@Column(name = "actualizacion_automatica_compra")
	private int permiteActualizacionAutoCompra;
	
	@ColumnDefault(value = "0")
	@Column(name = "pide_dispositivo_compra")
	private int pideDispCompra;
	
	@ColumnDefault(value = "0")
	@Column(name = "imprime_nota_resumida_compra")
	private int imprimeNotaResCompra;
	
	// Opciones de PDV
	@ColumnDefault(value = "0")
	@Column(name = "caja_id_pdv")
	private int cajaIdPDV;
	
	@ColumnDefault(value = "0")
	@Column(name = "deposito_id_pdv")
	private int depositoIdPDV;
	
	@ColumnDefault(value = "0")
	@Column(name = "usuario_id_pdv")
	private Long usuarioIdPDV;
	
	// opciones en Usuario
	@ColumnDefault(value = "0")
	@Column(name = "venta_directo_contado")
	private int ventaDirectoContado;
	
	@ColumnDefault(value = "0")
	@Column(name = "mensaje_finaliza_venta")
	private int mensajeFinalizaVenta;
	
	@ColumnDefault(value = "0")
	@Column(name = "nota_recibida_f3")
	private int notaRecibidaF3;
	
	@ColumnDefault(value = "0")
	@Column(name = "monitor_caja_f4")
	private int monitorCajaF4;
	
	@ColumnDefault(value = "0")
	@Column(name = "codigo_referencia")
	private int codigoReferencia;
	
	@ColumnDefault(value = "0")
	@Column(name = "altera_precio_venta")
	private int alteraPrecioVenta;
	
	@ColumnDefault(value = "A")
	@Column(name="precio_definido")
	private String precioDefinido;
	
	//opciones de usuario
	@ColumnDefault(value = "0")
	@Column(name = "define_deposito_venta")
	private Long defineDepositoVenta;
	
	@ColumnDefault(value = "0")
	@Column(name = "define_deposito_compra")
	private Long defineDepositoCompra;
	
	@ColumnDefault(value = "0")
	@Column(name = "define_apertura_caja")
	private Long defineAperCajaCodigo;
	
	@ColumnDefault(value = "0")
	@Column(name = "operacion_id")
	private Long OperacionBloqCaja;
	
	@ColumnDefault(value = "0")
	@Column(name = "define_caja_transferencia")
	private Long defineCajaTransferencia;
	
	@ColumnDefault(value = "0")
	@Column(name = "visualiza_deposito_usuario")
	private Long visualizaDepositoUsuario;
	
	@ColumnDefault(value = "0")
	@Column(name = "habilita_desc_usuario")
	private int habilitaDescUsuario;
	
	@ColumnDefault(value = "0")
	@Column(name = "tipo_desc_usuario")
	private int tipoDescUsuario;
	
	@ColumnDefault(value = "0")
	@Column(name = "limite_desc_nota_valor")
	private Double limiteDescNotaValor;
	
	@ColumnDefault(value = "0")
	@Column(name = "autoriza_precio_d")
	private int AutorizaVentaPrecioD;
	
	@ColumnDefault(value = "0")
	@Column(name = "autoriza_precio_e")
	private int AutorizaVentaPrecioE;
	
	@ColumnDefault(value = "0")
	@Column(name = "autoriza_venta_hasta_costo")
	private int AutorizaVentaHastaCosto;
	
	@ColumnDefault(value = "0")
	@Column(name = "autoriza_venta_bajo_costo")
	private int AutorizaVentaBajoCosto;
	
	@ColumnDefault(value = "0")
	@Column(name = "autoriza_plazo_pago")
	private int AutorizaPlazoPago;
	
	@ColumnDefault(value = "0")
	@Column(name = "autoriza_limite_credito")
	private int AutorizaLimiteCredito;
	
	@ColumnDefault(value = "0")
	@Column(name = "puede_descuento_nota")
	private int puedeDescNota;
	
	//usuario stock
	@ColumnDefault(value = "0")
	@Column(name = "puede_ver_todo_pventa")
	private int puedeVerTodoPventa;
	
	@ColumnDefault(value = "0")
	@Column(name = "puede_ver_precio_a")
	private int puedeVerPrecioA;
	
	@ColumnDefault(value = "0")
	@Column(name = "puede_ver_precio_b")
	private int puedeVerPrecioB;
	
	@ColumnDefault(value = "0")
	@Column(name = "puede_ver_precio_c")
	private int puedeVerPrecioC;
	
	@ColumnDefault(value = "0")
	@Column(name = "puede_ver_precio_d")
	private int puedeVerPrecioD;
	
	@ColumnDefault(value = "0")
	@Column(name = "puede_ver_precio_e")
	private int puedeVerPrecioE;
	
	@ColumnDefault(value = "0")
	@Column(name = "puede_ver_costo_fob")
	private int puedeVerCostoFob;
	
	@ColumnDefault(value = "0")
	@Column(name = "puede_ver_costo_cif")
	private int puedeVerCostoCif;
	
	@ColumnDefault(value = "0")
	@Column(name = "habilita_lanzamiento_caja")
	private int habilitaLanzamientoCaja;
	
	public Configuracion() {}
	
	public Configuracion(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPideVendedor() {
		return pideVendedor;
	}

	public void setPideVendedor(int pideVendedor) {
		this.pideVendedor = pideVendedor;
	}

	public int getPideDeposito() {
		return pideDeposito;
	}

	public void setPideDeposito(int pideDeposito) {
		this.pideDeposito = pideDeposito;
	}

	public int getPideDescuento() {
		return pideDescuento;
	}

	public void setPideDescuento(int pideDescuento) {
		this.pideDescuento = pideDescuento;
	}

	public int getPideFlete() {
		return pideFlete;
	}

	public void setPideFlete(int pideFlete) {
		this.pideFlete = pideFlete;
	}

	public int getImprimeMoneda() {
		return imprimeMoneda;
	}

	public void setImprimeMoneda(int imprimeMoneda) {
		this.imprimeMoneda = imprimeMoneda;
	}

	public int getImprimeVendedor() {
		return imprimeVendedor;
	}

	public void setImprimeVendedor(int imprimeVendedor) {
		this.imprimeVendedor = imprimeVendedor;
	}

	public int getImprimePorOrdenAlfabetico() {
		return imprimePorOrdenAlfabetico;
	}

	public void setImprimePorOrdenAlfabetico(int imprimePorOrdenAlfabetico) {
		this.imprimePorOrdenAlfabetico = imprimePorOrdenAlfabetico;
	}

	public int getPermiteVentaPorReferencia() {
		return permiteVentaPorReferencia;
	}

	public void setPermiteVentaPorReferencia(int permiteVentaPorReferencia) {
		this.permiteVentaPorReferencia = permiteVentaPorReferencia;
	}

	public int getPermiteItemDuplicado() {
		return permiteItemDuplicado;
	}

	public void setPermiteItemDuplicado(int permiteItemDuplicado) {
		this.permiteItemDuplicado = permiteItemDuplicado;
	}

	public Long getUsuarioIdPDV() {
		return usuarioIdPDV;
	}
	
	public void setUsuarioIdPDV(Long usuarioId) {
		this.usuarioIdPDV = usuarioId;
	}

	public Empresa getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Empresa empresaId) {
		this.empresaId = empresaId;
	}

	public int getPideDispositivoVenta() {
		return pideDispositivoVenta;
	}

	public void setPideDispositivoVenta(int pideDispositivoVenta) {
		this.pideDispositivoVenta = pideDispositivoVenta;
	}

	public int getBloqueaProductoNegativo() {
		return bloqueaProductoNegativo;
	}

	public void setBloqueaProductoNegativo(int bloqueaProductoNegativo) {
		this.bloqueaProductoNegativo = bloqueaProductoNegativo;
	}

	public int getPermitePrecioPorCliente() {
		return permitePrecioPorCliente;
	}

	public void setPermitePrecioPorCliente(int permitePrecioPorCliente) {
		this.permitePrecioPorCliente = permitePrecioPorCliente;
	}

	public Usuario getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getPideMoneda() {
		return pideMoneda;
	}

	public void setPideMoneda(int pideMoneda) {
		this.pideMoneda = pideMoneda;
	}

	public int getPideDepositoCompra() {
		return pideDepositoCompra;
	}

	public void setPideDepositoCompra(int pideDepositoCompra) {
		this.pideDepositoCompra = pideDepositoCompra;
	}

	public int getPideGastoItemCompra() {
		return pideGastoItemCompra;
	}

	public void setPideGastoItemCompra(int pideGastoItemCompra) {
		this.pideGastoItemCompra = pideGastoItemCompra;
	}

	public int getPideFleteCompraLocal() {
		return pideFleteCompraLocal;
	}

	public void setPideFleteCompraLocal(int pideFleteCompraLocal) {
		this.pideFleteCompraLocal = pideFleteCompraLocal;
	}

	public int getImprimeCompraResumida() {
		return imprimeCompraResumida;
	}

	public void setImprimeCompraResumida(int imprimeCompraResumida) {
		this.imprimeCompraResumida = imprimeCompraResumida;
	}

	public int getPermiteActualizacionAutoCompra() {
		return permiteActualizacionAutoCompra;
	}

	public void setPermiteActualizacionAutoCompra(int permiteActualizacionAutoCompra) {
		this.permiteActualizacionAutoCompra = permiteActualizacionAutoCompra;
	}

	public int getPideDispCompra() {
		return pideDispCompra;
	}

	public void setPideDispCompra(int pideDispCompra) {
		this.pideDispCompra = pideDispCompra;
	}

	public int getImprimeNotaResCompra() {
		return imprimeNotaResCompra;
	}

	public void setImprimeNotaResCompra(int imprimeNotaResCompra) {
		this.imprimeNotaResCompra = imprimeNotaResCompra;
	}

	public int getCalculoPrecioVenta() {
		return calculoPrecioVenta;
	}

	public void setCalculoPrecioVenta(int calculoPrecioVenta) {
		this.calculoPrecioVenta = calculoPrecioVenta;
	}

	public int getRegistroProductoIVA() {
		return registroProductoIVA;
	}

	public void setRegistroProductoIVA(int registroProductoIVA) {
		this.registroProductoIVA = registroProductoIVA;
	}

	public int getTipoComisionVenta() {
		return tipoComisionVenta;
	}

	public void setTipoComisionVenta(int tipoComisionVenta) {
		this.tipoComisionVenta = tipoComisionVenta;
	}

	public int getPideDispNotaPresu() {
		return pideDispNotaPresu;
	}

	public void setPideDispNotaPresu(int pideDispNotaPresu) {
		this.pideDispNotaPresu = pideDispNotaPresu;
	}

	public int getPideDispNotaTrans() {
		return pideDispNotaTrans;
	}

	public void setPideDispNotaTrans(int pideDispNotaTrans) {
		this.pideDispNotaTrans = pideDispNotaTrans;
	}

	public int getPideDispAjusteEntrada() {
		return pideDispAjusteEntrada;
	}

	public void setPideDispAjusteEntrada(int pideDispAjusteEntrada) {
		this.pideDispAjusteEntrada = pideDispAjusteEntrada;
	}

	public int getPideDispNotaAjusteSalida() {
		return pideDispNotaAjusteSalida;
	}

	public void setPideDispNotaAjusteSalida(int pideDispNotaAjusteSalida) {
		this.pideDispNotaAjusteSalida = pideDispNotaAjusteSalida;
	}

	public int getPideDispNotaDevolCompra() {
		return pideDispNotaDevolCompra;
	}

	public void setPideDispNotaDevolCompra(int pideDispNotaDevolCompra) {
		this.pideDispNotaDevolCompra = pideDispNotaDevolCompra;
	}

	public int getPideDispNotaDevolVenta() {
		return pideDispNotaDevolVenta;
	}

	public void setPideDispNotaDevolVenta(int pideDispNotaDevolVenta) {
		this.pideDispNotaDevolVenta = pideDispNotaDevolVenta;
	}

	public int getPideDispNotaAjusteCuenta() {
		return pideDispNotaAjusteCuenta;
	}

	public void setPideDispNotaAjusteCuenta(int pideDispNotaAjusteCuenta) {
		this.pideDispNotaAjusteCuenta = pideDispNotaAjusteCuenta;
	}

	public Long getDefineDepositoVenta() {
		return defineDepositoVenta;
	}

	public void setDefineDepositoVenta(Long defineDepositoVenta) {
		this.defineDepositoVenta = defineDepositoVenta;
	}

	public Long getDefineDepositoCompra() {
		return defineDepositoCompra;
	}

	public void setDefineDepositoCompra(Long defineDepositoCompra) {
		this.defineDepositoCompra = defineDepositoCompra;
	}

	public Long getDefineAperCajaCodigo() {
		return defineAperCajaCodigo;
	}

	public void setDefineAperCajaCodigo(Long defineAperCajaCodigo) {
		this.defineAperCajaCodigo = defineAperCajaCodigo;
	}

	public Long getOperacionBloqCaja() {
		return OperacionBloqCaja;
	}
	
	public void setOperacionBloqCaja(Long operacionBloqCaja) {
		OperacionBloqCaja = operacionBloqCaja;
	}

	public Long getDefineCajaTransferencia() {
		return defineCajaTransferencia;
	}

	public void setDefineCajaTransferencia(Long defineCajaTransferencia) {
		this.defineCajaTransferencia = defineCajaTransferencia;
	}

	public int getAutorizaVentaPrecioD() {
		return AutorizaVentaPrecioD;
	}

	public void setAutorizaVentaPrecioD(int autorizaVentaPrecioD) {
		AutorizaVentaPrecioD = autorizaVentaPrecioD;
	}

	public int getAutorizaVentaPrecioE() {
		return AutorizaVentaPrecioE;
	}

	public void setAutorizaVentaPrecioE(int autorizaVentaPrecioE) {
		AutorizaVentaPrecioE = autorizaVentaPrecioE;
	}

	public int getAutorizaVentaHastaCosto() {
		return AutorizaVentaHastaCosto;
	}

	public void setAutorizaVentaHastaCosto(int autorizaVentaHastaCosto) {
		AutorizaVentaHastaCosto = autorizaVentaHastaCosto;
	}

	public int getAutorizaVentaBajoCosto() {
		return AutorizaVentaBajoCosto;
	}

	public void setAutorizaVentaBajoCosto(int autorizaVentaBajoCosto) {
		AutorizaVentaBajoCosto = autorizaVentaBajoCosto;
	}

	public int getAutorizaPlazoPago() {
		return AutorizaPlazoPago;
	}

	public void setAutorizaPlazoPago(int autorizaPlazoPago) {
		AutorizaPlazoPago = autorizaPlazoPago;
	}

	public int getAutorizaLimiteCredito() {
		return AutorizaLimiteCredito;
	}

	public void setAutorizaLimiteCredito(int autorizaLimiteCredito) {
		AutorizaLimiteCredito = autorizaLimiteCredito;
	}

	public int getPuedeDescNota() {
		return puedeDescNota;
	}

	public void setPuedeDescNota(int puedeDescNota) {
		this.puedeDescNota = puedeDescNota;
	}

	public int getPuedeVerTodoPventa() {
		return puedeVerTodoPventa;
	}

	public void setPuedeVerTodoPventa(int puedeVerTodoPventa) {
		this.puedeVerTodoPventa = puedeVerTodoPventa;
	}

	public int getPuedeVerPrecioA() {
		return puedeVerPrecioA;
	}

	public void setPuedeVerPrecioA(int puedeVerPrecioA) {
		this.puedeVerPrecioA = puedeVerPrecioA;
	}

	public int getPuedeVerPrecioB() {
		return puedeVerPrecioB;
	}

	public void setPuedeVerPrecioB(int puedeVerPrecioB) {
		this.puedeVerPrecioB = puedeVerPrecioB;
	}

	public int getPuedeVerPrecioC() {
		return puedeVerPrecioC;
	}

	public void setPuedeVerPrecioC(int puedeVerPrecioC) {
		this.puedeVerPrecioC = puedeVerPrecioC;
	}

	public int getPuedeVerPrecioD() {
		return puedeVerPrecioD;
	}

	public void setPuedeVerPrecioD(int puedeVerPrecioD) {
		this.puedeVerPrecioD = puedeVerPrecioD;
	}

	public int getPuedeVerPrecioE() {
		return puedeVerPrecioE;
	}

	public void setPuedeVerPrecioE(int puedeVerPrecioE) {
		this.puedeVerPrecioE = puedeVerPrecioE;
	}

	
	public String getPrecioDefinido() {
		return precioDefinido;
	}

	public void setPrecioDefinido(String precioDefinido) {
		this.precioDefinido = precioDefinido;
	}

	public int getPuedeVerCostoFob() {
		return puedeVerCostoFob;
	}

	public void setPuedeVerCostoFob(int puedeVerCostoFob) {
		this.puedeVerCostoFob = puedeVerCostoFob;
	}

	public int getPuedeVerCostoCif() {
		return puedeVerCostoCif;
	}

	public void setPuedeVerCostoCif(int puedeVerCostoCif) {
		this.puedeVerCostoCif = puedeVerCostoCif;
	}

	public int getVentaDirectoContado() {
		return ventaDirectoContado;
	}

	public void setVentaDirectoContado(int ventaDirectoContado) {
		this.ventaDirectoContado = ventaDirectoContado;
	}

	public int getMensajeFinalizaVenta() {
		return mensajeFinalizaVenta;
	}

	public void setMensajeFinalizaVenta(int mensajeFinalizaVenta) {
		this.mensajeFinalizaVenta = mensajeFinalizaVenta;
	}

	public int getNotaRecibidaF3() {
		return notaRecibidaF3;
	}

	public void setNotaRecibidaF3(int notaRecibidaF3) {
		this.notaRecibidaF3 = notaRecibidaF3;
	}

	public int getMonitorCajaF4() {
		return monitorCajaF4;
	}

	public void setMonitorCajaF4(int monitorCajaF4) {
		this.monitorCajaF4 = monitorCajaF4;
	}

	public int getCodigoReferencia() {
		return codigoReferencia;
	}

	public void setCodigoReferencia(int codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public int getAlteraPrecioVenta() {
		return alteraPrecioVenta;
	}

	public void setAlteraPrecioVenta(int alteraPrecioVenta) {
		this.alteraPrecioVenta = alteraPrecioVenta;
	}

	public int getHabilitaDescUsuario() {
		return habilitaDescUsuario;
	}

	public void setHabilitaDescUsuario(int habilitaDescUsuario) {
		this.habilitaDescUsuario = habilitaDescUsuario;
	}

	public int getTipoDescUsuario() {
		return tipoDescUsuario;
	}

	public void setTipoDescUsuario(int tipoDescUsuario) {
		this.tipoDescUsuario = tipoDescUsuario;
	}

	public Double getLimiteDescNotaValor() {
		return limiteDescNotaValor;
	}

	public void setLimiteDescNotaValor(Double limiteDescNotaValor) {
		this.limiteDescNotaValor = limiteDescNotaValor;
	}

	public Long getVisualizaDepositoUsuario() {
		return visualizaDepositoUsuario;
	}

	public void setVisualizaDepositoUsuario(Long visualizaDepositoUsuario) {
		this.visualizaDepositoUsuario = visualizaDepositoUsuario;
	}

	public int getCajaIdPDV() {
		return cajaIdPDV;
	}

	public void setCajaIdPDV(int cajaIdPDV) {
		this.cajaIdPDV = cajaIdPDV;
	}

	public int getDepositoIdPDV() {
		return depositoIdPDV;
	}

	public void setDepositoIdPDV(int depositoIdPDV) {
		this.depositoIdPDV = depositoIdPDV;
	}

	public int getHabilitaLanzamientoCaja() {
		return habilitaLanzamientoCaja;
	}

	public void setHabilitaLanzamientoCaja(int habilitaLanzamientoCaja) {
		this.habilitaLanzamientoCaja = habilitaLanzamientoCaja;
	}
	
	

}