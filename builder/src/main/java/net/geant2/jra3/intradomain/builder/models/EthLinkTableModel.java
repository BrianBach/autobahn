package net.geant2.jra3.intradomain.builder.models;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.geant2.jra3.intradomain.builder.AutobahnTopologyBuilder;
import net.geant2.jra3.intradomain.builder.MessagesProvider;
import net.geant2.jra3.intradomain.ethernet.EthLink;

/**
 * Table properties model for {@link EthLink}
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class EthLinkTableModel extends DefaultTableModel implements
		ObjectPropertiesModel {

	private static final long serialVersionUID = 8149639309991858815L;
	/**
	 * Table column names
	 */
	private static final String columnNames[] = {
			MessagesProvider.getMessage("element.type"),
			MessagesProvider.getMessage("eth.link") };

	/**
	 * EthLink related with properties model
	 */
	EthLink ethLink;

	/**
	 * Creates EthLinkTableModel object
	 */
	public EthLinkTableModel() {
		columnIdentifiers
				.add(MessagesProvider.getMessage("generic.link.delay"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.link.protection"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.link.direction"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("eth.link.discoveryProtocol"));
		columnIdentifiers.add(MessagesProvider.getMessage("eth.link.isTrunk"));
		columnIdentifiers.add(MessagesProvider.getMessage("eth.link.L2Brandy"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("eth.link.nativeVlan"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenet.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#
	 * removeUserObject()
	 */
	public void removeUserObject() {
		setUserObject(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenet.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#
	 * setUserObject(java.lang.Object)
	 */
	public void setUserObject(Object object) {

		if (object == null) {
			int length = columnIdentifiers.size();
			for (int i = 1; i < length; i++)
				dataVector.add("");
			ethLink = null;
			return;

		}

		EthLink link = null;
		try {
			link = (EthLink) object;
		} catch (ClassCastException e) {
			setUserObject(null);
			return;
		}

		ethLink = link;

		dataVector.removeAllElements();
		dataVector.add(link.getGenericLink().getPropDelay());
		dataVector.add(link.getGenericLink().getProtection());
		dataVector.add(link.getGenericLink().getDirection());
		dataVector.add(link.getDiscoveryProtocol());
		dataVector.add(link.getIsTrunk());
		dataVector.add(link.getIsL2Bndry());
		dataVector.add(link.getNativeVlan());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenet.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#
	 * getUserObject()
	 */
	public Object getUserObject() {
		if (ethLink != null)
			return ethLink;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	public int getRowCount() {
		return columnIdentifiers.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object,
	 * int, int)
	 */
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			if (ethLink == null)
				return;
			dataVector.setElementAt(value, rowIndex);

			switch (rowIndex) {
			case 0:
				ethLink.getGenericLink().setPropDelay(
						ModelTool.parseDouble(ethLink.getGenericLink().getPropDelay(),
								(String) value));
				break;
			case 1:
				ethLink.getGenericLink().setProtection(
						ModelTool.parseBoolean(ethLink.getGenericLink().getProtection(),
								(String) value));
				dataVector.setElementAt(ethLink.getGenericLink()
						.getProtection(), 1);
				break;
			case 2:
				ethLink.getGenericLink().setDirection((String) value);
				break;
			case 3:
				ethLink.setDiscoveryProtocol((String) value);
				break;
			case 4:
				ethLink.setIsTrunk(ModelTool.parseBoolean(ethLink.getIsTrunk(),
						(String) value));
				dataVector.setElementAt(ethLink.getIsTrunk(), 4);
				break;
			case 5:
				ethLink.setIsL2Bndry(ModelTool.parseBoolean(ethLink.getIsL2Bndry(),
						(String) value));
				dataVector.setElementAt(ethLink.getIsL2Bndry(), 5);
				break;
			case 6:
				ethLink.setNativeVlan(ModelTool.parseLong(ethLink.getNativeVlan(),
						(String) value));
				dataVector.setElementAt(ethLink.getNativeVlan(), 6);
				break;
			}
			AutobahnTopologyBuilder.view
					.cellViewsChanged(AutobahnTopologyBuilder.view
							.getAllViews());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

		if (columnIndex == 1)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
		if (row >= dataVector.size())
			return null;
		Object returnValue = null;
		if (column == 0) {
			returnValue = columnIdentifiers.elementAt(row);
		} else if (column == 1) {
			returnValue = dataVector.elementAt(row);
		}
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenet.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#
	 * getTableModel()
	 */
	public TableModel getTableModel() {
		return this;
	}

}
