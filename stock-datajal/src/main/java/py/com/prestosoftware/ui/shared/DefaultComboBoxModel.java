package py.com.prestosoftware.ui.shared;

import java.util.List;

public abstract class DefaultComboBoxModel<T> extends javax.swing.DefaultComboBoxModel<T> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public T getSelectedItem() {
        return (T) super.getSelectedItem();
    }

    public void addElements(List<T> elements) {
        elements.forEach(this::addElement);
    }

    public void clear() {
        removeAllElements();
    }
}
