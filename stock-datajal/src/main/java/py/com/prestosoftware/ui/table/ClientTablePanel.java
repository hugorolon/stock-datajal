package py.com.prestosoftware.ui.table;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@Component
public class ClientTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ClientTableModel clientTableModel;

    private JTable clientTable;

    @Autowired
    public ClientTablePanel(ClientTableModel clientTableModel) {
        this.clientTableModel = clientTableModel;
        initComponents();
    }

    private void initComponents() {
        clientTable = new JTable(clientTableModel);
        clientTable.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane paneWithTable = new JScrollPane(clientTable);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(paneWithTable, GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
        			.addContainerGap())
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(5)
        			.addComponent(paneWithTable, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        			.addContainerGap())
        );
        setLayout(groupLayout);
    }

    public JTable getClientTable() {
        return clientTable;
    }

}
