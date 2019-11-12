package py.com.prestosoftware.ui.shared;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.CondicionPagoService;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.MonedaService;
import py.com.prestosoftware.domain.services.PedidoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.validations.CompraValidator;
import py.com.prestosoftware.ui.search.CondicionPagoDialog;
import py.com.prestosoftware.ui.search.ConsultaProveedor;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.MonedaDialog;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.table.CompraImportacionTableModel;
import py.com.prestosoftware.ui.table.CompraItemTableModel;
import py.com.prestosoftware.ui.table.PedidoItemTableModel;
import py.com.prestosoftware.ui.transactions.CompraConsignadaPanel;
import py.com.prestosoftware.ui.transactions.CompraImportacionPanel;
import py.com.prestosoftware.ui.transactions.CompraLocalPanel;
import py.com.prestosoftware.ui.transactions.PedidoCompraPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class CompraPanel extends JDialog {	

	private static final long serialVersionUID = 1L;
	
	private PanelCompraInterfaz panelInterfaz;
	
	private CompraItemTableModel itemTableModel;
	private PedidoItemTableModel pedidoItemModel;
    private ConsultaProveedor proveedorDialog;
    private DepositoDialog depositoDialog;
    private MonedaDialog monedaDialog;
    private ProductoDialog productoDialog;
    private CompraImportacionTableModel gastoTableModel;
    
    private CompraService compraService;
    private PedidoService pedidoService;
    private ProveedorService proveedorService;
    private MonedaService monedaService;
    private DepositoService depositoService;
    private ProductoService productoService;
    private CompraValidator compraValidator;
    
    private CompraLocalPanel compraLocal;
    private CompraImportacionPanel compraImportacion;
    private CompraConsignadaPanel compraConsignada;
    private PedidoCompraPanel pedidoCompra;
    private CondicionPagoDialog condicionPagoDialog;
    private CondicionPagoService condicionPagoService;
    private ConfiguracionService configService;

    @Autowired
	public CompraPanel(PanelCompraInterfaz panelInterfaz, CompraItemTableModel itemTableModel,
			CompraImportacionTableModel gastoTableModel, ConsultaProveedor proveedorDialog,
			DepositoDialog depositoDialog, MonedaDialog monedaDialog, ProductoDialog productoDialog,
			CompraService compraService, ProveedorService proveedorService, MonedaService monedaService,
			DepositoService depositoService, ProductoService productoService, 
			CompraValidator compraValidator, PedidoService pedidoService,
			CompraLocalPanel compraPanelLocal, PedidoItemTableModel pedidoItemModel,
			CompraImportacionPanel compraImportacion, CondicionPagoDialog condicionPagoDialog,
			CompraConsignadaPanel compraConsignada, CondicionPagoService condicionPagoService,
			PedidoCompraPanel pedidoCompra, ConfiguracionService configService) {
		this.panelInterfaz = panelInterfaz;
		this.itemTableModel = itemTableModel;
		this.proveedorDialog = proveedorDialog;
		this.depositoDialog = depositoDialog;
		this.monedaDialog = monedaDialog;
		this.productoDialog = productoDialog;
		this.compraService = compraService;
		this.proveedorService = proveedorService;
		this.monedaService = monedaService;
		this.depositoService = depositoService;
		this.productoService = productoService;
		this.compraValidator = compraValidator;
		this.gastoTableModel = gastoTableModel;
		this.pedidoCompra = pedidoCompra;
		this.pedidoItemModel = pedidoItemModel;
		this.compraLocal = compraPanelLocal;
		this.compraImportacion = compraImportacion;
		this.compraConsignada = compraConsignada;
		this.pedidoCompra = pedidoCompra;
		this.condicionPagoDialog = condicionPagoDialog;
		this.condicionPagoService = condicionPagoService;
		this.configService = configService;
		
		setTitle("OPCIONES DE COMPRA");
		setModal(true);
		setBounds(100, 100, 563, 178);
		getContentPane().setLayout(null);
		
		JButton btnFactura = new JButton("LOCAL");
		btnFactura.setFont(new Font("Verdana", Font.BOLD, 14));
		btnFactura.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					panelInterfaz.goToCompraLocal();
					dispose();
					openCompraLocal();
				}
			}
		});
		btnFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInterfaz.goToCompraLocal();
				dispose();
				openCompraLocal();
			}
		});
		btnFactura.setBounds(1, 80, 139, 52);
		getContentPane().add(btnFactura);
		
		JButton btnRemision = new JButton("IMPORTACIÓN");
		btnRemision.setFont(new Font("Verdana", Font.BOLD, 14));
		btnRemision.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					panelInterfaz.goToCompraImportacion();
					dispose();
					openCompraImportacion();
				}
			}
		});
		btnRemision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInterfaz.goToCompraImportacion();
				dispose();
				openCompraImportacion();
			}
		});
		btnRemision.setBounds(141, 80, 139, 52);
		getContentPane().add(btnRemision);
		
		JLabel lblImpresionFactura = new JLabel("Seleccione Compra");
		lblImpresionFactura.setFont(new Font("Verdana", Font.BOLD, 20));
		lblImpresionFactura.setHorizontalAlignment(SwingConstants.CENTER);
		lblImpresionFactura.setBounds(6, 11, 551, 52);
		getContentPane().add(lblImpresionFactura);
		
		JButton btnCancelar = new JButton("CONSIGNADA");
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 14));
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					panelInterfaz.goToCompraConsiganada();
					dispose();
					openCompraConsignada();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInterfaz.goToCompraConsiganada();
				dispose();
				openCompraConsignada();
			}
		});
		btnCancelar.setBounds(281, 80, 139, 52);
		getContentPane().add(btnCancelar);
		
		JButton btnPedido = new JButton("SOLICITUD");
		btnPedido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					openSolicitudCompra();
				}
			}
		});
		btnPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSolicitudCompra();
			}
		});
		btnPedido.setFont(new Font("Verdana", Font.BOLD, 14));
		btnPedido.setBounds(421, 80, 139, 52);
		getContentPane().add(btnPedido);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}
	
	private void openCompraLocal() {
		 compraLocal = new CompraLocalPanel(itemTableModel, proveedorDialog,
				depositoDialog, monedaDialog, productoDialog, compraService, proveedorService, 
				monedaService, depositoService, compraValidator, productoService, 
				condicionPagoDialog, condicionPagoService, configService);
		 compraLocal.setVisible(true);
		 compraLocal.getConfig();
		 compraLocal.clearForm();
		 compraLocal.newCompra();
	}
	
	private void openCompraImportacion() {
		compraImportacion = new CompraImportacionPanel(itemTableModel, gastoTableModel, proveedorDialog,
				depositoDialog, monedaDialog, productoDialog, compraService, proveedorService, 
				monedaService, depositoService, compraValidator, productoService);
		compraImportacion.setVisible(true);
		compraImportacion.newCompra();
		compraImportacion.clearForm();
	}
	
	private void openCompraConsignada() {
		compraConsignada = new CompraConsignadaPanel(itemTableModel, proveedorDialog,
				depositoDialog, monedaDialog, productoDialog, compraService, proveedorService, 
				monedaService, depositoService, compraValidator, productoService);
		compraConsignada.setVisible(true);
		compraConsignada.newCompra();
		compraConsignada.clearForm();
	}
	
	private void openSolicitudCompra() {
		pedidoCompra = new PedidoCompraPanel(pedidoItemModel, proveedorDialog,
				productoDialog, pedidoService, proveedorService, productoService);
		pedidoCompra.setVisible(true);
		pedidoCompra.newCompra();
		pedidoCompra.clearForm();
	}
	
	public void setPanelInterfaz(PanelCompraInterfaz panelInterfaz) {
		this.panelInterfaz = panelInterfaz;
	}
	
	public PanelCompraInterfaz getPanelInterfaz() {
		return panelInterfaz;
	}
	
}
