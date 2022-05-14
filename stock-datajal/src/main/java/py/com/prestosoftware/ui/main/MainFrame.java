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
	private JMenuItem mnuFacturacion;
	private JMenuItem mntmNewMenuItem;

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
		mnuRegistros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuRegistros.setMnemonic('R');
		JMenu mnuProducto = new JMenu("PRODUCTOS");
		mnuProducto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(mnuRegistros);

		mnuMercaderia = new JMenuItem("MERCADERIAS");
		mnuMercaderia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMercaderia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		mnuCategoria = new JMenuItem("CATEGORIAS");
		mnuCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuCategoria.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnuGrupo = new JMenuItem("GRUPOS");
		mnuGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuGrupo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		mnuNcm = new JMenuItem("NCM");
		mnuNcm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuNcm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnuMarca = new JMenuItem("MARCAS");
		mnuMarca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMarca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		mnuImpuesto = new JMenuItem("IMPUESTO IVA");
		mnuImpuesto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuImpuesto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		mnuTamanhos = new JMenuItem("TAMAÑOS");
		mnuTamanhos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuTamanhos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		mnuColores = new JMenuItem("COLORES");
		mnuColores.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuColores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnuUnidadMedida = new JMenuItem("UNIDAD DE MEDIDA");
		mnuUnidadMedida.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuUnidadMedida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
		mnuListaPrecio = new JMenuItem("LISTA DE PRECIOS");
		mnuListaPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuListaPrecio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		
		mnuProducto.add(mnuMercaderia);
		mnuProducto.addSeparator();
		mnuProducto.add(mnuGrupo);
		mnuProducto.addSeparator();
		mnuSubgrupo = new JMenuItem("SUB GRUPOS");
		mnuSubgrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuSubgrupo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnuProducto.add(mnuSubgrupo);
		mnuProducto.addSeparator();
		mnuEmpaque_2 = new JMenuItem("EMPAQUES");
		mnuEmpaque_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
		mnuProveedor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuProveedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnuCliente = new JMenuItem("CLIENTES");
		mnuCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnuCliente.setMnemonic('C');
		mnuCliente.setMnemonic(KeyEvent.VK_ALT);
		JMenu mnLocalidad = new JMenu("LOCALIDADES");
		mnLocalidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		mnuRegistros.add(mnuProducto);
		mnuRegistros.addSeparator();
		mnuRegistros.add(mnuProveedor);
		mnuRegistros.addSeparator();
		mnuRegistros.add(mnuCliente);
		mnuRegistros.addSeparator();
		mnuRegistros.add(mnLocalidad);
		
		mnuPais = new JMenuItem("PAISES");
		mnuPais.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuPais.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuDepartamento = new JMenuItem("DEPARTAMENTOS");
		mnuDepartamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuDepartamento.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		mnuCiudad = new JMenuItem("CIUDADES");
		mnuCiudad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuCiudad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		
		mnLocalidad.add(mnuPais);
		mnLocalidad.addSeparator();
		mnLocalidad.add(mnuDepartamento);
		mnLocalidad.addSeparator();
		mnLocalidad.add(mnuCiudad);
		
		JMenu mnuMovimientos = new JMenu("MOVIMIENTOS");
		mnuMovimientos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovimientos.setMnemonic('M');
		menuBar.add(mnuMovimientos);
		mnuCompra = new JMenuItem("COMPRAS");
		mnuCompra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuCompra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK));
		
		mnuVenta = new JMenuItem("VENTAS");
		mnuVenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuVenta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_MASK));
		mnuMovimientos.add(mnuVenta);
		mnuMovimientos.addSeparator();
		mnuMovimientos.add(mnuCompra);
//		mnuPDV = new JMenuItem("PDV");
//		mnuMovimientos.add(mnuPDV);
		mnuMovimientos.addSeparator();
		
		mnuPresupuesto = new JMenuItem("PRESUPUESTOS");
		mnuPresupuesto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_MASK));
//		
		mnuMovimientos.add(mnuPresupuesto);
		mnuMovimientos.addSeparator();
		
		//mnuMovimientos.addSeparator();
		
		
		mnuDevolucion = new JMenu("DEVOLUCIONES");
		
		mnuMovCuentaAPagarRecibir = new JMenu("CUENTAS A PAGAR Y RECIBIR");
		mnuMovCuentaAPagarRecibir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovCuentaAPagar = new JMenu("CUENTAS A PAGAR");
		mnuMovCuentaAPagar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovCuentaARecibir = new JMenu("CUENTAS A RECIBIR");
		mnuMovCuentaARecibir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovCuentaAPagarRecibir.add(mnuMovCuentaARecibir);
		mnuMovCuentaAPagarRecibir.addSeparator();
		mnuMovCuentaAPagarRecibir.add(mnuMovCuentaAPagar);
		mnuMovCuentaAPagarPagoProveedor = new JMenuItem("PAGO A PROVEEDORES");
		mnuMovCuentaAPagarPagoProveedor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovCuentaAPagar.add(mnuMovCuentaAPagarPagoProveedor);
		mnuMovCuentaARecibirCobroCliente = new JMenuItem("COBRO A CLIENTES");
		mnuMovCuentaARecibirCobroCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovCuentaARecibir.add(mnuMovCuentaARecibirCobroCliente);
		mnuMovimientos.add(mnuMovCuentaAPagarRecibir);
		mnuMovimientos.addSeparator();
		
		mnuMovCaja = new JMenu("CAJAS");
		mnuMovCaja.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovCajaIngreso = new JMenuItem("MOVIMIENTO DE INGRESO DE EFECTIVO");
		mnuMovCajaIngreso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovCajaEgreso = new JMenuItem("MOVIMIENTO DE EGRESO DE EFECTIVO");
		mnuMovCajaEgreso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
		mnuMovimientos.add(mnuDevolucion);
		mnuDevolucionVenta = new JMenuItem("VENTAS");
		mnuDevolucion.add(mnuDevolucionVenta);
		mnuDevolucion.addSeparator();
		mnuDevolucionCompra = new JMenuItem("COMPRAS");
		mnuDevolucion.add(mnuDevolucionCompra);

		mnuMovimientos.addSeparator();
		mnuTransformacion = new JMenuItem("TRANSFORMACION DE PRODUCTOS");
		mnuTransformacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuTransformacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
		mnuMovimientos.add(mnuTransformacion);
		
		mnuFacturacion = new JMenuItem("FACTURACIÓN");
		mnuFacturacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuMovimientos.add(mnuFacturacion);
		
		
		
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
		mnuRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuRelatorios.setMnemonic('L');
		menuBar.add(mnuRelatorios);
		
		mnuInfProductos = new JMenu("PRODUCTOS");
		mnuInfProductos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuRelatorios.add(mnuInfProductos);
		
		mnuInfDatosProductos= new JMenu("DATOS DEL PRODUCTO");
		mnuInfDatosProductos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuInfStockPorDeposito = new JMenuItem("INFORME DE STOCK POR DEPOSITO");
		mnuInfStockPorDeposito.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuInfProductos.add(mnuInfDatosProductos);
		mnuInfDatosProductos.add(mnuInfStockPorDeposito);
		
		mnuInfCuentaAPagarRecibir = new JMenu("CUENTAS A PAGAR Y RECIBIR");
		mnuInfCuentaAPagarRecibir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuInfCuentaAPagar = new JMenu("CUENTAS A PAGAR");
		mnuInfCuentaAPagar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuInfCuentaARecibir = new JMenu("CUENTAS A RECIBIR");
		mnuInfCuentaARecibir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		mnuInfCuentaAPagarRecibir.add(mnuInfCuentaARecibir);
		mnuInfCuentaAPagarRecibir.addSeparator();
		mnuInfCuentaAPagarRecibir.add(mnuInfCuentaAPagar);
		
		mnuInfCuentaAPagarVencimientoProveedor = new JMenuItem("VENCIMIENTO DE MOVIMIENTOS DEL PROVEEDOR");
		mnuInfCuentaAPagarVencimientoProveedor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		//mnuInfCuentaAPagarExtractoProveedor= new JMenuItem("EXTRACTO DE MOVIMIENTOS DEL PROVEEDOR");
		mnuInfCuentaAPagar.add(mnuInfCuentaAPagarVencimientoProveedor);
//		mnuInfCuentaAPagar.addSeparator();
//		mnuInfCuentaAPagar.add(mnuInfCuentaAPagarExtractoProveedor);
		
		mnuInfCuentaARecibirVencimientoCliente= new JMenuItem("VENCIMIENTO DE MOVIMIENTOS DEL CLIENTE");
		mnuInfCuentaARecibirVencimientoCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		//mnuInfCuentaARecibirExtractoCliente= new JMenuItem("EXTRACTO DE MOVIMIENTOS DEL CLIENTE");
		mnuInfCuentaARecibir.add(mnuInfCuentaARecibirVencimientoCliente);
//		mnuInfCuentaARecibir.addSeparator();
//		mnuInfCuentaARecibir.add(mnuInfCuentaARecibirExtractoCliente);
		
		mnuRelatorios.add(mnuInfCuentaAPagarRecibir);
		
		mnuInfEstadoFinanciero = new JMenu("ESTADO FINANCIERO");
		mnuInfEstadoFinanciero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuInfResumenUtilidadProducto = new JMenuItem("RESUMEN DE UTILIDAD DE PRODUCTOS");
		mnuInfResumenUtilidadProducto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuInfEstadoFinanciero.add(mnuInfResumenUtilidadProducto);
		mnuRelatorios.add(mnuInfEstadoFinanciero);
		mnuInfCajas = new JMenu("CAJAS");
		mnuInfCajas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuInfResumenCajas= new JMenuItem("RESUMEN DETALLADO DE INGRESO EGRESO CAJA");
		mnuInfResumenCajas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuInfResumenAgrupadoCajas= new JMenuItem("RESUMEN AGRUPADO DE INGRESO EGRESO");
		mnuInfResumenAgrupadoCajas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		mnuInfCajas.add(mnuInfResumenCajas);
		mnuInfCajas.addSeparator();
		mnuInfCajas.add(mnuInfResumenAgrupadoCajas);
		
		mnuRelatorios.add(mnuInfCajas);
		
//		JMenu mnuUtilidades = new JMenu("UTILIDADES");
//		mnuUtilidades.setMnemonic('U');
//		menuBar.add(mnuUtilidades);
		
		mnConfiguracion = new JMenu("CONFIG");
		mnConfiguracion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
		mntmImpresoras.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mntmImpresoras.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mntmImpresoras);
		mnConfiguracion.addSeparator();
//		mnuCaja = new JMenuItem("CAJAS");
//		mnuCaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
//		mnConfiguracion.add(mnuCaja);
//		mnConfiguracion.addSeparator();
		mnuUsuario = new JMenuItem("USUARIOS");
		mnuUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuUsuario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mnuUsuario);
		mnConfiguracion.addSeparator();
		mnuUsuarioRol = new JMenuItem("USUARIO ROLES");
		mnuUsuarioRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuUsuarioRol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mnuUsuarioRol);
		mnConfiguracion.addSeparator();
		mnuRol = new JMenuItem("ROLES");
		mnuRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuRol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mnuRol);
		mnConfiguracion.addSeparator();
		mnuPermisos = new JMenuItem("PERMISOS");
		mnuPermisos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnuPermisos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnConfiguracion.add(mnuPermisos);
		mnConfiguracion.addSeparator();
		mnuConfig = new JMenuItem("CONFIG. GENERAL");
		mnuConfig.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		mnConfiguracion.add(mnuConfig);
		mnConfiguracion.addSeparator();
		mntmSalir = new JMenuItem("SALIR");
		mntmSalir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
		lblEmpresaNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblEmpresaNombre);
		
		label = new JLabel(" - ");
		panel.add(label);
		
		lblUsuarioNombre = new JLabel("USUARIO NOMBRE");
		lblUsuarioNombre.setFont(new Font("Sylfaen", Font.PLAIN, 14));
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

	public JMenuItem getMnuFacturacion() {
		return mnuFacturacion;
	}

	public void setMnuFacturacion(JMenuItem mnuFacturacion) {
		this.mnuFacturacion = mnuFacturacion;
	}
	
	
}
