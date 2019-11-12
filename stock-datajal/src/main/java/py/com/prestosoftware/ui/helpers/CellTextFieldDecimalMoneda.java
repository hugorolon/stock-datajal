package py.com.prestosoftware.ui.helpers;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

public class CellTextFieldDecimalMoneda extends DefaultCellEditor implements TableCellRenderer  {
	
	private static final long serialVersionUID = 1L;
	
	private JComponent component = new JTextField();    
    private String value = "";
    
    public CellTextFieldDecimalMoneda() {
        super(new JTextField());
    }

    @Override
    public Object getCellEditorValue() {
        return ((JTextField)component).getText();        
    }
    
    @Override//metodo donde se atrapan las acciones por pulsacion
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	((JTextField)component).setDocument(new CellDecimalDocument());//bloquea letras
    	String  texto = String.valueOf(value);
        ((JTextField) component).setText(texto);
        ((JTextField) component).setHorizontalAlignment(JTextField.RIGHT);
        ((JTextField)component).addKeyListener(new java.awt.event.KeyAdapter() {
        	public void keyPressed(java.awt.event.KeyEvent e) {
				Util.puntoDecimal(e);	//atrapa si se pulsa la tecla '.'
			}
        	public void keyReleased(java.awt.event.KeyEvent e) {
        		if(!((JTextField) component).getText().equals("")){
					GlobalVars.esSeleccionado=true;
        		}else{
        			GlobalVars.esSeleccionado=false;
        		}
				if (GlobalVars.esPuntoDecimal==true) {
					((JTextField)component).setText(Util.puntoDecimalAComa(((JTextField)component).getText()));
					GlobalVars.esPuntoDecimal=false;
				}
			}
		});
        ((JTextField) component).addFocusListener(new java.awt.event.FocusAdapter() {   
			public void focusGained(java.awt.event.FocusEvent e) {//cuando entra
				( (JTextField) component).selectAll();
			}
		});
        return ( (JTextField) component);     
    }

    @Override
    public boolean stopCellEditing() { 
        value = String.valueOf(getCellEditorValue()) ;
        value= Util.cantidadDosDecimales(value,2);
        ((JTextField)component).setText(value);
        return super.stopCellEditing();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	if (value == null){
             return null; 
         } 
    	return ((JTextField)component);
    }
}
