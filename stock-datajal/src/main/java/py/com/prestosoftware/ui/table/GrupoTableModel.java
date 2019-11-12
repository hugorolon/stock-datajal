package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class GrupoTableModel extends DefaultTableModel<Grupo> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "NOMBRE", "PA %", "PB %", "PC %", "PD %", "PE %", "ACTIVO" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Grupo grupo = entities.get(rowIndex);
        
    	switch (columnIndex) {
            case 0:
                return grupo.getNombre();
            case 1:
                return grupo.getPorcIncrementoPrecioA() != null ? grupo.getPorcIncrementoPrecioA():0;
            case 2:
                return grupo.getPorcIncrementoPrecioB() != null ? grupo.getPorcIncrementoPrecioB():0;
            case 3:
                return grupo.getPorcIncrementoPrecioC() != null ? grupo.getPorcIncrementoPrecioC():0;
            case 4:
                return grupo.getPorcIncrementoPrecioD() != null ? grupo.getPorcIncrementoPrecioD():0;
            case 5:
                return grupo.getPorcIncrementoPrecioE() != null ? grupo.getPorcIncrementoPrecioE():0;
            case 6:
                return grupo.getActivo() == 1 ? "SI":"NO";
            default:
                return "";
        }
    }
}
