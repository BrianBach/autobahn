package net.geant2.jra3.intradomain.builder.models;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.geant2.jra3.intradomain.builder.AutobahnTopologyBuilder;
import net.geant2.jra3.intradomain.builder.MessagesProvider;
import net.geant2.jra3.intradomain.ethernet.EthPhysicalPort;

/**
 * Table properties model for {@link EthPort}
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class EthPortTableModel extends DefaultTableModel implements
		ObjectPropertiesModel {
	/**
	 * Table column names
	 */
	private static final String columnNames[] = {
			MessagesProvider.getMessage("element.type"),
			MessagesProvider.getMessage("eth.port") };

	/**
	 * Related port with properties model
	 */
	EthPhysicalPort port;

	/**
	 * Creates EthPortTableModel object
	 */
	public EthPortTableModel() {
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.interface.name"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("eth.port.macaddress"));
		columnIdentifiers.add(MessagesProvider.getMessage("eth.port.duplex"));
		columnIdentifiers.add(MessagesProvider.getMessage("eth.port.isTaged"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("eth.port.mediumDependentInterface"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.interface.bandwitdh"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.interface.clientPort"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.interface.status"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.interface.mtu"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.interface.domainId"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.interface.description"));
		setUserObject(null);
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
			dataVector.add(MessagesProvider.getMessage("sdh.interface.type"));
			for (int i = 1; i < length; i++)
				dataVector.add("");
			port = null;
			return;

		}

		EthPhysicalPort device = null;
		try {
			device = (EthPhysicalPort) object;
		} catch (ClassCastException e) {
			setUserObject(null);
			return;
		}

		port = device;

		dataVector.removeAllElements();
		dataVector.add(device.getGenericInterface().getName());
		dataVector.add(device.getMacAddress());
		dataVector.add(device.getDuplex());
		dataVector.add(device.getIsTagged());
		dataVector.add(device.getMediumDependentInterface());
		dataVector.add(device.getGenericInterface().getBandwidth());
		dataVector.add(device.getGenericInterface().isClientPort());
		dataVector.add(device.getGenericInterface().getStatus());
		dataVector.add(device.getGenericInterface().getMtu());
		dataVector.add(device.getGenericInterface().getDomainId());
		dataVector.add(device.getGenericInterface().getDescription());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seenet.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#
	 * getUserObject()
	 */
	public Object getUserObject() {
		return port;
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
			if (port == null)
				return;
			dataVector.setElementAt(value, rowIndex);
			String name;
			switch (rowIndex) {
			case 0:
				name = AutobahnTopologyBuilder.project.checkNames(port
						.getGenericInterface().getName(), (String) value);
				port.getGenericInterface().setName(name);
				port.setInterfaceName(name);
				dataVector.setElementAt(name, 0);
				break;

			case 1:
				port.setMacAddress((String) value);
				break;
			case 2:
				port.setDuplex((String) value);
				break;
			case 3:
				port.setIsTagged(ModelTool.parseBoolean(port.getIsTagged(),
						(String) value));
				dataVector.set(3, port.getIsTagged());
				break;
			case 4:
				port.setMediumDependentInterface((String) value);
			case 5:
				port.getGenericInterface().setBandwidth(
						ModelTool.parseLong(port.getGenericInterface()
								.getBandwidth(), (String) value));
				dataVector.set(5, port.getGenericInterface().getBandwidth());
				break;
			case 6:
				port.getGenericInterface().setClientPort(
						ModelTool.parseBoolean(port.getGenericInterface()
								.isClientPort(), (String) value));
				dataVector.set(6, port.getGenericInterface().isClientPort());
				break;
			case 7:
				port.getGenericInterface().setStatus((String) value);
				break;
			case 8:
				port.getGenericInterface().setMtu((String) value);
				break;
			case 9:
				port.getGenericInterface().setDomainId((String) value);
				break;
			case 10:
				port.getGenericInterface().setDescription((String) value);
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

	public TableModel getTableModel() {
		return this;
	}
}
