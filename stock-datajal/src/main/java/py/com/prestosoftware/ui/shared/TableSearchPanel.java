package py.com.prestosoftware.ui.shared;

import org.springframework.stereotype.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@Component
public class TableSearchPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private static final int TEXT_FIELD_COLUMNS = 30;

	private JLabel lblSearch;
    private JTextField tfSearchField;
	private JButton searchBtn;

    public TableSearchPanel() {
        initComponents();
    }

    private void initComponents() {  
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{180, 223, 85, 0};
        gridBagLayout.rowHeights = new int[]{29, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        lblSearch = new JLabel("BUSCADOR");
        GridBagConstraints gbc_lblSearch = new GridBagConstraints();
        gbc_lblSearch.anchor = GridBagConstraints.WEST;
        gbc_lblSearch.insets = new Insets(0, 0, 0, 5);
        gbc_lblSearch.gridx = 0;
        gbc_lblSearch.gridy = 0;
        add(lblSearch, gbc_lblSearch);
        
        tfSearchField = new JTextField(TEXT_FIELD_COLUMNS);
        GridBagConstraints gbc_tfSearchField = new GridBagConstraints();
        gbc_tfSearchField.anchor = GridBagConstraints.WEST;
        gbc_tfSearchField.insets = new Insets(0, 0, 0, 5);
        gbc_tfSearchField.gridx = 1;
        gbc_tfSearchField.gridy = 0;
        add(tfSearchField, gbc_tfSearchField);
        
        searchBtn = new JButton("Buscar");
        GridBagConstraints gbc_searchBtn = new GridBagConstraints();
        gbc_searchBtn.anchor = GridBagConstraints.NORTHWEST;
        gbc_searchBtn.gridx = 2;
        gbc_searchBtn.gridy = 0;
        add(searchBtn, gbc_searchBtn);
    }
    
    public JButton getSearchBtn() {
		return searchBtn;
	}
    
    public JTextField getTfSearchField() {
		return tfSearchField;
	}

}