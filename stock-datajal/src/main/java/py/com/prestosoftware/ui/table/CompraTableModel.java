package py.com.prestosoftware.ui.table;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CompraTableModel extends DefaultTableModel<Compra> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
        		"COD.", "FECHA", "PROVEEDOR", "FACTURA", "TOTAL", "CONDICION", "ESTADO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Compra c = entities.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        switch (columnIndex) {
	        case 0:
	            return c.getId();
            case 1:
                return sdf.format(c.getFecha());
            case 2:
                return c.getProveedorNombre();
            case 3:
                return c.getFactura();
            case 4:
                return c.getTotalGeneral();
            case 5:
                return c.getCondicion();
            case 6:
                return c.getSituacion();
            default:
                return "";
        }
    }
}
