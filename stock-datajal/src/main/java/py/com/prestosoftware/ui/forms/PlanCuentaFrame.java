package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.PlanCuentaTablePanel;

@Component
public class PlanCuentaFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    private TableSearchPanel searchPanel;
    private PlanCuentaTablePanel tablePanel;
    private PlanCuentaPanel formPanel;

    @Autowired
    public PlanCuentaFrame(TableSearchPanel searchPanel, PlanCuentaTablePanel tablePanel, PlanCuentaPanel formPanel) {
    	this.searchPanel = searchPanel;
    	this.tablePanel = tablePanel;
        this.formPanel = formPanel;
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle("Registro de Plan de Cuentas");
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
    
    public PlanCuentaTablePanel getTablePanel() {
		return tablePanel;
	}
    
    public TableSearchPanel getSearchPanel() {
		return searchPanel;
	}
    
    public PlanCuentaPanel getFormPanel() {
		return formPanel;
	}
    
    public void closeWindow() {
    	dispose();
    }
  
}