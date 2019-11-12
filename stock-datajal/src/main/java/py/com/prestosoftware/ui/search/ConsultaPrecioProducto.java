package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.table.ProductoPrecioTableModel;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class ConsultaPrecioProducto extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTable tbPrecios;
	private JScrollPane scrollPane;
	private JLabel lblCodigo;
	private JTextField tfCodigo;
	private JLabel lblDescripcion;
	private JTextField tfDescripcion;
	private JLabel lblGrupo;
	private JTextField tfGrupo;
	private JLabel lblSubgrupo;
	private JTextField tfSubgrupo;
	private JLabel lblMarca;
	private JTextField tfMarca;
	private JLabel lblCtdXCj;
	private JTextField tfCantPorCaja;
	private JLabel lblPeso;
	private JTextField tfPeso;
	private JLabel lblReferencia;
	private JTextField tfReferencia;
	private JLabel lblUltCompra;
	private JTextField tfUltCompra;
	private JLabel lblUltVenta;
	private JTextField tfUltVenta;
	private JLabel lblMedioFob;
	private JTextField tfMedioFob;
	private JLabel lblCostoFob;
	private JTextField tfCostoFob;
	private JLabel lblMedioCif;
	private JLabel lblCostoCif;
	private JTextField tfMedioCif;
	private JTextField tfCostoCif;
	private JPanel panel;
	private JTextField tfTamanho;
	private JLabel lblTamao;
	
//	private ProductoService service; ProductoService service, this.service = service;
	private ProductoPrecioTableModel tableModel;
	private JButton btnCerrar;

	@Autowired
	public ConsultaPrecioProducto(ProductoPrecioTableModel tableModel) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setType(Type.UTILITY);
		setResizable(false);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}
		});
		setUndecorated(true);
		this.tableModel = tableModel;
		
		this.setTitle("CONSULTA PRECIOS");
		this.setSize(737, 395);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 105, 725, 160);
		getContentPane().add(scrollPane);
		
		tbPrecios = new JTable(tableModel);
		tbPrecios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbPrecios.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
//					aceptar();
				} else if (e.getKeyCode()==KeyEvent.VK_INSERT) {
//					aceptar();
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					 //tfBuscador.requestFocus();
				}
			}
		});
		scrollPane.setViewportView(tbPrecios);
		
		lblUltCompra = new JLabel("ULT. COMPRA");
		lblUltCompra.setBounds(6, 289, 95, 26);
		getContentPane().add(lblUltCompra);
		
		tfUltCompra = new JTextField();
		tfUltCompra.setEditable(false);
		tfUltCompra.setColumns(10);
		tfUltCompra.setBounds(102, 289, 130, 26);
		getContentPane().add(tfUltCompra);
		
		lblUltVenta = new JLabel("ULT. VENTA");
		lblUltVenta.setBounds(6, 327, 95, 26);
		getContentPane().add(lblUltVenta);
		
		tfUltVenta = new JTextField();
		tfUltVenta.setEditable(false);
		tfUltVenta.setColumns(10);
		tfUltVenta.setBounds(102, 327, 130, 26);
		getContentPane().add(tfUltVenta);
		
		lblMedioFob = new JLabel("MEDIO FOB");
		lblMedioFob.setBounds(244, 289, 95, 26);
		getContentPane().add(lblMedioFob);
		
		tfMedioFob = new JTextField();
		tfMedioFob.setEditable(false);
		tfMedioFob.setColumns(10);
		tfMedioFob.setBounds(340, 289, 130, 26);
		getContentPane().add(tfMedioFob);
		
		lblCostoFob = new JLabel("COSTO FOB");
		lblCostoFob.setBounds(244, 327, 95, 26);
		getContentPane().add(lblCostoFob);
		
		tfCostoFob = new JTextField();
		tfCostoFob.setEditable(false);
		tfCostoFob.setColumns(10);
		tfCostoFob.setBounds(340, 327, 130, 26);
		getContentPane().add(tfCostoFob);
		
		lblMedioCif = new JLabel("MEDIO CIF");
		lblMedioCif.setBounds(505, 289, 95, 26);
		getContentPane().add(lblMedioCif);
		
		lblCostoCif = new JLabel("COSTO CIF");
		lblCostoCif.setBounds(505, 327, 95, 26);
		getContentPane().add(lblCostoCif);
		
		tfMedioCif = new JTextField();
		tfMedioCif.setEditable(false);
		tfMedioCif.setColumns(10);
		tfMedioCif.setBounds(601, 289, 130, 26);
		getContentPane().add(tfMedioCif);
		
		tfCostoCif = new JTextField();
		tfCostoCif.setEditable(false);
		tfCostoCif.setColumns(10);
		tfCostoCif.setBounds(601, 327, 130, 26);
		getContentPane().add(tfCostoCif);
		
		panel = new JPanel();
		panel.setBounds(6, 6, 725, 98);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lblCodigo = new JLabel("COD.:");
		lblCodigo.setBounds(6, 5, 61, 26);
		panel.add(lblCodigo);
		
		tfCodigo = new JTextField();
		tfCodigo.setEditable(false);
		tfCodigo.setBounds(66, 5, 80, 26);
		panel.add(tfCodigo);
		tfCodigo.setColumns(10);
		
		lblReferencia = new JLabel("REF.:");
		lblReferencia.setBounds(6, 36, 61, 26);
		panel.add(lblReferencia);
		
		tfReferencia = new JTextField();
		tfReferencia.setEditable(false);
		tfReferencia.setBounds(66, 36, 140, 26);
		panel.add(tfReferencia);
		tfReferencia.setColumns(10);
		
		tfPeso = new JTextField();
		tfPeso.setEditable(false);
		tfPeso.setBounds(66, 67, 140, 26);
		panel.add(tfPeso);
		tfPeso.setColumns(10);
		
		lblPeso = new JLabel("PESO:");
		lblPeso.setBounds(6, 68, 61, 26);
		panel.add(lblPeso);
		
		lblDescripcion = new JLabel("DESCRI:");
		lblDescripcion.setBounds(259, 5, 61, 26);
		panel.add(lblDescripcion);
		
		tfDescripcion = new JTextField();
		tfDescripcion.setEditable(false);
		tfDescripcion.setBounds(322, 5, 140, 26);
		panel.add(tfDescripcion);
		tfDescripcion.setColumns(10);
		
		tfCantPorCaja = new JTextField();
		tfCantPorCaja.setEditable(false);
		tfCantPorCaja.setBounds(322, 36, 140, 26);
		panel.add(tfCantPorCaja);
		tfCantPorCaja.setColumns(10);
		
		lblCtdXCj = new JLabel("CTD X CJ:");
		lblCtdXCj.setBounds(259, 36, 61, 26);
		panel.add(lblCtdXCj);
		
		lblGrupo = new JLabel("GRUPO:");
		lblGrupo.setBounds(507, 5, 61, 26);
		panel.add(lblGrupo);
		
		tfGrupo = new JTextField();
		tfGrupo.setEditable(false);
		tfGrupo.setBounds(579, 5, 140, 26);
		panel.add(tfGrupo);
		tfGrupo.setColumns(10);
		
		lblSubgrupo = new JLabel("SUBGRUPO:");
		lblSubgrupo.setBounds(488, 36, 80, 26);
		panel.add(lblSubgrupo);
		
		tfSubgrupo = new JTextField();
		tfSubgrupo.setEditable(false);
		tfSubgrupo.setBounds(579, 36, 140, 26);
		panel.add(tfSubgrupo);
		tfSubgrupo.setColumns(10);
		
		lblMarca = new JLabel("MARCA:");
		lblMarca.setBounds(507, 68, 61, 26);
		panel.add(lblMarca);
		
		tfMarca = new JTextField();
		tfMarca.setEditable(false);
		tfMarca.setBounds(579, 67, 140, 26);
		panel.add(tfMarca);
		tfMarca.setColumns(10);
		
		tfTamanho = new JTextField();
		tfTamanho.setEditable(false);
		tfTamanho.setColumns(10);
		tfTamanho.setBounds(322, 67, 140, 26);
		panel.add(tfTamanho);
		
		lblTamao = new JLabel("TAMAÑO");
		lblTamao.setBounds(259, 68, 61, 26);
		panel.add(lblTamao);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCerrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					dispose();
				}
			}
		});
		btnCerrar.setBounds(614, 360, 117, 29);
		getContentPane().add(btnCerrar);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		
		btnCerrar.requestFocus();
	}

	public void setDataProducto(Producto p) {
		clearInputs();
		
		tfCodigo.setText(String.valueOf(p.getId()));
		tfDescripcion.setText(p.getDescripcion());
		tfReferencia.setText(p.getReferencia());
		tfMarca.setText(p.getMarca().getNombre());
		tfGrupo.setText(p.getGrupo().getNombre());
		tfSubgrupo.setText(p.getSubgrupo().getNombre());
		tfTamanho.setText(p.getTamanho().getNombre());
		tfCantPorCaja.setText(FormatearValor.doubleAString(p.getCantidadPorCaja()));
		tfPeso.setText(FormatearValor.doubleAString(p.getPeso()));
		tfCostoCif.setText("");
		tfCostoFob.setText("");
		tfMedioCif.setText("");
		tfMedioFob.setText("");
		tfUltCompra.setText("");
		tfUltVenta.setText("");
		//tableModel.addEntities(p.getPrecios());
	}
	
	private void clearInputs() {
		tfCodigo.setText("");
		tfDescripcion.setText("");
		tfReferencia.setText("");
		tfMarca.setText("");
		tfGrupo.setText("");
		tfSubgrupo.setText("");
		tfTamanho.setText("");
		tfCantPorCaja.setText("");
		tfPeso.setText("");
		tfCostoCif.setText("");
		tfCostoFob.setText("");
		tfMedioCif.setText("");
		tfMedioFob.setText("");
		tfUltCompra.setText("");
		tfUltVenta.setText("");
		
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}
	}
}
