package py.com.prestosoftware.ui.shared;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.util.ConstMessagesEN;

@Component
public class TableBtnPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton addBtn;
    private JButton removeBtn;

    public TableBtnPanel() {
        initComponents();
    }

    private void initComponents() {
        addBtn = new JButton(ConstMessagesEN.Labels.ADD_BTN);
        add(addBtn);

        removeBtn = new JButton(ConstMessagesEN.Labels.REMOVE_BTN);
        add(removeBtn);
    }

    public JButton getAddBtn() {
        return addBtn;
    }

    public JButton getRemoveBtn() {
        return removeBtn;
    }

}
