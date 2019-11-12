package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.viewmodel.MonedaValor;

@Component
public class MonedaValorTableModel extends DefaultTableModel<MonedaValor> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "COD", "OPER", "SIGLA", "TOTAL", "RECIBIDO", "VUELTO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	MonedaValor item = entities.get(rowIndex);

        switch (columnIndex) {
	        case 0:
	            return item.getMonedaId();
            case 1:
                return item.getOperacion();
            case 2:
                return item.getSigla();
            case 3:
                return "";
            case 4:
                return "";
            case 5:
                return "";
            default:
                return "";
        }
    }
}
