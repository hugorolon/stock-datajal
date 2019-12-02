package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.UsuarioRolTablePanel;

@Component
public class UsuarioRolFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 400;

    private TableSearchPanel searchPanel;
    private UsuarioRolTablePanel tablePanel;
    private UsuarioRolPanel formPanel;
    
    @Autowired
    public UsuarioRolFrame(TableSearchPanel searchPanel, UsuarioRolTablePanel tablePanel, UsuarioRolPanel formPanel) {
    	this.searchPanel = searchPanel;
    	this.tablePanel = tablePanel;
        this.formPanel = formPanel;
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle("REGISTRO DE USUARIOS ROLES");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
    	add(searchPanel, BorderLayout.NORTH);
    	add(tablePanel, BorderLayout.CENTER);
    	add(formPanel, BorderLayout.SOUTH); 
    }
    
    public UsuarioRolTablePanel getTablePanel() {
		return tablePanel;
	}
    
    public TableSearchPanel getSearchPanel() {
		return searchPanel;
	}
    
    public UsuarioRolPanel getFormPanel() {
		return formPanel;
	}
    
    public void closeWindow() {
    	dispose();
    }
    
}