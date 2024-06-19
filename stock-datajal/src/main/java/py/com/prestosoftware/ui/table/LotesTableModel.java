package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Lotes;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class LotesTableModel extends DefaultTableModel<Lotes> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "PRODUCTO.", "FECHA FINAL", "PRECIO COMPRA", "PRECIO VENTA", "STOCK" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Lotes item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getIdProducto();
            case 1:
                return Fechas.dateUtilAStringDDMMAAAA(item.getFechaFinal());
            case 2:
                return item.getPrecioCompra();
            case 3:
                return item.getPrecioVenta();
            case 4:
                return item.getStock();
            default:
                return "";
        }
    }
}
