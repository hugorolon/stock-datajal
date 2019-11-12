package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class SubgrupoTableModel extends DefaultTableModel<Subgrupo> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "NOMBRE", "PA %", "PB %", "PC %", "PD %", "PE %", "ACTIVO" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Subgrupo subgrupo = entities.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return subgrupo.getNombre();
            case 1:
                return subgrupo.getPorcentajePrecioA() != null ? subgrupo.getPorcentajePrecioA():0;
            case 2:
                return subgrupo.getPorcentajePrecioB() != null ? subgrupo.getPorcentajePrecioB():0;
            case 3:
                return subgrupo.getPorcentajePrecioC() != null ? subgrupo.getPorcentajePrecioC():0;
            case 4:
                return subgrupo.getPorcentajePrecioD() != null ? subgrupo.getPorcentajePrecioD():0;
            case 5:
                return subgrupo.getPorcentajePrecioE() != null ? subgrupo.getPorcentajePrecioE():0;
            case 6:
                return subgrupo.getActivo() == 1 ? "SI":"NO";
            default:
                return "";
        }
    }
}
