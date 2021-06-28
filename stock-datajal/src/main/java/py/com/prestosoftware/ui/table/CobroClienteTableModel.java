package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.CobroCliente;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CobroClienteTableModel extends DefaultTableModel<CobroCliente> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "NUMERO", "FECHA", "CLIENTE", "VALOR", "DESCUENTO", "RECARGO","MONTO NETO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	CobroCliente item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getCclNumero();
            case 1:
                return item.getFecha();
            case 2:
                return item.getCclEntidad();
            case 3:
                return item.getCclValor();
            case 4:
                return item.getCclDescuento();
            case 5:
                return item.getCclRecargo();
            case 6:
                return item.getCclMonto();    
            default:
                return "";
        }
    }
}
