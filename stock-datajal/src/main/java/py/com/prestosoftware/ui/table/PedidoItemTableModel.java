package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.PedidoDetalle;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class PedidoItemTableModel extends DefaultTableModel<PedidoDetalle> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "Codigo", "Cantidad", "Descripcion", "Precio Unit.", "Precio Total"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	PedidoDetalle item = entities.get(rowIndex);

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
            default:
                return "";
        }
    }
}
