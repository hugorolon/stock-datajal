package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.UsuarioTablePanel;

@Component
public class UsuarioFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 400;

    private TableSearchPanel searchPanel;
    private UsuarioTablePanel tablePanel;
    private UsuarioPanel formPanel;

    @Autowired
    public UsuarioFrame(TableSearchPanel searchPanel, UsuarioTablePanel tablePanel, UsuarioPanel formPanel) {
    	this.searchPanel = searchPanel;
    	this.tablePanel = tablePanel;
        this.formPanel = formPanel;
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle("REGISTRO DE USUARIOS");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
    	//add(searchPanel, BorderLayout.NORTH);
    	add(tablePanel, BorderLayout.CENTER);
    	add(formPanel, BorderLayout.SOUTH); 
    }
    
    public UsuarioTablePanel getTablePanel() {
		return tablePanel;
	}
    
    public TableSearchPanel getSearchPanel() {
		return searchPanel;
	}
    
    public UsuarioPanel getFormPanel() {
		return formPanel;
	}
    
    public void closeWindow() {
    	dispose();
    }
    
}