package py.com.prestosoftware.ui.helpers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;

public class CuotaTransacctionPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JButton bCancelar = null;

	private JTextField tfNumero = null;

	private JButton bCerrar = null;
	
	public Integer monedaDecimales=0;
	
	private DefaultTableModel dtmTableCuotas = new DefaultTableModel(null, new String[] {
			"SECUENCIA","NRO","VENCIMIENTO","MONTO"});

	private JTextField tfTotalGeneral = null;

	private JLabel jLabel4 = null;

	private JFormattedTextField tfFecha = null;

	private MaskFormatter formatoFecha = null;

	private JLabel jLabel9 = null;

	private JLabel labelVenci = null;

	private JFormattedTextField tfVencimiento = null;

	private MaskFormatter formatoVencimiento = null;

	private JLabel jLabel13 = null;

	private JTextField tfCuotaCantidad = null;

	private JLabel jLabel14 = null;

	private JTextField tfLapso = null;

	private JLabel jLabel15 = null;

	private JScrollPane jScrollPaneCuotas = null;

	public JTable jTableCuotas = null;//

	private JButton bAceptar = null;

	private JTextField tfTotalCuotas = null;

	private JLabel jLabel1 = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;
	
	public String numero="";
	
	public Integer operacionTipoCodigo=0;  //  @jve:decl-index=0:
	
	public String fecha="";
	
	public String totalGeneral="";  //  @jve:decl-index=0:
	
	public String cuotasCantidad="";
	
	public String lapso="";
	
	public String vencimiento="";
	
	public String totalCuotas="";

	private JLabel lbCuotas = null;

	private JLabel lbLapso = null;

	private JLabel lbVencimiento = null;

	public CuotaTransacctionPanel() {
		super();
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setBounds(new Rectangle(0, 0, 270, 535));
		this.setContentPane(getJContentPane());
		this.setTitle("GENERADOR DE CUOTAS");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				cerrar();
			}
		});
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		cancelar();
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbVencimiento = new JLabel();
			lbVencimiento.setBounds(new Rectangle(188, 132, 20, 16));
			lbVencimiento.setForeground(Color.red);
			lbVencimiento.setText("(*)");
			lbVencimiento.setVisible(false);
			lbLapso = new JLabel();
			lbLapso.setBounds(new Rectangle(156, 107, 20, 16));
			lbLapso.setForeground(Color.red);
			lbLapso.setText("(*)");
			lbLapso.setVisible(false);
			lbCuotas = new JLabel();
			lbCuotas.setBounds(new Rectangle(130, 82, 20, 16));
			lbCuotas.setForeground(Color.red);
			lbCuotas.setText("(*)");
			lbCuotas.setVisible(false);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(3, 448, 82, 23));
			jLabel1.setText("Total");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(128, 104, 27, 23));
			jLabel15.setForeground(new Color(204, 102, 0));
			jLabel15.setText("dias");
			jLabel15.setFont(new Font("Monotype Corsiva", Font.BOLD, 12));
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(3, 104, 97, 23));
			jLabel14.setText("Lapso");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(3, 79, 97, 23));
			jLabel13.setText("Cuotas");
			labelVenci = new JLabel();
			labelVenci.setBounds(new Rectangle(3, 129, 97, 23));
			labelVenci.setText("Vencimiento Ini.");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(3, 54, 97, 23));
			jLabel9.setText("Total General");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(3, 29, 97, 23));
			jLabel4.setText("Fecha");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(3, 3, 97, 23));
			jLabel.setText("Numero");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getBCancelar(), null);
			jContentPane.add(getTfNumero(), null);
			jContentPane.add(getBCerrar(), null);
			jContentPane.add(getTfTotalGeneral(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfFecha(), null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(labelVenci, null);
			jContentPane.add(getTfVencimiento(), null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getTfCuotaCantidad(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getTfLapso(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getBAceptar(), null);
			jContentPane.add(getTfTotalCuotas(), null);
			jContentPane.add(getJScrollPaneCuotas(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(lbCuotas, null);
			jContentPane.add(lbLapso, null);
			jContentPane.add(lbVencimiento, null);
		}
		return jContentPane;
	}

	private JButton getBCancelar() {
		if (bCancelar == null) {
			bCancelar = new JButton();
			bCancelar.setBounds(new Rectangle(93, 475, 85, 26));
			bCancelar.setText("Cancelar");
			bCancelar.setMnemonic(KeyEvent.VK_C);
			bCancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelar();
				}
			});
			bCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						cancelar();
					}
				}
			});
		}
		return bCancelar;
	}

	private JTextField getTfNumero() {
		if (tfNumero == null) {
			tfNumero = new JTextField();
			tfNumero.setBounds(new Rectangle(101, 3, 47, 23));
			tfNumero.setEnabled(false);
			tfNumero.setHorizontalAlignment(JTextField.RIGHT);
		}
		return tfNumero;
	}
	private void habilitarTextos(Boolean estado) {
		tfCuotaCantidad.setEnabled(estado);
		tfLapso.setEnabled(estado);
		tfVencimiento.setEnabled(estado);
	}
	private void cancelar() {
		cargarCampos();
		limpiarTextos();
		habilitarTextos(true);
		visualizarEtiquetas(true);
		cuotasCantidad="1";
		vencimiento="";
		totalCuotas="";
		while (dtmTableCuotas.getRowCount() > 0) {
			dtmTableCuotas.removeRow(0);
		}
		tfCuotaCantidad.requestFocus();
	}
	private Boolean esValidoCamposDetalle() {
		if(dtmTableCuotas.getRowCount()==0){
			JOptionPane.showMessageDialog(null, "Cargue las cuotas");
			tfVencimiento.requestFocus();
			return false;
		}
//		String fechaActual=Fechas.formatoDDMMAAAA(new Date());
//		String vencimiento=tfVencimiento.getText();
//		Integer diferenciaDias=Fechas.diferenciaFechas(fechaActual,vencimiento);
//		diferenciaDias--;
//		Date vencimientoAux=Fechas.restarFecha(1, 0, 0, vencimiento);
//		//ver si es domingo y de acuerdo a eso restar uno menos otra vez, y comparar
//		System.out.println("dif:"+diferenciaDias+"-lapso:"+lapso);
//		Integer lapso=Integer.valueOf(this.lapso);
//		if(diferenciaDias>lapso){
//			JOptionPane.showMessageDialog(null, "La fecha excede al lapso permitido");
//			tfVencimiento.requestFocus();
//			return false;
//		}
//		String vencimientoSiguiente;
//		String vencimientoAnterior=Fechas.formatoDDMMAAAA(new Date());
//		for (Integer fila = 0; fila < jTableCuotas.getRowCount(); fila++) {
//			vencimientoSiguiente=String.valueOf(jTableCuotas.getValueAt(fila, 2));
//			diferenciaDias=Fechas.diferenciaFechas(vencimientoAnterior, vencimientoSiguiente);
//			System.out.println(diferenciaDias);
//		}
		if(!tfTotalCuotas.getText().equals(tfTotalGeneral.getText())){
			JOptionPane.showMessageDialog(null, "No coincide el total de las cuotas con el total general");
			forzarFocoGrilla(0, 3);
			return false;
		}
		return true;
	}

	private JButton getBCerrar() {
		if (bCerrar == null) {
			bCerrar = new JButton();
			bCerrar.setBounds(new Rectangle(185, 475, 71, 26));
			bCerrar.setText("Cerrar");
			bCerrar.setMnemonic(KeyEvent.VK_E);
			bCerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
			bCerrar.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						cerrar();
					}
				}
			});
		}
		return bCerrar;
	}

	private void visualizarEtiquetas(Boolean estado) {
		lbCuotas.setVisible(estado);
		lbLapso.setVisible(estado);
		lbVencimiento.setVisible(estado);
	}

	private void eventoTeclado(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			dispose();
		}
	}
	private JTextField getTfTotalGeneral() {
		if (tfTotalGeneral == null) {
			tfTotalGeneral = new JTextField();
			tfTotalGeneral.setBounds(new Rectangle(101, 54, 85, 23));
			tfTotalGeneral.setText("0");
			tfTotalGeneral.setHorizontalAlignment(JTextField.RIGHT);
			tfTotalGeneral.setForeground(new Color(204, 102, 0));
			tfTotalGeneral.setEnabled(false);
		}
		return tfTotalGeneral;
	}
	private void insertarValoresCuota(Integer cuotaNumero,String cuotaVencimiento,BigDecimal cuotaMonto) {
		Object[] campos = {
				0,
				cuotaNumero,
				cuotaVencimiento,
				FormatearValor.bigDecimalAStringDosDecimales(cuotaMonto)
				};
		dtmTableCuotas.addRow(campos);
	}

	private void limpiarTextos() {
		tfCuotaCantidad.setText("1");
		tfLapso.setText("30");
		tfVencimiento.setText("");
	}
	private void ocultarColumnaCuotas(Integer columna) {
		jTableCuotas.getColumnModel().getColumn(columna).setMaxWidth(0);
		jTableCuotas.getColumnModel().getColumn(columna).setMinWidth(0);
		jTableCuotas.getTableHeader().getColumnModel().getColumn(columna).setMaxWidth(0);
		jTableCuotas.getTableHeader().getColumnModel().getColumn(columna).setMinWidth(0);
	}

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

	private JFormattedTextField getTfFecha() {
		if (tfFecha == null) {
			tfFecha = new JFormattedTextField(getFormatoFecha());
			tfFecha.setBounds(new Rectangle(101, 29, 85, 23));
			tfFecha.setFont(new Font("Dialog", Font.PLAIN, 12));
			tfFecha.setColumns(8);
			tfFecha.setHorizontalAlignment(JTextField.CENTER);
			tfFecha.setEnabled(false);
		}
		return tfFecha;
	}

	private MaskFormatter getFormatoVencimiento() {
		try {
			if (formatoVencimiento == null) {
				formatoVencimiento = new MaskFormatter("##/##/####");
				formatoVencimiento.setPlaceholderCharacter('_');
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatoVencimiento;
	}

	private JFormattedTextField getTfVencimiento() {
		if (tfVencimiento == null) {
			tfVencimiento = new JFormattedTextField(getFormatoVencimiento());
			tfVencimiento.setBounds(new Rectangle(101, 129, 85, 23));
			tfVencimiento.setFont(new Font("Dialog", Font.PLAIN, 12));
			tfVencimiento.setColumns(8);
			tfVencimiento.setHorizontalAlignment(JTextField.CENTER);
			tfVencimiento.setEnabled(false);
			tfVencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						bAceptar.requestFocus();
					}
				}
			});
			tfVencimiento.addFocusListener(new java.awt.event.FocusAdapter() {   
				public void focusLost(java.awt.event.FocusEvent e) {    
					if(FechaAutomatica.calcular(tfVencimiento.getText())!=null){
						tfVencimiento.setText(FechaAutomatica.calcular(tfVencimiento.getText()));
					}
					tfVencimiento.setText(Fechas.formatoDDMMAAAA(Fechas.domingos(tfVencimiento.getText())));
					generarCuotas();
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					tfVencimiento.selectAll();
				}
			});
		}
		return tfVencimiento;
	}
	private JTextField getTfCuotaCantidad() {
		if (tfCuotaCantidad == null) {
			tfCuotaCantidad = new JTextField();
			tfCuotaCantidad.setBounds(new Rectangle(101, 79, 27, 23));
			tfCuotaCantidad.setHorizontalAlignment(JTextField.RIGHT);
			tfCuotaCantidad.setEnabled(false);
			tfCuotaCantidad.addKeyListener(new java.awt.event.KeyAdapter() {   
				public void keyTyped(java.awt.event.KeyEvent e) {    
					Util.validateNumero(e);
				}
				public void keyPressed(java.awt.event.KeyEvent e) {
					eventoTeclado(e);
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						tfLapso.requestFocus();
					}
				}
			});
			tfCuotaCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {    
					Integer cantidad=!tfCuotaCantidad.getText().equals("")?Integer.valueOf(tfCuotaCantidad.getText()):1;
					tfCuotaCantidad.setText(String.valueOf(cantidad));
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					tfCuotaCantidad.selectAll();
				}
			});
		}
		return tfCuotaCantidad;
	}

	private JTextField getTfLapso() {
		if (tfLapso == null) {
			tfLapso = new JTextField();
			tfLapso.setBounds(new Rectangle(101, 104, 27, 23));
			tfLapso.setHorizontalAlignment(JTextField.RIGHT);
			tfLapso.setEnabled(false);
			tfLapso.addKeyListener(new java.awt.event.KeyAdapter() {   
				public void keyTyped(java.awt.event.KeyEvent e) {    
					Util.validateNumero(e);
				}
				public void keyPressed(java.awt.event.KeyEvent e) {
					eventoTeclado(e);
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						tfVencimiento.requestFocus();
					}
				}
			});
			tfLapso.addFocusListener(new java.awt.event.FocusAdapter() {   
				public void focusLost(java.awt.event.FocusEvent e) {    
					calcularPrimerVencimiento();
				}
				public void focusGained(java.awt.event.FocusEvent e) {
					tfLapso.selectAll();
				}
			});
		}
		return tfLapso;
	}
	private void calcularPrimerVencimiento() {
		Integer lapso=!tfLapso.getText().equals("")?Integer.valueOf(tfLapso.getText()):30;
		tfLapso.setText(String.valueOf(lapso));
		String fecha=Fechas.formatoDDMMAAAA(Fechas.sumarFecha(lapso, 0, 0, tfFecha.getText()));
		tfVencimiento.setText(Fechas.formatoDDMMAAAA(Fechas.domingos(fecha)));
	}

	private JScrollPane getJScrollPaneCuotas() {
		if (jScrollPaneCuotas == null) {
			jScrollPaneCuotas = new JScrollPane();
			jScrollPaneCuotas.setBounds(new Rectangle(3, 170, 257, 260));
			jScrollPaneCuotas.setViewportView(getJTableCuotas());
		}
		return jScrollPaneCuotas;
	}

	@SuppressWarnings("serial")
	private JTable getJTableCuotas() {
		if (jTableCuotas == null) {
			jTableCuotas = new JTable(dtmTableCuotas){
		        public boolean isCellEditable(int fila, int columna) {
		            return true;
		        }
		    };
		    jTableCuotas.setAutoCreateRowSorter(true);
			jTableCuotas.addKeyListener(new java.awt.event.KeyAdapter() {
	            @Override
	            public void keyReleased(java.awt.event.KeyEvent e) {//released, es tambien cuando entra
	                cambioCelda();
	            }
	        });
			ocultarColumnaCuotas(0);  
			tipoColumna();
	        tamanhoTablaFijo();
	        altura();
	        bloquearDesplazamientoColumnas();
		}
		return jTableCuotas;
	}

	public void generarCuotas() {
		while (dtmTableCuotas.getRowCount() > 0) {
			dtmTableCuotas.removeRow(0);
		}
		if(esValidoCamposCabecera()){
			Integer cuotaNumero=1;
			Integer cuotasCantidad=Integer.valueOf(tfCuotaCantidad.getText());
			Integer lapso=Integer.valueOf(tfLapso.getText());
			String cuotaVencimiento=tfVencimiento.getText();
			String cuotaVencimientoAux=cuotaVencimiento;
			BigDecimal total=FormatearValor.stringABigDecimal(tfTotalGeneral.getText());
			BigDecimal cuotaMonto=total.divide(new BigDecimal(String.valueOf(cuotasCantidad)), RoundingMode.HALF_UP);
			BigDecimal cuotaMontoAux=BigDecimal.ZERO;
			while(cuotaNumero<=cuotasCantidad){
				cuotaMontoAux=cuotaMonto.multiply(new BigDecimal(String.valueOf(cuotasCantidad)), MathContext.DECIMAL64);
				cuotaMontoAux=total.subtract(cuotaMontoAux);
				cuotaMontoAux=cuotaMontoAux.compareTo(BigDecimal.ZERO)>0?cuotaMonto.add(cuotaMontoAux):cuotaMonto.add(cuotaMontoAux);
				cuotaMonto=cuotaNumero==cuotasCantidad?cuotaMontoAux:cuotaMonto;
				cuotaVencimiento=Fechas.formatoDDMMAAAA(Fechas.domingos(cuotaVencimiento));
				insertarValoresCuota(cuotaNumero, cuotaVencimiento, cuotaMonto);
				cuotaVencimientoAux=Fechas.formatoDDMMAAAA(Fechas.sumarFecha(lapso, 0, 0, cuotaVencimientoAux));//para que calcule a partir de la fecha sin el domingo
				cuotaVencimiento=cuotaVencimientoAux;
				cuotaNumero++;
			}
		}
		tfTotalCuotas.setText(tfTotalGeneral.getText());
	}

	private JButton getBAceptar() {
		if (bAceptar == null) {
			bAceptar = new JButton();
			bAceptar.setMnemonic(KeyEvent.VK_A);
			bAceptar.setBounds(new Rectangle(7, 475, 79, 26));
			bAceptar.setText("Aceptar");
			bAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(esValidoCamposDetalle()){
						aceptar();
					}
				}
			});
			bAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						if(esValidoCamposDetalle()){
							aceptar();
						}
					}
				}
			});
		}
		return bAceptar;
	}
	private JTextField getTfTotalCuotas() {
		if (tfTotalCuotas == null) {
			tfTotalCuotas = new JTextField();
			tfTotalCuotas.setText("0");
			tfTotalCuotas.setEditable(false);
			tfTotalCuotas.setHorizontalAlignment(JTextField.RIGHT);
			tfTotalCuotas.setBounds(new Rectangle(87, 448, 90, 23));
			tfTotalCuotas.setForeground(new Color(204, 102, 0));
		}
		return tfTotalCuotas;
	}
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBounds(new Rectangle(3, 155, 257, 9));
			jPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
		}
		return jPanel;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setBounds(new Rectangle(4, 437, 256, 9));
			jPanel1.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
		}
		return jPanel1;
	}
	public void cargarCampos() {
		tfNumero.setText(numero);
		tfFecha.setText(fecha);
		tfTotalGeneral.setText(totalGeneral);
		tfCuotaCantidad.setText(cuotasCantidad);
		tfLapso.setText(lapso);
		tfVencimiento.setText(vencimiento);
	}
	private void aceptar() {
		cuotasCantidad=tfCuotaCantidad.getText();
		lapso=tfLapso.getText();
		vencimiento=tfVencimiento.getText();
		totalCuotas=tfTotalCuotas.getText();
		dispose();
	}
	private void cerrar() {
		Integer respuesta=JOptionPane.showConfirmDialog(null, "Proceso pendiente de confirmacion!, desea cerrar el formulario por el momento?");
		if(respuesta==0){
			dispose();
		}
	}
	private void cambioCelda() {
		if (!jTableCuotas.isEditing() && jTableCuotas.editCellAt(jTableCuotas.getSelectedRow(), jTableCuotas.getSelectedColumn())) {
        	jTableCuotas.getEditorComponent().requestFocusInWindow();  // obligamos que la celda reciba el foco
        }
		Integer cantidadFilas=jTableCuotas.getRowCount();
        Integer fila = jTableCuotas.getSelectedRow();
        if(jTableCuotas.getSelectedColumn()==1){
        	forzarFocoGrilla(fila-1, 2);
        }else if(jTableCuotas.getSelectedColumn()==2 && fila>0){//se a�ade la fila=0 debido a un error que ocurre
		    forzarFocoGrilla(fila-1, 3);
		}else if(jTableCuotas.getSelectedColumn()==3){
	        forzarFocoGrilla(fila, 1);//baja un renglon
	        calculoTotalCuotasPagadas();//vuelve a calcular los totales
        }
		if(fila==0){
			if(jTableCuotas.getSelectedColumn()==2){//simboliza a la ultima cuota col 1, sabra Dios xq...
	            forzarFocoGrilla(cantidadFilas-1, 2);
			}else if(jTableCuotas.getSelectedColumn()==1){//simboliza que esta en la ultima cuota col 2, que rayos paso aqui?
				forzarFocoGrilla(cantidadFilas-1, 3);
		    }else if(jTableCuotas.getSelectedColumn()==0){//simboliza que esta en la ultima cuota col 3, que rayos paso aqui?
		    	BigDecimal pago=FormatearValor.stringABigDecimal(String.valueOf(jTableCuotas.getValueAt(cantidadFilas-1, 3)));
	        	pago=pago.setScale(monedaDecimales,RoundingMode.HALF_UP);//valida los decimales de la moneda usada
	        	String pagoString=FormatearValor.bigDecimalAStringDosDecimales(pago);//formatea el nuevo valor
	        	jTableCuotas.setValueAt(pagoString, cantidadFilas-1, 3);//escribe el nuevo valor
				bAceptar.requestFocus();
		    }
			calculoTotalCuotasPagadas();//vuelve a calcular los totales
		}
	}
	private void tipoColumna() {
        jTableCuotas.getColumnModel().getColumn( 1 ).setCellEditor(new CellTextFieldDecimalMoneda());
        jTableCuotas.getColumnModel().getColumn( 1 ).setCellRenderer(new CellRendererTextFieldDecimalMoneda());
        jTableCuotas.getColumnModel().getColumn( 2 ).setCellEditor(new CellTextFieldCaracter());
        jTableCuotas.getColumnModel().getColumn( 2 ).setCellRenderer(new CellRendererTextFieldCaracter());
        jTableCuotas.getColumnModel().getColumn( 3 ).setCellEditor(new CellTextFieldDecimalMoneda());
        jTableCuotas.getColumnModel().getColumn( 3 ).setCellRenderer(new CellRendererTextFieldDecimalMoneda());
	}
	private void altura() {
		jTableCuotas.setRowHeight(20);
	}
	private void tamanhoTablaFijo() {
		jTableCuotas.getColumnModel().getColumn(1).setMinWidth(40);
		jTableCuotas.getColumnModel().getColumn(1).setMaxWidth(40);
		jTableCuotas.getColumnModel().getColumn(2).setMinWidth(110);
		jTableCuotas.getColumnModel().getColumn(2).setMaxWidth(110);
		jTableCuotas.getColumnModel().getColumn(3).setMinWidth(90);
		jTableCuotas.getColumnModel().getColumn(3).setMaxWidth(90);
	}
	 private void forzarFocoGrilla(Integer fila, Integer columna) {
		 jTableCuotas.changeSelection(fila, columna, false, false);
		 jTableCuotas.editCellAt(fila, columna);
		 jTableCuotas.getEditorComponent().requestFocusInWindow();
	 }
	private void bloquearDesplazamientoColumnas() {
		jTableCuotas.getTableHeader().setReorderingAllowed(false) ;
	}
	private void calculoTotalCuotasPagadas() {
		BigDecimal totalMontoAcumulado=BigDecimal.ZERO;
		BigDecimal montoPago;
		String aux;
		for(Integer fila=0;fila<dtmTableCuotas.getRowCount();fila++){
			aux=String.valueOf(jTableCuotas.getValueAt(fila, 3));
			if(!aux.equals("")){
				montoPago=FormatearValor.stringABigDecimal(aux);
				totalMontoAcumulado=totalMontoAcumulado.add(montoPago);
			}
		}
		tfTotalCuotas.setText(FormatearValor.bigDecimalAStringDosDecimales(totalMontoAcumulado));
	}
	private Boolean esValidoCamposCabecera() {
		Integer lapsoCampo=Integer.valueOf(tfLapso.getText());
		Integer lapsoVariable=Integer.valueOf(this.lapso);
		if(lapsoCampo>lapsoVariable){
			JOptionPane.showMessageDialog(null, "El lapso para el vencimiento no puede ser mayor a "+lapsoVariable);
			tfLapso.setText(this.lapso);
			tfLapso.requestFocus();
			return false;
		}
		return true;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
