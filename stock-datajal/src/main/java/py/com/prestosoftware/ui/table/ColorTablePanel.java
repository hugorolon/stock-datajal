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
public class ColorTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ColorTableModel tableModel;
    private JTable proveedorTable;

    @Autowired
    public ColorTablePanel(ColorTableModel tableModel) {
        this.tableModel = tableModel;
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        proveedorTable = new JTable(tableModel);
        proveedorTable.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        proveedorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane paneWithTable = new JScrollPane(proveedorTable);
        add(paneWithTable, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return proveedorTable;
    }

}
