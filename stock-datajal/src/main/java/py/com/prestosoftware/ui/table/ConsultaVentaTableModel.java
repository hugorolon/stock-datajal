package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;

@Component
public class ConsultaVentaTableModel extends DefaultTableModel<ConsultaNota> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
        		"OP.", "NOTA", "FECHA", "COD. CLI", "CLIENTE", "PRECIO", "CANTIDAD", "USUARIO", "DEPOSITO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	ConsultaNota v = entities.get(rowIndex);

        switch (columnIndex) {
	        case 0:
	            return v.getOperacion();
            case 1:
                return v.getNotaNro();
            case 2:
                return v.getFecha();
            case 3:
                return v.getNotaRefCod();
            case 4:
                return v.getNotaRef();
            case 5:
                return v.getPrecioProducto();
            case 6:
                return v.getCantidad();
            case 7:
                return v.getUsuarioId();
            case 8:
                return v.getDepositoId();
            default:
                return "";
        }
    }
}
