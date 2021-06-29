package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.PagarProveedor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class PagarProveedorTableModel extends DefaultTableModel<PagarProveedor> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "NUMERO", "FECHA", "PROVEEDOR", "VALOR", "DESCUENTO", "RECARGO","MONTO NETO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	PagarProveedor item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getPprNumero();
            case 1:
                return item.getFecha();
            case 2:
                return item.getPprEntidad();
            case 3:
                return item.getPprValor();
            case 4:
                return item.getPprDescuento();
            case 5:
                return item.getPprRecargo();
            case 6:
                return item.getPprMonto();    
            default:
                return "";
        }
    }
}
