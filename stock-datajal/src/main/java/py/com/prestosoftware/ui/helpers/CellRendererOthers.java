package py.com.prestosoftware.ui.helpers;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRendererOthers extends DefaultTableCellRenderer {
 
	private static final long serialVersionUID = 1L;

	public CellRendererOthers() {
        super();
    }
    
    public void setValue(Object value) {
        if ((value != null) && ((value instanceof Integer) || (value instanceof Long))) {
            Number numberValue = (Number) value;
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
            nf.setMaximumFractionDigits(0);
            nf.setMinimumFractionDigits(0);
            value = nf.format(numberValue.longValue());
        } else if (value instanceof Double || value instanceof BigDecimal) {
                DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
        	    simbolo.setDecimalSeparator(',');
        	    simbolo.setGroupingSeparator('.');
        	    DecimalFormat formateador = new DecimalFormat("###,###.###",simbolo); 
        	    value= formateador.format(value);
            } else if (value instanceof String) {
                setHorizontalAlignment(SwingConstants.LEFT);
            }
        super.setValue(value);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
    	if ((row % 2) == 0) {
            super.setBackground(Color.WHITE);
    	} else { 
            super.setBackground(new Color(225, 251, 234));
    	}
    	JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setFont(new java.awt.Font("Tahoma", 0, 14));
        
        return label;
    }
}
