package py.com.prestosoftware.ui.helpers;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class CellTextFieldCaracter extends DefaultCellEditor implements TableCellRenderer  {
	private static final long serialVersionUID = 3562965297268488880L;
	private JComponent component = new JTextField();
    private String value = ""; // valor de la celda
    /** Constructor de clase */
    public CellTextFieldCaracter() {
        super( new JTextField());
    }

    /** retorna valor de celda */
    @Override
    public Object getCellEditorValue() {
        return ((JTextField)component).getText();        
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String  texto = String.valueOf(value);
        ((JTextField) component).setText(texto);
        ((JTextField) component).setHorizontalAlignment(JTextField.LEFT);
        ((JTextField) component).addFocusListener(new java.awt.event.FocusAdapter() {   
			public void focusGained(java.awt.event.FocusEvent e) {
				((JTextField) component).selectAll();
			}
		});
        return ((JTextField) component);     
    }

    /** cuando termina la manipulacion de la celda */
    @Override
    public boolean stopCellEditing() { 
        value = String.valueOf(getCellEditorValue());
        ((JTextField)component).setText(value);
        return super.stopCellEditing();
    }

    /** retorna componente */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	if (value == null){
             return null; 
         } 
    	((JTextField) component).setHorizontalAlignment(JTextField.CENTER);//si se quiere universal
  	  	String texto = String.valueOf(value);
  	  ((JTextField) component).setText(texto);
  	  return ((JTextField) component);
    }
}
