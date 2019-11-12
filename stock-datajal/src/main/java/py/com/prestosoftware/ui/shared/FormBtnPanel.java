package py.com.prestosoftware.ui.shared;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.springframework.stereotype.Component;

@Component
public class FormBtnPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton saveBtn;
    private JButton cancelBtn;
    private JButton closeBtn;

    public FormBtnPanel() {
        initComponents();
    }

    private void initComponents() {
        //saveBtn = new JButton(ConstMessagesEN.Labels.ADD_BTN);
        saveBtn = new JButton("Guardar");
        add(saveBtn);

        cancelBtn = new JButton("Cancelar");
        add(cancelBtn);
        
        closeBtn = new JButton("Cerrar");
        add(closeBtn);
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }
    
    public JButton getCloseBtn() {
		return closeBtn;
	}

}