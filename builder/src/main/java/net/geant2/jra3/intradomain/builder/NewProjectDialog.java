package net.geant2.jra3.intradomain.builder;
import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.io.File;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JDialog for creating new project
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class NewProjectDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 8278057436299275522L;
	/**
	 * Result project of the NewProjectDialog
	 */
	TopologyProject project;
	
	/**
	 * GUI componets
	 */
	private Font font = AutobahnTopologyBuilder.font;
	private JPanel mainPanel = null;
	private JLabel nameLabel = null;
	private JTextField nameField = null;
	private JLabel Directory = null;
	private JTextField directoryField = null;
	private JButton openDirButton = null;
	private JLabel typeLabel = null;
	private JComboBox topologyTypeCombo = null;
	private JPanel bottomPanel = null;
	private JButton createButton = null;
	private JButton cancelButton = null;
	/**
	 * This method initializes 
	 * 
	 */
	public NewProjectDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(496, 207));
        this.setFont(new Font("Arial", Font.PLAIN, 10));
        this.setContentPane(getMainPanel());
        this.setTitle("Creating new topology project");
        this.setFont(AutobahnTopologyBuilder.fontBolded);
        String path = new File("").getAbsolutePath();
        this.directoryField.setText(path);
	}

	/**
	 * This method initializes mainPanel	
	 * 	
	 * @return javax.swing.JPanel created panel	
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints() ;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridwidth = 3;
			gridBagConstraints7.insets = new Insets(20, 0, 0, 0);
			gridBagConstraints7.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.gridwidth = 2;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			typeLabel = new JLabel();
			typeLabel.setText("TopologyType");
			typeLabel.setPreferredSize(new Dimension(100, 25));
			typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			typeLabel.setFont(AutobahnTopologyBuilder.fontBolded);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints() ;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 1;
			Directory = new JLabel();
			Directory.setText("Directory");
			Directory.setPreferredSize(new Dimension(100, 25));
			Directory.setHorizontalAlignment(SwingConstants.RIGHT);
			Directory.setFont(AutobahnTopologyBuilder.fontBolded);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints() ;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.gridy = 0;
			nameLabel = new JLabel();
			nameLabel.setText("Name");
			nameLabel.setPreferredSize(new Dimension(100, 25));
			nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			nameLabel.setFont(AutobahnTopologyBuilder.fontBolded);
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			mainPanel.add(nameLabel, gridBagConstraints);
			mainPanel.add(Directory, gridBagConstraints11);
			mainPanel.add(getDirectoryField(), gridBagConstraints2);
			mainPanel.add(getOpenDirButton(), gridBagConstraints3);
			mainPanel.add(typeLabel, gridBagConstraints4);
			mainPanel.add(getBottomPanel(), gridBagConstraints7);
			mainPanel.setFont(font);
			mainPanel.add(getNameField(), gridBagConstraints1);
			mainPanel.add(getTopologyTypeCombo(), gridBagConstraints5);
		}
		return mainPanel;
	}

	/**
	 * This method initializes nameField	
	 * 	
	 * @return javax.swing.JTextField created field	
	 */
	private JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
			nameField.setPreferredSize(new Dimension(100, 25));
			nameField.setFont(font);
		}
		return nameField;
	}

	/**
	 * This method initializes directoryField	
	 * 	
	 * @return javax.swing.JTextField created field	
	 */
	private JTextField getDirectoryField() {
		if (directoryField == null) {
			directoryField = new JTextField();
			directoryField.setPreferredSize(new Dimension(100, 25));
			directoryField.setEditable(true);
			directoryField.setFont(font);
		}
		return directoryField;
	}

	/**
	 * This method initializes openDirButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpenDirButton() {
		if (openDirButton == null) {
			openDirButton = new JButton();
			openDirButton.setText("Browse ...");
			openDirButton.setPreferredSize(new Dimension(90, 25));
			openDirButton.setActionCommand("BROWSER");
			openDirButton.addActionListener(this);
			openDirButton.setFont(font);
		}
		return openDirButton;
	}

	/**
	 * This method initializes topologyTypeCombo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTopologyTypeCombo() {
		if (topologyTypeCombo == null) {
			topologyTypeCombo = new JComboBox();
			topologyTypeCombo.setPreferredSize(new Dimension(100, 25));
			topologyTypeCombo.setFont(font);
			Iterator iterator = ProjectFactory.getProjectTypesNames();
			while (iterator.hasNext()){
				topologyTypeCombo.addItem(iterator.next());
			}
			
		}
		return topologyTypeCombo;
	}

	/**
	 * This method initializes bottomPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getBottomPanel() {
		if (bottomPanel == null) {
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 4;
			gridBagConstraints8.gridy = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 5;
			gridBagConstraints6.insets = new Insets(0, 0, 0, 20);
			gridBagConstraints6.gridy = 0;
			bottomPanel = new JPanel();
			bottomPanel.setLayout(new GridBagLayout());
			bottomPanel.setFont(font);
			bottomPanel.add(getCancelButton(), gridBagConstraints6);
			bottomPanel.add(getCreateButton(), gridBagConstraints8);
		}
		return bottomPanel;
	}

	/**
	 * This method initializes createButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCreateButton() {
		if (createButton == null) {
			createButton = new JButton();
			createButton.setActionCommand("CREATE");
			createButton.setPreferredSize(new Dimension(100, 25));
			createButton.setText("Create");
			createButton.setFont(font); 
			createButton.addActionListener (this);
		}
		return createButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setActionCommand("CANCEL");
			cancelButton.setPreferredSize(new Dimension(100, 25));
			cancelButton.setText("Cancel");
			cancelButton.setFont(font);
			cancelButton.addActionListener (this);
		}
		return cancelButton;
	}
	public TopologyProject openDialog () {
		this.setModal (true);
		this.setVisible(true);
		return project;
	}
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("BROWSER")){
			JFileChooser chooser = new JFileChooser(directoryField.getText());
			 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		            directoryField.setText(chooser.getSelectedFile().getAbsolutePath());
		    }
		    return;
		}
		if (e.getActionCommand().equals("CREATE")){
			if (nameField.getText()==null || nameField.getText().length()==0){
				JOptionPane.showMessageDialog(this,
						"Name field can not be empty",
						"Alert",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			System.out.println (topologyTypeCombo.getSelectedItem());
			project = ProjectFactory.createProject((String)topologyTypeCombo.getSelectedItem());
			System.out.println (project);
			project.setName(nameField.getText());
			project.setPath(directoryField.getText());
			System.out.println (project);
			
			setVisible(false);
		}else
		if (e.getActionCommand().equals("CANCEL")){
			setVisible(false);
		}

	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
