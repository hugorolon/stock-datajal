package py.com.prestosoftware.ui.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;

import javax.swing.*;
import java.awt.*;

@Component
public class ItemTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ClientTableModel clientTableModel;

    private JTable clientTable;

    @Autowired
    public ItemTablePanel(ClientTableModel clientTableModel) {
        this.clientTableModel = clientTableModel;
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        clientTable = new JTable(clientTableModel);
        clientTable.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane paneWithTable = new JScrollPane(clientTable);
        add(paneWithTable, BorderLayout.CENTER);
    }

    public JTable getClientTable() {
        return clientTable;
    }

}
