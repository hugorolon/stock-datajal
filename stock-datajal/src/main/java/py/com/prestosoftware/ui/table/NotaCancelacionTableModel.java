package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.viewmodel.Nota;

@Component
public class NotaCancelacionTableModel extends DefaultTableModel<Nota> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "OPER. COD.", "OPERACION", "DOCUMENTO", "COD.", "CLI/PROV", "VALOR", 
        		"USUARIO COD.", "USUARIO", "EMPRESA", "FECHA"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Nota item = entities.get(rowIndex);

        switch (columnIndex) {
	        case 0:
	            return item.getOperacion();
            case 1:
                return item.getNota();
            case 2:
                return item.getNotaNro();
            case 3:
                return item.getNotaRefCod();
            case 4:
                return item.getNotaRef();
            case 5:
                return (Object)FormatearValor.doubleAString(item.getNotaValor());
            case 6:
                return item.getUsuarioId();
            case 7:
                return item.getUsuario();
            case 8:
                return item.getEmpresa();
            case 9:
                return Fechas.dateUtilAStringDDMMAAAA(item.getFecha());
            default:
                return "";
        }
    }
}
