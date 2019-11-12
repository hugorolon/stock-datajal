package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class NotaLanzadaTableModel extends DefaultTableModel<MovimientoCaja> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "DOCUMENTO", "VALOR", "REF.", "SITUACIÓN" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	MovimientoCaja item = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getNotaNro();
            case 1:
                return FormatearValor.doubleAString(item.getNotaValor());
            case 2:
                return item.getNotaReferencia();
            case 3:
                return item.getSituacion();
                
            default:
                return "";
        }
    }
}
