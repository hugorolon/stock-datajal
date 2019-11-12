package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.viewmodel.AjusteCuenta;

@Component
public class AjusteCuentaTableModel extends DefaultTableModel<AjusteCuenta> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "DOCUMENTO", "FECHA VENC", "VALOR", "SALDO DEB.", "VALOR A PAGAR"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	AjusteCuenta item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getDocumento();
            case 1:
            	return item.getVencimiento();
            case 2:
            	return item.getValor();
            case 3:
            	return item.getSaldo();
            case 4:
            	return item.getValorPago();
            default:
                return "";
        }
    }
}
