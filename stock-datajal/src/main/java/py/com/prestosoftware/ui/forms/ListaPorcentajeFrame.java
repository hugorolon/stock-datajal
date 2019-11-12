package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.ListaPrecioTablePanel;

@Component
public class ListaPorcentajeFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 300;

    private TableSearchPanel searchPanel;
    private ListaPrecioTablePanel tablePanel;
    private ListaPrecioPanel formPanel;

    @Autowired
    public ListaPorcentajeFrame(TableSearchPanel searchPanel, ListaPrecioTablePanel tablePanel, ListaPrecioPanel formPanel) {
    	this.searchPanel = searchPanel;
    	this.tablePanel = tablePanel;
        this.formPanel = formPanel;
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle("Lista de Precios");
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
    
    public ListaPrecioTablePanel getTablePanel() {
		return tablePanel;
	}
    
    public TableSearchPanel getSearchPanel() {
		return searchPanel;
	}
    
    public ListaPrecioPanel getFormPanel() {
		return formPanel;
	}
    
    public void closeWindow() {
    	dispose();
    }
    
}
