package py.com.prestosoftware.ui.main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.GlobalVars;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import javax.swing.JButton;
import java.awt.GridLayout;

@Component
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JMenuItem mnuMercaderia;
	private JMenuItem mnuCategoria;
	private JMenuItem mnuMarca;
	private JMenuItem mnuGrupo;
	private JMenuItem mnuImpuesto;
	private JMenuItem mnuUnidadMedida;
	private JMenuItem mnuListaPrecio;
	
	private JMenuItem mnuDeposito;
	private JMenuItem mnuCliente;
	private JMenuItem mnuProveedor;
	private JMenuItem mnuPais;
	private JMenuItem mnuDepartamento;
	private JMenuItem mnuCiudad;
	private JMenuItem mnuVenta;
	private JMenuItem mnuCompra;
	private JMenuItem mnuPresupuesto;
	private JMenu mnuDevolucion;
	private JMenu mnuMovCaja;
	private JMenuItem mnuMovCajaIngreso;
	private JMenuItem mnuMovCajaEgreso;
	private JMenuItem mnuNcm;
	private JMenuItem mnuTamanhos;
	private JMenuItem mnuColores;
	private JMenu mnConfiguracion;
	private JMenuItem mnuEmpresa;
	private JMenuItem mnuUsuario;
	private JMenuItem mnuUsuarioRol;
	private JMenuItem mnuRol;
	private JMenuItem mnuPermisos;
	private JMenuItem mnuCaja;
	
	private JMenuItem mnuAperturaCaja;
	private JMenuItem mnuCierreCaja;
	private JMenuItem mnuLanzamiento;
	private JMenuItem mnuCotizacion;
	private JMenuItem mnuPlanDeCuenta;
	private JMenuItem mnuTransferencia;
	private JMenuItem mnuTransformacion;
	private JMenuItem mnuMoneda;
	private JMenuItem mntmImpresoras;
	private JMenuItem mnuBoletaFiscal;
	private JMenuItem mnuEmpaque;
	private JMenuItem mnuBoleta;
	private JMenuItem mnuAgenda;
	private JMenuItem mnuSubgrupo;
	private JPanel panel;
	private JLabel lblEmpresaNombre;
	private JLabel lblUsuarioNombre;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblFechaSistema;
	private JMenuItem mntmSalir;
	private JMenuItem mnuDevolucionVenta;
	private JMenuItem mnuDevolucionCompra;
	private JMenuItem mnuConsultaClientes;
	private JMenuItem mnuConsultaProveedor;
	private JMenuItem mnuSaldoDeposito;
	private JMenu mnuInfProductos;
	private JMenu mnuInfDatosProductos;
	private JMenuItem mnuInfStockPorDeposito;
	private JMenu mnuMovCuentaAPagarRecibir;
	private JMenu mnuMovCuentaAPagar;
	private JMenu mnuMovCuentaARecibir;
	private JMenuItem mnuMovCuentaAPagarPagoProveedor;
	private JMenuItem mnuMovCuentaARecibirCobroCliente;
	private JMenu mnuInfCuentaAPagarRecibir;
	private JMenu mnuInfEstadoFinanciero;
	private JMenuItem mnuInfResumenUtilidadProducto;
	private JMenu mnuInfCuentaAPagar;
	private JMenu mnuInfCuentaARecibir;
	private JMenuItem mnuInfCuentaAPagarVencimientoProveedor;
	private JMenuItem mnuInfCuentaAPagarExtractoProveedor;
	private JMenuItem mnuInfCuentaARecibirVencimientoCliente;
	private JMenuItem mnuInfCuentaARecibirExtractoCliente;
	private JMenu mnuInfCajas;
	private JMenuItem mnuInfResumenCajas;
	private JMenuItem mnuInfResumenAgrupadoCajas;
	private JMenuItem mnuAnularBoleta;
	private JPanel panel_1;
	private JButton btnClientes;
	private JButton btnProveedores;
	private JButton btnVentas;
	private JButton btnCompras;
	private JButton btnProductos;
	private JButton btnPDV;
	private JButton btnLanzamientos;
	private JMenuItem mnuSaldoStock;
	private JMenuItem mnuAjusteEntrada;
	private JMenuItem mnuAjusteSalida;
	private JMenuItem mnuDebitoCliente;
	private JMenuItem mnuCreditoCliente;
	private JMenuItem mnuDebitoProveedor;
	private JMenuItem mnuCreditoProveedor;
	private JMenuItem mnuEmpaque_2;
	private JMenuItem mnuPDV;
	private JMenuItem mnuConfig;
	private JMenuItem mnuCondicionDePago;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 393);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("SOFTWARE DE GESTIÓN EMPRESARIAL");
		
		addWindowListener(new java.awt.event.WindowAdapter() {   
			public void windowClosing(java.awt.event.WindowEvent e) {    
				System.exit(0);   
			}		 
		});	
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnuRegistros = new JMenu("REGISTROS");
		mnuRegistros.setMnemonic('R');
		JMenu mnuProducto = new JMenu("PRODUCTOS");
		menuBar.add(mnuRegistros);

		mnuMercaderia = new JMenuItem("MERCADERIAS");
		mnuMercaderia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		mnuCategoria = new JMenuItem("CATEGORIAS");
		mnuCategoria.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnuGrupo = new JMenuItem("GRUPOS");
		mnuGrupo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		mnuNcm = new JMenuItem("NCM");
		mnuNcm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnuMarca = new JMenuItem("MARCAS");
		mnuMarca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		mnuImpuesto = new JMenuItem("IMPUESTO IVA");
		mnuImpuesto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		mnuTamanhos = new JMenuItem("TAMAÑOS");
		mnuTamanhos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		mnuColores = new JMenuItem("COLORES");
		mnuColores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnuUnidadMedida = new JMenuItem("UNIDAD DE MEDIDA");
		mnuUnidadMedida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
		mnuListaPrecio = new JMenuItem("LISTA DE PRECIOS");
		mnuListaPrecio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		
		mnuProducto.add(mnuMercaderia);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuGrupo);
		mnuProducto.addSeparator();
		mnuSubgrupo = new JMenuItem("SUB GRUPOS");
		mnuSubgrupo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnuProducto.add(mnuSubgrupo);
		mnuProducto.addSeparator();
		mnuEmpaque_2 = new JMenuItem("EMPAQUES");
		mnuProducto.add(mnuEmpaque_2);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuNcm);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuCategoria);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuImpuesto);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuMarca);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuColores);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuTamanhos);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuUnidadMedida);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuListaPrecio);
		
		mnuProveedor = new JMenuItem("PROVEEDORES");
		mnuProveedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnuCliente = new JMenuItem("CLIENTES");
		mnuCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnuCliente.setMnemonic('C');
		mnuCliente.setMnemonic(KeyEvent.VK_ALT);
		JMenu mnLocalidad = new JMenu("LOCALIDADES");
		
		mnuRegistros.add(mnuProducto);
		mnuRegistros.addSeparator();
		mnuRegistros.add(mnuProveedor);
		mnuRegistros.addSeparator();
		mnuRegistros.add(mnuCliente);
		mnuRegistros.addSeparator();
		mnuRegistros.add(mnLocalidad);
		
		mnuPais = new JMenuItem("PAISES");
		mnuPais.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuDepartamento = new JMenuItem("DEPARTAMENTOS");
		mnuDepartamento.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		mnuCiudad = new JMenuItem("CIUDADES");
		mnuCiudad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		
		mnLocalidad.add(mnuPais);
		mnLocalidad.addSeparator();
		mnLocalidad.add(mnuDepartamento);
		mnLocalidad.addSeparator();
		mnLocalidad.add(mnuCiudad);
		
		JMenu mnuMovimientos = new JMenu("MOVIMIENTOS");
		mnuMovimientos.setMnemonic('M');
		menuBar.add(mnuMovimientos);
		mnuCompra = new JMenuItem("COMPRAS");
		mnuCompra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK));
		
		mnuVenta = new JMenuItem("VENTAS");
		mnuVenta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_MASK));
		mnuMovimientos.add(mnuVenta);
		mnuMovimientos.addSeparator();
		mnuMovimientos.add(mnuCompra);
//		mnuPDV = new JMenuItem("PDV");
//		mnuMovimientos.add(mnuPDV);
//		mnuMovimientos.addSeparator();
		
//		mnuPresupuesto = new JMenuItem("PRESUPUESTOS");
//		mnuPresupuesto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_MASK));
//		
//		mnuMovimientos.add(mnuPresupuesto);
//		mnuMovimientos.addSeparator();
		
		//mnuMovimientos.addSeparator();
		
		
		mnuDevolucion = new JMenu("DEVOLUCIONES");
		
		mnuMovCuentaAPagarRecibir = new JMenu("CUENTAS A PAGAR Y RECIBIR");
		mnuMovCuentaAPagar = new JMenu("CUENTAS A PAGAR");
		mnuMovCuentaARecibir = new JMenu("CUENTAS A RECIBIR");
		mnuMovCuentaAPagarRecibir.add(mnuMovCuentaARecibir);
		mnuMovCuentaAPagarRecibir.addSeparator();
		mnuMovCuentaAPagarRecibir.add(mnuMovCuentaAPagar);
		mnuMovCuentaAPagarPagoProveedor = new JMenuItem("PAGO A PROVEEDORES");
		mnuMovCuentaAPagar.add(mnuMovCuentaAPagarPagoProveedor);
		mnuMovCuentaARecibirCobroCliente = new JMenuItem("COBRO A CLIENTES");
		mnuMovCuentaARecibir.add(mnuMovCuentaARecibirCobroCliente);
		mnuMovimientos.add(mnuMovCuentaAPagarRecibir);
		mnuMovimientos.addSeparator();
		
		mnuMovCaja = new JMenu("CAJAS");
		mnuMovCajaIngreso = new JMenuItem("MOVIMIENTO DE INGRESO DE EFECTIVO");
		mnuMovCajaEgreso = new JMenuItem("MOVIMIENTO DE EGRESO DE EFECTIVO");
		mnuMovCaja.add(mnuMovCajaIngreso);
		mnuMovCaja.addSeparator();
		mnuMovCaja.add(mnuMovCajaEgreso);
		mnuMovimientos.add(mnuMovCaja);
		mnuMovimientos.addSeparator();
		
//		mnuAnularBoleta = new JMenuItem("ANULAR BOLETAS");
//		mnuAnularBoleta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_MASK));
//		mnuMovimientos.add(mnuAnularBoleta);
//		mnuMovimientos.addSeparator();
		
//		mnuTransferencia = new JMenuItem("TRANSFERENCIA");
//		mnuTransferencia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK));
//		mnuMovimientos.add(mnuTransferencia);
//		mnuMovimientos.addSeparator();
//		mnuMovimientos.add(mnuDevolucion);
//		mnuMovimientos.addSeparator();
		mnuTransformacion = new JMenuItem("TRANSFORMACION DE PRODUCTOS");
		mnuTransformacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
		mnuMovimientos.add(mnuTransformacion);
		
//		mnuDevolucionVenta = new JMenuItem("VENTAS");
//		mnuDevolucion.add(mnuDevolucionVenta);
//		mnuDevolucion.addSeparator();
//		mnuDevolucionCompra = new JMenuItem("COMPRAS");
//		mnuDevolucion.add(mnuDevolucionCompra);
		
//		mnAjustes = new JMenu("AJUSTES STOCK");
//		mnuMovimientos.add(mnAjustes);
//		
//		mnuAjusteEntrada = new JMenuItem("ENTRADAS");
//		mnAjustes.add(mnuAjusteEntrada);
//		mnAjustes.addSeparator();
//		mnuAjusteSalida = new JMenuItem("SALIDAS");
//		mnAjustes.add(mnuAjusteSalida);
//		mnuMovimientos.addSeparator();
//		
//		mnuAjusteCuenta = new JMenu("AJUSTE CUENTA");
//		mnuMovimientos.add(mnuAjusteCuenta);
//		
//		mnuDebitoCliente = new JMenuItem("DEBITO CLIENTES");
//		mnuAjusteCuenta.add(mnuDebitoCliente);
//		
//		mnuCreditoCliente = new JMenuItem("CREDITO CLIENTES");
//		mnuAjusteCuenta.add(mnuCreditoCliente);
//		mnuAjusteCuenta.addSeparator();
//		mnuDebitoProveedor = new JMenuItem("DEBITO PROVEEDORES");
//		mnuAjusteCuenta.add(mnuDebitoProveedor);
//		
//		mnuCreditoProveedor = new JMenuItem("CREDITO PROVEEDORES");
//		mnuAjusteCuenta.add(mnuCreditoProveedor);
		
//		JMenu mnuFinanciero = new JMenu("FINANCIERO");
//		mnuFinanciero.setMnemonic('F');
//		menuBar.add(mnuFinanciero);
		
//		mnuAperturaCaja = new JMenuItem("APERTURA DE CAJA");
//		mnuAperturaCaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
//		
//		mnuFinanciero.add(mnuAperturaCaja);
//		mnuFinanciero.addSeparator();
//		mnuCierreCaja = new JMenuItem("CIERRE DE CAJA");
//		mnuCierreCaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
//		mnuFinanciero.add(mnuCierreCaja);
//		mnuFinanciero.addSeparator();
//		mnuLanzamiento = new JMenuItem("LANZAMIENTO DE CAJA");
//		mnuLanzamiento.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
//		mnuFinanciero.add(mnuLanzamiento);
//		mnuFinanciero.addSeparator();
//		mnuCotizacion = new JMenuItem("COTIZACIONES");
//		mnuCotizacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
//		mnuFinanciero.add(mnuCotizacion);
		
//		JMenu mnuConsultas = new JMenu("CONSULTAS");
//		mnuConsultas.setMnemonic('C');
//		menuBar.add(mnuConsultas);
		
//		mnuBoletaFiscal = new JMenuItem("BOLETA FISCAL");
//		mnuBoletaFiscal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.ALT_GRAPH_MASK));
//		mnuConsultas.add(mnuBoletaFiscal);
//		mnuConsultas.addSeparator();
//		mnuEmpaque = new JMenuItem("EMPAQUE");
//		mnuEmpaque.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_GRAPH_MASK));
//		mnuConsultas.add(mnuEmpaque);
//		mnuConsultas.addSeparator();
//		mnuBoleta = new JMenuItem("BOLETAS");
//		mnuBoleta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_GRAPH_MASK));
//		mnuConsultas.add(mnuBoleta);
//		mnuConsultas.addSeparator();
//		mnuAgenda = new JMenuItem("AGENDA");
//		mnuAgenda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_GRAPH_MASK));
//		mnuConsultas.add(mnuAgenda);
//		mnuConsultas.addSeparator();
//		mnuConsultaClientes = new JMenuItem("CLIENTES");
//		mnuConsultaClientes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
//		mnuConsultas.add(mnuConsultaClientes);
//		mnuConsultas.addSeparator();
//		mnuConsultaProveedor = new JMenuItem("PROVEEDORES");
//		mnuConsultaProveedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
//		mnuConsultas.add(mnuConsultaProveedor);
//		mnuConsultas.addSeparator();
//		
//		mnuSaldoStock = new JMenuItem("SALDO STOCK");
//		mnuSaldoStock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
//		mnuConsultas.add(mnuSaldoStock);
//		mnuConsultas.addSeparator();
//		mnuSaldoDeposito = new JMenuItem("SALDO DEPOSITOS ");
//		mnuSaldoDeposito.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
//		mnuConsultas.add(mnuSaldoDeposito);
		
		JMenu mnuRelatorios = new JMenu("INFORMES");
		mnuRelatorios.setMnemonic('L');
		menuBar.add(mnuRelatorios);
		
		mnuInfProductos = new JMenu("PRODUCTOS");
		mnuRelatorios.add(mnuInfProductos);
		
		mnuInfDatosProductos= new JMenu("DATOS DEL PRODUCTO");
		mnuInfStockPorDeposito = new JMenuItem("INFORME DE STOCK POR DEPOSITO");
		mnuInfProductos.add(mnuInfDatosProductos);
		mnuInfDatosProductos.add(mnuInfStockPorDeposito);
		
		mnuInfCuentaAPagarRecibir = new JMenu("CUENTAS A PAGAR Y RECIBIR");
		mnuInfCuentaAPagar = new JMenu("CUENTAS A PAGAR");
		mnuInfCuentaARecibir = new JMenu("CUENTAS A RECIBIR");
		
		mnuInfCuentaAPagarRecibir.add(mnuInfCuentaARecibir);
		mnuInfCuentaAPagarRecibir.addSeparator();
		mnuInfCuentaAPagarRecibir.add(mnuInfCuentaAPagar);
		
		mnuInfCuentaAPagarVencimientoProveedor = new JMenuItem("VENCIMIENTO DE MOVIMIENTOS DEL PROVEEDOR");
		//mnuInfCuentaAPagarExtractoProveedor= new JMenuItem("EXTRACTO DE MOVIMIENTOS DEL PROVEEDOR");
		mnuInfCuentaAPagar.add(mnuInfCuentaAPagarVencimientoProveedor);
//		mnuInfCuentaAPagar.addSeparator();
//		mnuInfCuentaAPagar.add(mnuInfCuentaAPagarExtractoProveedor);
		
		mnuInfCuentaARecibirVencimientoCliente= new JMenuItem("VENCIMIENTO DE MOVIMIENTOS DEL CLIENTE");
		//mnuInfCuentaARecibirExtractoCliente= new JMenuItem("EXTRACTO DE MOVIMIENTOS DEL CLIENTE");
		mnuInfCuentaARecibir.add(mnuInfCuentaARecibirVencimientoCliente);
//		mnuInfCuentaARecibir.addSeparator();
//		mnuInfCuentaARecibir.add(mnuInfCuentaARecibirExtractoCliente);
		
		mnuRelatorios.add(mnuInfCuentaAPagarRecibir);
		
		mnuInfEstadoFinanciero = new JMenu("ESTADO FINANCIERO");
		mnuInfResumenUtilidadProducto = new JMenuItem("RESUMEN DE UTILIDAD DE PRODUCTOS");
		mnuInfEstadoFinanciero.add(mnuInfResumenUtilidadProducto);
		mnuRelatorios.add(mnuInfEstadoFinanciero);
		mnuInfCajas = new JMenu("CAJAS");
		mnuInfResumenCajas= new JMenuItem("RESUMEN DETALLADO DE INGRESO EGRESO CAJA");
		mnuInfResumenAgrupadoCajas= new JMenuItem("RESUMEN AGRUPADO DE INGRESO EGRESO");
		
		mnuInfCajas.add(mnuInfResumenCajas);
		mnuInfCajas.addSeparator();
		mnuInfCajas.add(mnuInfResumenAgrupadoCajas);
		
		mnuRelatorios.add(mnuInfCajas);
		
//		JMenu mnuUtilidades = new JMenu("UTILIDADES");
//		mnuUtilidades.setMnemonic('U');
//		menuBar.add(mnuUtilidades);
		
		mnConfiguracion = new JMenu("CONFIG");
		mnConfiguracion.setMnemonic('C');
		menuBar.add(mnConfiguracion);
		
//		mnuEmpresa = new JMenuItem("EMPRESAS");
//		mnuEmpresa.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
//		mnConfiguracion.add(mnuEmpresa);
//		mnConfiguracion.addSeparator();
//		mnuDeposito = new JMenuItem("DEPOSITOS");
//		mnuDeposito.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
//		mnConfiguracion.add(mnuDeposito);
//		mnuPlanDeCuenta = new JMenuItem("PLAN DE CUENTAS");
//		mnuPlanDeCuenta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
//		mnConfiguracion.addSeparator();
//		mnConfiguracion.add(mnuPlanDeCuenta);
//		mnConfiguracion.addSeparator();
//		mnuMoneda = new JMenuItem("MONEDA");
//		mnuMoneda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
//		mnConfiguracion.add(mnuMoneda);
//		mnConfiguracion.addSeparator();
//		mnuCondicionDePago = new JMenuItem("CONDICION DE PAGO");
//		mnConfiguracion.add(mnuCondicionDePago);
		mnConfiguracion.addSeparator();
		mntmImpresoras = new JMenuItem("IMPRESORAS");
		mntmImpresoras.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mntmImpresoras);
		mnConfiguracion.addSeparator();
//		mnuCaja = new JMenuItem("CAJAS");
//		mnuCaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
//		mnConfiguracion.add(mnuCaja);
//		mnConfiguracion.addSeparator();
		mnuUsuario = new JMenuItem("USUARIOS");
		mnuUsuario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mnuUsuario);
		mnConfiguracion.addSeparator();
		mnuUsuarioRol = new JMenuItem("USUARIO ROLES");
		mnuUsuarioRol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mnuUsuarioRol);
		mnConfiguracion.addSeparator();
		mnuRol = new JMenuItem("ROLES");
		mnuRol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mnuRol);
		mnConfiguracion.addSeparator();
		mnuPermisos = new JMenuItem("PERMISOS");
		mnuPermisos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mnuPermisos);
		mnConfiguracion.addSeparator();
		mnuConfig = new JMenuItem("CONFIG. GENERAL");
		mnConfiguracion.add(mnuConfig);
		mnConfiguracion.addSeparator();
		mntmSalir = new JMenuItem("SALIR");
		mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnConfiguracion.add(mntmSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		lblEmpresaNombre = new JLabel("EMPRESA NOMBRE");
		panel.add(lblEmpresaNombre);
		
		label = new JLabel(" - ");
		panel.add(label);
		
		lblUsuarioNombre = new JLabel("USUARIO NOMBRE");
		panel.add(lblUsuarioNombre);
		
		label_1 = new JLabel(" - ");
		panel.add(label_1);
		
		lblFechaSistema = new JLabel("FECHA SISTEMA");
		lblFechaSistema.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		panel.add(lblFechaSistema);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new GridLayout(0, 1, 10, 15));
		
//		btnClientes = new JButton("CLIENTES");
//		btnClientes.setFont(new Font("Lucida Grande", Font.BOLD, 20));
//		
//		panel_1.add(btnClientes);
		
//		btnProveedores = new JButton("PROVEEDORES");
//		btnProveedores.setFont(new Font("Lucida Grande", Font.BOLD, 20));
//		
//		panel_1.add(btnProveedores);
		
		btnVentas = new JButton("VENTAS");
		btnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVentas.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		
//		btnProductos = new JButton("PRODUCTOS");
//		btnProductos.setFont(new Font("Lucida Grande", Font.BOLD, 20));
//		panel_1.add(btnProductos);
		panel_1.add(btnVentas);
		
		btnCompras = new JButton("COMPRAS");
		btnCompras.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		btnCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_1.add(btnCompras);
		
//		btnPDV = new JButton("PDV");
//		btnPDV.setFont(new Font("Lucida Grande", Font.BOLD, 20));
//		panel_1.add(btnPDV);
//		
//		btnLanzamientos = new JButton("LANZAMIENTOS");
//		btnLanzamientos.setFont(new Font("Lucida Grande", Font.BOLD, 20));
//		panel_1.add(btnLanzamientos);
	}

	public JPanel getContentPane() {
		return contentPane;
	}
	
	public JMenuItem getMnuAperturaCaja() {
		return mnuAperturaCaja;
	}
	
	public JMenuItem getMnuCierreCaja() {
		return mnuCierreCaja;
	}
	
	public JMenuItem getMnuLanzamiento() {
		return mnuLanzamiento;
	}

	public JMenuItem getMnuMercaderia() {
		return mnuMercaderia;
	}

	public JMenuItem getMnuCategoria() {
		return mnuCategoria;
	}

	public JMenuItem getMnuMarca() {
		return mnuMarca;
	}

	public JMenuItem getMnuGrupo() {
		return mnuGrupo;
	}

	public JMenuItem getMnuImpuesto() {
		return mnuImpuesto;
	}

	public JMenuItem getMnuUnidadMedida() {
		return mnuUnidadMedida;
	}

	public JMenuItem getMnuListaPrecio() {
		return mnuListaPrecio;
		
	}

	public JMenuItem getMnuDeposito() {
		return mnuDeposito;
	}

	public JMenuItem getMnuCliente() {
		return mnuCliente;
	}

	public JMenuItem getMnuProveedor() {
		return mnuProveedor;
	}

	public JMenuItem getMnuPais() {
		return mnuPais;
	}

	public JMenuItem getMnuDepartamento() {
		return mnuDepartamento;
	}

	public JMenuItem getMnuCiudad() {
		return mnuCiudad;
	}

	public JMenuItem getMnuVenta() {
		return mnuVenta;
	}

	public JMenuItem getMnuCompra() {
		return mnuCompra;
	}

	public JMenuItem getMnuPresupuesto() {
		return mnuPresupuesto;
	}

	public JMenuItem getMnuDevolucion() {
		return mnuDevolucion;
	}

	public JMenuItem getMnuNcm() {
		return mnuNcm;
	}

	public JMenuItem getMnuTamanhos() {
		return mnuTamanhos;
	}

	public JMenuItem getMnuColores() {
		return mnuColores;
	}

	public JMenuItem getMnuEmpresa() {
		return mnuEmpresa;
	}

	public JMenuItem getMnuUsuario() {
		return mnuUsuario;
	}

	public JMenuItem getMnuUsuarioRol() {
		return mnuUsuarioRol;
	}
	
	public JMenuItem getMnuRol() {
		return mnuRol;
	}

	public JMenuItem getMnuPermisos() {
		return mnuPermisos;
	}

	public JMenuItem getMnuCaja() {
		return mnuCaja;
	}

	public JMenuItem getMnuCotizacion() {
		return mnuCotizacion;
	}

	public JMenuItem getMnuPlanDeCuenta() {
		return mnuPlanDeCuenta;
	}

	public JMenuItem getMnuTransferencia() {
		return mnuTransferencia;
	}
	
	public JMenuItem getMnuTransformacion() {
		return mnuTransformacion;
	}

	public void setMnuTransformacion(JMenuItem mnuTransformacion) {
		this.mnuTransformacion = mnuTransformacion;
	}

	public JMenuItem getMnuMoneda() {
		return mnuMoneda;
	}
	
	public JMenuItem getMnuSubgrupo() {
		return mnuSubgrupo;
	}
	
	public JButton getBtnClientes() {
		return btnClientes;
	}
	
	public JButton getBtnProveedores() {
		return btnProveedores;
	}
	
	public JButton getBtnProductos() {
		return btnProductos;
	}
	
	public JButton getBtnVentas() {
		return btnVentas;
	}
	
	public JButton getBtnCompras() {
		return btnCompras;
	}
	
	public JButton getBtnPDV() {
		return btnPDV;
	}
	
	public JButton getBtnLanzamientos() {
		return btnLanzamientos;
	}
	
	public JMenuItem getMnuBoleta() {
		return mnuBoleta;
	}
	
	public JMenuItem getMnuBoletaFiscal() {
		return mnuBoletaFiscal;
	}
	
	public JMenuItem getMnuAgenda() {
		return mnuAgenda;
	}
	
	public JMenuItem getMnuEmpaque() {
		return mnuEmpaque;
	}
	
	public JMenuItem getMnuConsultaClientes() {
		return mnuConsultaClientes;
	}
	
	public JMenuItem getMnuConsultaProveedor() {
		return mnuConsultaProveedor;
	}
	
	public JMenuItem getMnuSaldoDeposito() {
		return mnuSaldoDeposito;
	}
	
	public JMenuItem getMnuAjusteEntrada() {
		return mnuAjusteEntrada;
	}
	
	public JMenuItem getMnuAjusteSalida() {
		return mnuAjusteSalida;
	}
	
	public JMenuItem getMnuDevolucionVenta() {
		return mnuDevolucionVenta;
	}
	
	public JMenuItem getMnuDevolucionCompra() {
		return mnuDevolucionCompra;
	}
	
	public JMenuItem getMnuDebitoCliente() {
		return mnuDebitoCliente;
	}
	
	public JMenuItem getMnuDebitoProveedor() {
		return mnuDebitoProveedor;
	}
	
	public JMenuItem getMnuCreditoCliente() {
		return mnuCreditoCliente;
	}
	
	public JMenuItem getMnuCreditoProveedor() {
		return mnuCreditoProveedor;
	}
	
	public void loadInfoUser() {
		lblEmpresaNombre.setText(GlobalVars.EMPRESA);
		lblUsuarioNombre.setText(GlobalVars.USER);
		lblFechaSistema.setText(Fechas.formatoDDMMAAAA(new Date()));
	}
	
	public JMenuItem getMnuSaldoStock() {
		return mnuSaldoStock;
	}
	
	public JMenuItem getMnuAnularBoleta() {
		return mnuAnularBoleta;
	}
	
	public JMenuItem getMnuPDV() {
		return mnuPDV;
	}
	
	public JMenuItem getMnuConfig() {
		return mnuConfig;
	}
	
	public JMenuItem getMnuEmpaque_2() {
		return mnuEmpaque_2;
	}
	
	public JMenuItem getMnuCondicionDePago() {
		return mnuCondicionDePago;
	}

	public JMenu getMnuInfProductos() {
		return mnuInfProductos;
	}

	public void setMnuInfProductos(JMenu mnuInfProductos) {
		this.mnuInfProductos = mnuInfProductos;
	}

	public JMenuItem getMnuInfCuentaAPagarVencimientoProveedor() {
		return mnuInfCuentaAPagarVencimientoProveedor;
	}

	public void setMnuInfCuentaAPagarVencimientoProveedor(JMenuItem mnuInfCuentaAPagarVencimientoProveedor) {
		this.mnuInfCuentaAPagarVencimientoProveedor = mnuInfCuentaAPagarVencimientoProveedor;
	}

	public JMenuItem getMnuInfCuentaAPagarExtractoProveedor() {
		return mnuInfCuentaAPagarExtractoProveedor;
	}

	public void setMnuInfCuentaAPagarExtractoProveedor(JMenuItem mnuInfCuentaAPagarExtractoProveedor) {
		this.mnuInfCuentaAPagarExtractoProveedor = mnuInfCuentaAPagarExtractoProveedor;
	}

	public JMenuItem getMnuInfCuentaARecibirVencimientoCliente() {
		return mnuInfCuentaARecibirVencimientoCliente;
	}

	public void setMnuInfCuentaARecibirVencimientoCliente(JMenuItem mnuInfCuentaARecibirVencimientoCliente) {
		this.mnuInfCuentaARecibirVencimientoCliente = mnuInfCuentaARecibirVencimientoCliente;
	}

	public JMenuItem getMnuInfCuentaARecibirExtractoCliente() {
		return mnuInfCuentaARecibirExtractoCliente;
	}

	public void setMnuInfCuentaARecibirExtractoCliente(JMenuItem mnuInfCuentaARecibirExtractoCliente) {
		this.mnuInfCuentaARecibirExtractoCliente = mnuInfCuentaARecibirExtractoCliente;
	}

	public JMenuItem getMnuInfResumenCajas() {
		return mnuInfResumenCajas;
	}

	public void setMnuInfResumenCajas(JMenuItem mnuInfResumenCajas) {
		this.mnuInfResumenCajas = mnuInfResumenCajas;
	}

	public JMenuItem getMnuInfResumenAgrupadoCajas() {
		return mnuInfResumenAgrupadoCajas;
	}

	public void setMnuInfResumenAgrupadoCajas(JMenuItem mnuInfResumenAgrupadoCajas) {
		this.mnuInfResumenAgrupadoCajas = mnuInfResumenAgrupadoCajas;
	}

	public JMenuItem getMnuInfStockPorDeposito() {
		return mnuInfStockPorDeposito;
	}

	public void setMnuInfStockPorDeposito(JMenuItem mnuInfStockPorDeposito) {
		this.mnuInfStockPorDeposito = mnuInfStockPorDeposito;
	}

	public JMenu getMnuInfEstadoFinanciero() {
		return mnuInfEstadoFinanciero;
	}

	public void setMnuInfEstadoFinanciero(JMenu mnuInfEstadoFinanciero) {
		this.mnuInfEstadoFinanciero = mnuInfEstadoFinanciero;
	}

	public JMenuItem getMnuInfResumenUtilidadProducto() {
		return mnuInfResumenUtilidadProducto;
	}

	public void setMnuInfResumenUtilidadProducto(JMenuItem mnuInfResumenUtilidadProducto) {
		this.mnuInfResumenUtilidadProducto = mnuInfResumenUtilidadProducto;
	}

	public JMenuItem getMnuMovCajaIngreso() {
		return mnuMovCajaIngreso;
	}

	public void setMnuMovCajaIngreso(JMenuItem mnuMovCajaIngreso) {
		this.mnuMovCajaIngreso = mnuMovCajaIngreso;
	}

	public JMenuItem getMnuMovCajaEgreso() {
		return mnuMovCajaEgreso;
	}

	public void setMnuMovCajaEgreso(JMenuItem mnuMovCajaEgreso) {
		this.mnuMovCajaEgreso = mnuMovCajaEgreso;
	}

	public JMenuItem getMnuMovCuentaAPagarPagoProveedor() {
		return mnuMovCuentaAPagarPagoProveedor;
	}

	public void setMnuMovCuentaAPagarPagoProveedor(JMenuItem mnuMovCuentaAPagarPagoProveedor) {
		this.mnuMovCuentaAPagarPagoProveedor = mnuMovCuentaAPagarPagoProveedor;
	}

	public JMenuItem getMnuMovCuentaARecibirCobroCliente() {
		return mnuMovCuentaARecibirCobroCliente;
	}

	public void setMnuMovCuentaARecibirCobroCliente(JMenuItem mnuMovCuentaARecibirCobroCliente) {
		this.mnuMovCuentaARecibirCobroCliente = mnuMovCuentaARecibirCobroCliente;
	}
	
}
