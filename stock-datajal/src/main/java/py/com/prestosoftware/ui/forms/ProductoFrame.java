package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.springframework.beans.factory.annotation.Autowired;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.ProductTablePanel;

//@Component
public class ProductoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 800;

    private TableSearchPanel searchPanel;
    private ProductTablePanel productTablePanel;
    private ProductoPanel formPanel;

    @Autowired
    public ProductoFrame(TableSearchPanel searchPanel, ProductTablePanel productTablePanel, ProductoPanel formPanel) {
    	this.searchPanel = searchPanel;
    	this.productTablePanel = productTablePanel;
        this.formPanel = formPanel;
        //setModal(true);
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle("REGISTRO DE PRODUCTOS");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
    	getContentPane().setLayout(new BorderLayout(10, 10));
//    	getContentPane().add(searchPanel, BorderLayout.SOUTH); //buscador
//    	getContentPane().add(productTablePanel, BorderLayout.SOUTH); //table
//    	getContentPane().add(formPanel, BorderLayout.CENTER); // para agregar cliente
    }

    public ProductTablePanel getTablePanel() {
        return productTablePanel;
    }
    
    public TableSearchPanel getSearchPanel() {
		return searchPanel;
	}
    
    public ProductoPanel getFormPanel() {
        return formPanel;
    }
    
    public void closeWindow() {
    	dispose();
    }
    
}