package py.com.prestosoftware.ui.shared;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

public abstract class AbstractFrameController {

    public abstract void prepareAndOpenFrame();

    protected void registerAction(JButton button, ActionListener listener) {
        button.addActionListener(listener);
    }
    
    protected void registerButtonEvent(JButton button, KeyListener listener) {
        button.addKeyListener(listener);
    }
    
    protected void registerKeyEvent(JTextField text, KeyListener listener) {
    	text.addKeyListener(listener);
    }
    
    protected void registerSelectRow(ListSelectionModel model, ListSelectionListener listener) {
    	model.addListSelectionListener(listener);
    }
    
    protected void registerOpenMenu(JMenuItem menuItem, ActionListener listener) {
    	menuItem.addActionListener(listener);
    }

}