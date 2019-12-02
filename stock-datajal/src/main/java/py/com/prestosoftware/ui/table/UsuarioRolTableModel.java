package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.UsuarioRol;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class UsuarioRolTableModel extends DefaultTableModel<UsuarioRol> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{"ID", "USUARIO", "ROL"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	UsuarioRol usuario = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return usuario.getId();
            case 1:
                return usuario.getUsuario().getUsuario();
            case 2:
                return usuario.getRol().getNombre();
            default:
                return "";
        }
    }
}
