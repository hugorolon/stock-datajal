package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.CompraImportacion;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CompraImportacionTableModel extends DefaultTableModel<CompraImportacion> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
                "Proveedor ID",
                "Proveedor",
                "Valor"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	CompraImportacion item = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getProveedorId();
            case 1:
                return item.getProveedor();
            case 2:
                return item.getValor();
            default:
                return "";
        }
    }
}
