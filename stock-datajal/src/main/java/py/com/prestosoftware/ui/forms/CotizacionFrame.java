package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.CotizacionTablePanel;

@Component
public class CotizacionFrame extends JDialog {

	private static final long serialVersionUID = 1L;

    private TableSearchPanel searchPanel;
    private CotizacionTablePanel tablePanel;
    private CotizacionPanel formPanel;

    @Autowired
    public CotizacionFrame(TableSearchPanel searchPanel, CotizacionTablePanel tablePanel, CotizacionPanel formPanel) {
    	this.searchPanel = searchPanel;
    	this.tablePanel = tablePanel;
        this.formPanel = formPanel;
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle("Registro de Cotizaciones");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(GlobalVars.DEFAULT_WIDTH, GlobalVars.DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
    	add(searchPanel, BorderLayout.NORTH);
    	add(tablePanel, BorderLayout.CENTER);
    	add(formPanel, BorderLayout.SOUTH); 
    }
    
    public CotizacionTablePanel getTablePanel() {
		return tablePanel;
	}
    
    public TableSearchPanel getSearchPanel() {
		return searchPanel;
	}
    
    public CotizacionPanel getFormPanel() {
		return formPanel;
	}
    
    public void closeWindow() {
    	dispose();
    }
    
}
