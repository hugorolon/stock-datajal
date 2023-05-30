package py.com.prestosoftware.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.domain.services.MovimientoIngresoService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.table.MovimientoIngresoTableModel;

@Component
public class MovimientoIngresoDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private MovimientoIngresoService service;
	private MovimientoIngresoTableModel tableModel;
	private MovimientoIngresoInterfaz interfaz;
	
	
	private List<Object[]> listaMovimientoIngresos;
	private JXDatePicker dtFecha;
	private JLabel lblFecha;

	@Autowired
	public MovimientoIngresoDialog(MovimientoIngresoService service, MovimientoIngresoTableModel tableModel) throws ParseException {
		this.service = service;
		this.tableModel = tableModel;
		
		this.setTitle("Buscador de Movimiento Ingreso");
		this.setSize(600, 300);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		
		lblFecha = new JLabel("Fecha");
		pnlBuscador.add(lblFecha);
		
		dtFecha = new JXDatePicker();
		pnlBuscador.add(dtFecha);
		dtFecha.setFormats("dd/MM/yyyy");
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loadMovimientoIngresos(dtFecha.getDate());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
					try {
						loadMovimientoIngresos(dtFecha.getDate());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		pnlBuscador.add(btnBuscar);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					try {
						aceptar();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} 
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel pnlBotonera = new JPanel();
		getContentPane().add(pnlBotonera, BorderLayout.SOUTH);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						aceptar();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					aceptar();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		pnlBotonera.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					dispose();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlBotonera.add(btnCancelar);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		dtFecha.setDate(new Date());
		loadMovimientoIngresos(new Date());	
	}
	
	public void loadMovimientoIngresos(Date fecha) throws ParseException {
		//SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		//Date hoy = sdf.parse("2022-12-07");// new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-07");
		//movimientoIngresos.clear();
		listaMovimientoIngresos = service.findByDateObjects(fecha);
        tableModel.clear();
        tableModel.addEntities(listaMovimientoIngresos);
        /*try {
        	for (Iterator iterator = listaIngresos.iterator(); iterator.hasNext();) {
    			Object[] objects = (Object[]) iterator.next();
    			MovimientoIngreso me= new MovimientoIngreso();
    			me.setMinNumero(Integer.valueOf(objects[0].toString()));
    			me.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(objects[1].toString())); 
    			me.setMinCaja(Integer.parseInt(objects[2].toString()));
    			me.setMinDocumento(objects[3].toString());
    			me.setMinEntidad(objects[4].toString());
    			me.setMinNumero(Integer.parseInt(objects[5].toString()));
    			movimientoIngresos.add(me);
    		}	
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
    }
	
	public MovimientoIngresoInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(MovimientoIngresoInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	private void aceptar() throws ParseException {
		for (Integer c : table.getSelectedRows()) {
			MovimientoIngreso me = new MovimientoIngreso();
			Object[] objects= listaMovimientoIngresos.get(c);
			me.setMinNumero(Integer.valueOf(objects[0].toString()));
			me.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(objects[1].toString())); 
			me.setMinCaja(Integer.parseInt(objects[2].toString()));
			me.setMinDocumento(objects[3].toString());
			me.setMinEntidad(objects[4].toString());
			me.setMinSituacion(Integer.valueOf(objects[6].toString()));
			//me.setMinNumero(FormatearValor.desformatearValor(objects[5].toString()).intValue());
			interfaz.getEntity(me);         
	    }
		dispose();
	}

	public JXDatePicker getDtFecha() {
		return dtFecha;
	}

	public void setDtFecha(JXDatePicker dtFecha) {
		this.dtFecha = dtFecha;
	}
	

}
