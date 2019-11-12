package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class MovimientoCajaTableModel extends DefaultTableModel<MovimientoCaja> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "OPERACIÓN", "TIPO", "DOC/NOTA", "CLI/PRO", "VALOR TOTAL", "HISTORICO", "ID REF" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	MovimientoCaja item = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getPlanCuentaId();
            case 1:
                return item.getTipoOperacion();
            case 2:
                return item.getNotaNro();
            case 3:
                return item.getNotaReferencia();
            case 4:
                return item.getNotaValor();
            case 5:
                return item.getObs();
            case 6:
                return item.getIdReferencia();
            default:
                return "";
        }
    }
}
