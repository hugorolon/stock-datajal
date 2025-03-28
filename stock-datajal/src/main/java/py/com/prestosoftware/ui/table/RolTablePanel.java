package py.com.prestosoftware.ui.table;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.BorderLayout;

@Component
public class RolTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private RolTableModel tableModel;
    private JTable table;

    @Autowired
    public RolTablePanel(RolTableModel tableModel) {
        this.tableModel = tableModel;
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
    	setLayout(new BorderLayout());
    }

    private void initComponents() {
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane paneWithTable = new JScrollPane(table);
        add(paneWithTable, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

}
