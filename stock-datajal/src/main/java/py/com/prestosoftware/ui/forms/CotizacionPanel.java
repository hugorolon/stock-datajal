package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cotizacion;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.MonedaComboBoxModel;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@Component
public class CotizacionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblCodigo, lblMoneda;
	private JFormattedTextField tfFecha;
    private JCheckBox chActivo;
    private JPanel panel, panel_1;
    private JLabel lblCompra;
    private JLabel lblVenta;
    private JTextField tfCompra, tfVenta, tfId;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JComboBox<Moneda> cbMoneda;
    
    private MonedaComboBoxModel monedaComboBoxModel;

    @Autowired
    public CotizacionPanel(MonedaComboBoxModel depComboBox) {
    	this.monedaComboBoxModel = depComboBox;
    	this.setSize(450, 290);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        
        panel = new JPanel();
        add(panel);
        
        JLabel lblFecha = new JLabel("Fecha:");
        
        tfFecha = new JFormattedTextField(getFormatoFecha());
        tfFecha.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbMoneda.requestFocus();
				}
        	}
        });
        tfFecha.setText(Fechas.formatoDDMMAAAA(new java.util.Date()));
        tfFecha.setColumns(10);
        
        chActivo = new JCheckBox();
        chActivo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        chActivo.setSelected(true);
        JLabel lblActivo = new JLabel("Activo:");
        
        tfId = new JTextField();
    
        lblMoneda = new JLabel("Moneda:");
        cbMoneda = new JComboBox<Moneda>(monedaComboBoxModel);
        cbMoneda.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfCompra.requestFocus();
				}
        	}
        });
        
        lblCodigo = new JLabel("Codigo:");
        
        lblCompra = new JLabel("Compra:");
        
        lblVenta = new JLabel("Venta:");
        
        tfCompra = new JTextField();
        tfCompra.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfVenta.requestFocus();
				}
        		
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCompra.setColumns(10);
        
        tfVenta = new JTextField();
        tfVenta.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfVenta.setColumns(10);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addGap(65)
        					.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addGap(65)
        					.addComponent(tfFecha, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
        				.addComponent(lblFecha, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblMoneda, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addGap(65)
        					.addComponent(cbMoneda, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_panel.createSequentialGroup()
        							.addGap(65)
        							.addComponent(tfCompra, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
        						.addComponent(lblCompra, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
        					.addGap(19)
        					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblVenta, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        						.addGroup(gl_panel.createSequentialGroup()
        							.addGap(52)
        							.addComponent(tfVenta, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addGap(65)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(9)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(tfFecha, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblFecha, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblMoneda, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(cbMoneda, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(tfCompra, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addGap(2)
        					.addComponent(lblCompra, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        				.addComponent(lblVenta, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfVenta, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
        );
        panel.setLayout(gl_panel);
        
        panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        panel_1.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        panel_1.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        panel_1.add(btnCerrar);
    }
    
    public void setFormValue(Cotizacion cotizacion) {
    	tfId.setText(cotizacion.getId() + "");
    	tfFecha.setText(String.valueOf(cotizacion.getFecha()));
    	tfCompra.setText(String.valueOf(cotizacion.getValorCompra()));
    	tfVenta.setText(String.valueOf(cotizacion.getValorVenta()));
    	chActivo.setSelected(cotizacion.getActivo() == 1 ? true : false);
    	monedaComboBoxModel.setSelectedItem(cotizacion.getMoneda());
    }

    public Cotizacion getFormValue() {
    	Cotizacion cotizacion = new Cotizacion();
        
        if (!tfId.getText().isEmpty()) {
        	cotizacion.setId(Long.parseLong(tfId.getText()));
		}
        
        cotizacion.setFecha(Fechas.stringDDMMAAAAADateSQL(tfFecha.getText()));
        cotizacion.setMoneda(monedaComboBoxModel.getSelectedItem());
        cotizacion.setValorCompra(FormatearValor.stringADouble(tfCompra.getText()));
        cotizacion.setValorVenta(FormatearValor.stringADouble(tfVenta.getText()));
        cotizacion.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return cotizacion;
    }

    public void clearForm() {
    	tfId.setText("");
        tfFecha.setText(Fechas.dateUtilAStringDDMMAAAA(new java.util.Date()));
        cbMoneda.setSelectedIndex(0);
        tfCompra.setText("");
        tfVenta.setText("");
    	chActivo.setSelected(true);
    	
    }
    
    public JButton getBtnGuardar() {
		return btnGuardar;
	}
    
    public JButton getBtnCancelar() {
		return btnCancelar;
	}
    
    public JButton getBtnCerrar() {
		return btnCerrar;
	}
	
	public JTextField getTfId() {
		return tfId;
	}
	
	public JFormattedTextField getTfFecha() {
		return tfFecha;
	}
	
	private MaskFormatter formatoFecha;
	
	private MaskFormatter getFormatoFecha() {
		try {
			if (formatoFecha == null) {
				formatoFecha = new MaskFormatter("##/##/####");
				formatoFecha.setPlaceholderCharacter('_');
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatoFecha;
	}

	public void setNewCotizacion(long id) {
		tfId.setText(String.valueOf(id));
	}

	public void setData(Cotizacion c) {
		tfId.setText(String.valueOf(c.getId()));
		tfFecha.setText(Fechas.formatoDDMMAAAA(c.getFecha()));
		tfCompra.setText(FormatearValor.doubleAString(c.getValorCompra()));
		tfVenta.setText(FormatearValor.doubleAString(c.getValorVenta()));
		tfCompra.requestFocus();
	}
}