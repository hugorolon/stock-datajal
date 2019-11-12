
package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class FacturaCompraTableModel extends DefaultTableModel<Compra> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
        		"COD.", "FECHA VENC.", "VALOR TOTAL", "TOTAL PAGADO", "SALDO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Compra c = entities.get(rowIndex);

        switch (columnIndex) {
	        case 0:
	            return c.getId();
            case 1:
                return Fechas.formatoDDMMAA(c.getVencimiento());
            case 2:
                return c.getTotalGeneral();
            case 3:
                return "";
            case 4:
                return "";
            default:
                return "";
        }
    }
}
