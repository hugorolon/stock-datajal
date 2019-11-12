package py.com.prestosoftware.ui.table;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;

@Component
public class ProductTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ProductTableModel productTableModel;
    private JTable table;

    @Autowired
    public ProductTablePanel(ProductTableModel productTableModel) {
        this.productTableModel = productTableModel;
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        table = new JTable(productTableModel);
        table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane paneWithTable = new JScrollPane(table);
        add(paneWithTable, BorderLayout.SOUTH);
    }

    public JTable getProductTable() {
        return table;
    }

}