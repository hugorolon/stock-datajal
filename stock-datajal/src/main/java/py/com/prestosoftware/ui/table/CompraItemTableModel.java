package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.CompraDetalle;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CompraItemTableModel extends DefaultTableModel<CompraDetalle> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "Codigo", "Cantidad", "Descripcion", "Precio Unit.", "Precio Total", 
        		"Precio Fob", "Costo Cif", "Gasto"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	CompraDetalle item = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getProductoId();
            case 1:
                return item.getCantidad();
            case 2:
                return item.getProducto();
            case 3:
                return item.getPrecio();
            case 4:
                return item.getSubtotal();
            case 5:
                return item.getPrecioFob();
            case 6:
                return item.getCostoCif();
            case 7:
                return item.getGastos();
            default:
                return "";
        }
    }
}
