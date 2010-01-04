package net.geant2.jra3.intradomain.builder;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.geant2.jra3.intradomain.ethernet.EthLink;
import net.geant2.jra3.intradomain.ethernet.SpanningTree;
import net.geant2.jra3.intradomain.topology.EthernetTopology;


/**
 * JDialog for configuration of Spanning Tree in ethernet topology
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class SpanningTreeDialog extends JDialog implements ActionListener, ListSelectionListener{

	private static final long serialVersionUID = -6589966460127391802L;
	EthLink ethLink;
	SpanningTree currentSpanningTree;  //  @jve:decl-index=0:
	EthernetTopology topology;
	Vector spanningData = new Vector();
	
	private JPanel mainPanel = null;
	private JPanel topPanel = null;
	private JPanel bottomPanel = null;
	private JLabel linkNameLabel = null;
	private JTextField linkName = null;
	private JButton createSpannigTreeButton = null;
	private JButton RemoveButton = null;
	private JButton modifyButton = null;
	private JScrollPane listScrollPane = null;
	private JList spannningsList = null;
	private JPanel vlanPanel = null;
	private JLabel vlanNameLabel = null;
	private JTextField vlanNameText = null;
	private JLabel vlanLowLabel = null;
	private JTextField vlanLowText = null;
	private JLabel vlanToLabel = null;
	private JTextField vlanHiText = null;
	private JLabel Cost = null;
	private JTextField costText = null;
	private JLabel Status = null;
	private JTextField stateText = null;
	/**
	 * Creates Spannig Tree dialog box
	 * 
	 */
	public SpanningTreeDialog() {
		super();
		initialize();
	}



	/**
	 * Initialises Spanning tree dialog
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(532, 283));
        this.setTitle("Spanning trees");
        this.setContentPane(getMainPanel());
			
	}


	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("REMOVE")){
			if (currentSpanningTree!=null){
				topology.removeSpanningTree(currentSpanningTree);
				spanningData.remove(currentSpanningTree);
				if (spanningData.size()==0)
					spannningsList.setSelectedIndex(-1);
				spannningsList.updateUI();
			}
			return;
		}
		if (action.equals("NEW")){
			String validate =validateFields();
			System.out.println (validate);
			if (validate!=null)
			{
				JOptionPane.showMessageDialog(this, validate);
				return;
			}
			SpanningTree tree= topology.createSpanningTree(vlanNameText.getText(), stateText.getText(), Integer.parseInt(vlanLowText.getText()), Integer.parseInt(vlanHiText.getText()), Long.parseLong(costText.getText()),ethLink);
			setCurrentSpanningTree(tree);
			spanningData.add(tree);
			spannningsList.updateUI();
			return;
		}
		if (action.equals("MODIFY")){
			String validate =validateFields();
			if (validate==null)
			{
				JOptionPane.showMessageDialog(this, validate);
				return;
			}
			currentSpanningTree.getVlan().setName(vlanNameText.getText());
			currentSpanningTree.getVlan().setLowNumber(Integer.parseInt(vlanLowText.getText()));
			currentSpanningTree.getVlan().setHighNumber(Integer.parseInt(vlanHiText.getText()));
			currentSpanningTree.setCost(Long.parseLong(costText.getText()));
			currentSpanningTree.setState(stateText.getText());
			return;
		}
	}
	/**
	 * Validates text from all fields
	 * @return error text
	 */
	public String validateFields(){
		String message=null;
		int vlanLow=0;
		int vlanHi =0;
		if (topology.checkVlanName (vlanNameText.getText())){
			return  "Vlan name exist";
		}
		try {
			vlanLow = Integer.parseInt(vlanLowText.getText());
		}catch (NumberFormatException e){
			return "Vlan low number field should be numeric value";
		}
		try {
			vlanHi = Integer.parseInt(vlanHiText.getText());
		}catch (NumberFormatException e){
				return "Vlan high number field should be numeric value";
		}
		try {
			long cost = Long.parseLong(costText.getText());
		}catch (NumberFormatException e){
				return "Cost field should be numeric value";
		}
		if (vlanHi<vlanLow)
			return "Vlan high number should be bigger as Vlan low number";
		
		return message;
	}

	/**
	 * This method initializes mainPanel	
	 * 	
	 * @return javax.swing.JPanel created pannel	
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(getTopPanel(), BorderLayout.NORTH);
			mainPanel.add(getBottomPanel(), BorderLayout.SOUTH);
			mainPanel.add(getListScrollPane(), BorderLayout.WEST);
			mainPanel.add(getVlanPanel(), BorderLayout.EAST);
			mainPanel.setFont(AutobahnTopologyBuilder.font);
		}
		return mainPanel;
	}



	/**
	 * This method initializes topPanel	
	 * 	
	 * @return javax.swing.JPanel created pannel	
	 */
	private JPanel getTopPanel() {
		if (topPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 364;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new Insets(2, 2, 2, 5);
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.gridx = 0;
			linkNameLabel = new JLabel();
			linkNameLabel.setText("Link");
			linkNameLabel.setFont(AutobahnTopologyBuilder.fontBolded);
			linkNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			linkNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			linkNameLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			linkNameLabel.setPreferredSize(new Dimension(100, 25));
			topPanel = new JPanel();
			topPanel.setLayout(new GridBagLayout());
			topPanel.add(linkNameLabel, gridBagConstraints);
			topPanel.add(getLinkName(), gridBagConstraints1);
		}
		return topPanel;
	}



	/**
	 * This method initializes bottomPanel	
	 * 	
	 * @return javax.swing.JPanel created pannel	
	 */
	private JPanel getBottomPanel() {
		if (bottomPanel == null) {
			bottomPanel = new JPanel();
			bottomPanel.setLayout(new BoxLayout(getBottomPanel(), BoxLayout.X_AXIS));
			bottomPanel.setPreferredSize(new Dimension(200, 30));
			bottomPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			bottomPanel.add(getCreateSpannigTreeButton(), null);
			bottomPanel.add(getModifyButton(), null);
			bottomPanel.add(getRemoveButton(), null);
			
		}
		return bottomPanel;
	}



	/**
	 * This method initializes linkName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLinkName() {
		if (linkName == null) {
			linkName = new JTextField();
			linkName.setPreferredSize(new Dimension(150, 25));
			linkName.setEditable(false);
			linkName.setEnabled(true);
			linkName.setFont(AutobahnTopologyBuilder.font);
		}
		return linkName;
	}



	/**
	 * This method initializes createSpannigTreeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCreateSpannigTreeButton() {
		if (createSpannigTreeButton == null) {
			createSpannigTreeButton = new JButton();
			
			createSpannigTreeButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			createSpannigTreeButton.setText("Create new");
			createSpannigTreeButton.setActionCommand("NEW");
			createSpannigTreeButton.setPreferredSize(new Dimension(50, 25));
			createSpannigTreeButton.addActionListener(this);
			createSpannigTreeButton.setFont(AutobahnTopologyBuilder.fontBolded);
		}
		return createSpannigTreeButton;
	}



	/**
	 * This method initializes RemoveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveButton() {
		if (RemoveButton == null) {
			RemoveButton = new JButton();
			RemoveButton.setText("Remove");
			RemoveButton.setHorizontalTextPosition(SwingConstants.RIGHT);
			RemoveButton.setActionCommand("REMOVE");
			RemoveButton.addActionListener(this);
			RemoveButton.setFont(AutobahnTopologyBuilder.fontBolded);
		}
		return RemoveButton;
	}



	/**
	 * This method initializes modifyButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getModifyButton() {
		if (modifyButton == null) {
			modifyButton = new JButton();
			modifyButton.setText("Modify");
			modifyButton.setActionCommand("MODIFY");
			modifyButton.addActionListener(this);
			modifyButton.setFont(AutobahnTopologyBuilder.fontBolded);
		}
		return modifyButton;
	}



	/**
	 * This method initializes listScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getListScrollPane() {
		if (listScrollPane == null) {
			listScrollPane = new JScrollPane();
			listScrollPane.setPreferredSize(new Dimension(220, 100));
			listScrollPane.setViewportView(getSpannningsList());
		}
		return listScrollPane;
	}



	/**
	 * This method initializes spannningsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getSpannningsList() {
		if (spannningsList == null) {
			spannningsList = new JList(spanningData);
			spannningsList.addListSelectionListener( this );
			spannningsList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
			spannningsList.setSize(new Dimension(220, 182));
			spannningsList.setFont(AutobahnTopologyBuilder.font);
		}
		return spannningsList;
	}



	/**
	 * This method initializes vlanPanel	
	 * 	
	 * @return javax.swing.JPanel created pannel	
	 */
	private JPanel getVlanPanel() {
		if (vlanPanel == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 4;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 4;
			Status = new JLabel();
			Status.setText("State");
			Status.setHorizontalAlignment(SwingConstants.CENTER);
			Status.setPreferredSize(new Dimension(100, 25));
			Status.setFont(AutobahnTopologyBuilder.fontBolded);
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints() ;
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			gridBagConstraints9.gridy = 3;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints9.gridx = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints() ;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 3;
			Cost = new JLabel();
			Cost.setText("Cost");
			Cost.setHorizontalAlignment(SwingConstants.CENTER);
			Cost.setPreferredSize(new Dimension(100, 25));
			Cost.setFont(AutobahnTopologyBuilder.fontBolded);
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 2;
			vlanToLabel = new JLabel();
			vlanToLabel.setText("Vlan high");
			vlanToLabel.setHorizontalAlignment(SwingConstants.CENTER);
			vlanToLabel.setPreferredSize(new Dimension(100, 25));
			vlanToLabel.setFont(AutobahnTopologyBuilder.fontBolded);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 1;
			vlanLowLabel = new JLabel();
			vlanLowLabel.setText("Vlan low");
			vlanLowLabel.setHorizontalAlignment(SwingConstants.CENTER);
			vlanLowLabel.setPreferredSize(new Dimension(45, 25));
			vlanLowLabel.setFont(AutobahnTopologyBuilder.fontBolded);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints3.weightx = 1.0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.ipady = 0;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 0;
			vlanNameLabel = new JLabel();
			vlanNameLabel.setText("Vlan name");
			vlanNameLabel.setPreferredSize(new Dimension(100, 25));
			vlanNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			vlanNameLabel.setName("vlanNameLabel");
			vlanNameLabel.setFont(AutobahnTopologyBuilder.fontBolded);
			vlanPanel = new JPanel();
			vlanPanel.setLayout(new GridBagLayout());
			vlanPanel.setPreferredSize(new Dimension(300, 35));
			vlanPanel.add(vlanNameLabel, gridBagConstraints2);
			vlanPanel.add(getVlanNameText(), gridBagConstraints3);
			vlanPanel.add(vlanLowLabel, gridBagConstraints4);
			vlanPanel.add(getVlanLowText(), gridBagConstraints5);
			vlanPanel.add(vlanToLabel, gridBagConstraints6);
			vlanPanel.add(getVlanHiText(), gridBagConstraints7);
			vlanPanel.add(Cost, gridBagConstraints8);
			vlanPanel.add(getCostText(), gridBagConstraints9);
			vlanPanel.add(Status, gridBagConstraints10);
			vlanPanel.add(getStateText(), gridBagConstraints11);
		}
		return vlanPanel;
	}



	/**
	 * This method initializes vlanNameText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getVlanNameText() {
		if (vlanNameText == null) {
			vlanNameText = new JTextField();
			vlanNameText.setFont(AutobahnTopologyBuilder.font);
		}
		return vlanNameText;
	}



	/**
	 * This method initializes vlanLowText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getVlanLowText() {
		if (vlanLowText == null) {
			vlanLowText = new JTextField();
			vlanLowText.setFont(AutobahnTopologyBuilder.font);
		}
		return vlanLowText;
	}



	/**
	 * This method initializes vlanHiText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getVlanHiText() {
		if (vlanHiText == null) {
			vlanHiText = new JTextField();
			vlanHiText.setFont(AutobahnTopologyBuilder.font);
		}
		return vlanHiText;
	}



	/**
	 * This method initializes costText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getCostText() {
		if (costText == null) {
			costText = new JTextField();
			costText.setFont(AutobahnTopologyBuilder.font);
		}
		return costText;
	}



	/**
	 * This method initializes stateText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getStateText() {
		if (stateText == null) {
			stateText = new JTextField();
			stateText.setFont(AutobahnTopologyBuilder.font);
		}
		return stateText;
	}


	/**
	 * Gets modified SpanningTree object
	 * @return modified SpanningTree object
	 */
	public SpanningTree getCurrentSpanningTree() {
		return currentSpanningTree;
	}


	/** 
	 * Sets SpanningTree object to modify
	 * @param currentSpanningTree SpanningTree object to modify
	 */
	public void setCurrentSpanningTree(SpanningTree currentSpanningTree) {
		this.currentSpanningTree = currentSpanningTree;
		if (currentSpanningTree==null){
			getVlanNameText().setText("");
			getVlanLowText().setText("");
			getVlanHiText().setText("");
			getCostText().setText("");
			getStateText().setText("");
			return;
		}
		getVlanNameText().setText(currentSpanningTree.getVlan().getName());
		getVlanLowText().setText(""+currentSpanningTree.getVlan().getLowNumber());
		getVlanHiText().setText(""+currentSpanningTree.getVlan().getHighNumber());
		getCostText().setText(""+currentSpanningTree.getCost());
		getStateText().setText(currentSpanningTree.getState());
		vlanPanel.updateUI();
	}


	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
	
		int index = spannningsList.getSelectedIndex();
		SpanningTree tree = (SpanningTree) spanningData.get(index);
		System.out.println (""+tree);
		setCurrentSpanningTree(tree);
	}
	
	
	/**
	 * Opens SpanningTree dialog box
	 * 
	 * @param topology	topology where link exists
	 * @param link link with SpanningTree object to modify
	 */	
	public void openSpannnigTreeDialog (EthernetTopology topology, EthLink link){
		System.out.println (link);
		linkName.setText(""+link);
		
		List list = topology.getSpanningTreesForLink(link);
		int length = list.size();
		spanningData.removeAllElements();
		for (int i=0;i<length;i++){
			spanningData.add(list.get(i));
		}	
		if (spanningData.size()>0){
			spannningsList.setSelectedIndex(0);
			setCurrentSpanningTree((SpanningTree) spanningData.get(0));
		}
		else{
			spannningsList.setSelectedIndex(-1);
			setCurrentSpanningTree(null);
		}
		this.setModal(false);
		this.setVisible(true);
		this.topology=topology;
		this.ethLink = link;
		
		
	}
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
