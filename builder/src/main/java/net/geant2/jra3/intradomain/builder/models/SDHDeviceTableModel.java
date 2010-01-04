package net.geant2.jra3.intradomain.builder.models;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.geant2.jra3.intradomain.builder.AutobahnTopologyBuilder;
import net.geant2.jra3.intradomain.builder.MessagesProvider;
import net.geant2.jra3.intradomain.sdh.SdhDevice;

/**
 * Table properties model for {@link SDHDevice}
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class SDHDeviceTableModel extends DefaultTableModel implements
		ObjectPropertiesModel {
	/**
	 * Table columns names
	 */
	private static final String columnNames[] = {
			MessagesProvider.getMessage("element.type"),
			MessagesProvider.getMessage("sdh.node") };
	
	/**
	 *SdhDevice related with properties model
	 */
	SdhDevice sdhDevice;

	/**
	 * Creates SDHDeviceTableModel object
	 */
	public SDHDeviceTableModel() {
		columnIdentifiers.add(MessagesProvider.getMessage("generic.node.name"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.node.ipAddress"));
		columnIdentifiers.add(MessagesProvider.getMessage("sdh.node.nsap"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.node.description"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.node.status"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.node.vendor"));
		columnIdentifiers
				.add(MessagesProvider.getMessage("generic.node.model"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.node.osName"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.name"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("location.description"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.country"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("location.institution"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.street"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.floor"));
		columnIdentifiers
				.add(MessagesProvider.getMessage("location.roomSuite"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.cabinet"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.zipCode"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("location.phoneNumber"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("location.emailAddress"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("location.geoLatitude"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("location.geoLongitude"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.type"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.zone"));
		columnIdentifiers.add(MessagesProvider.getMessage("location.attitude"));
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
			for (int i = 1; i < length; i++)
				dataVector.add("");
			sdhDevice = null;
			return;

		}

		SdhDevice device = null;
		try {
			device = (SdhDevice) object;
		} catch (ClassCastException e) {
			setUserObject(null);
			return;
		}

		sdhDevice = device;
		dataVector.removeAllElements();
		dataVector.add(device.getNode().getName());
		dataVector.add(device.getNode().getIpAddress());
		dataVector.add(device.getNsap());
		dataVector.add(device.getNode().getDescription());
		dataVector.add(device.getNode().getStatus());
		dataVector.add(device.getNode().getVendor());
		dataVector.add(device.getNode().getModel());
		dataVector.add(device.getNode().getOsName());
		dataVector.add(device.getNode().getOsVersion());
		dataVector.add(device.getNode().getLocation().getName());
		dataVector.add(device.getNode().getLocation().getDescription());
		dataVector.add(device.getNode().getLocation().getCountry());
		dataVector.add(device.getNode().getLocation().getInstitution());
		dataVector.add(device.getNode().getLocation().getStreet());
		dataVector.add(device.getNode().getLocation().getFloor());
		dataVector.add(device.getNode().getLocation().getRoomSuite());
		dataVector.add(device.getNode().getLocation().getCabinet());
		dataVector.add(device.getNode().getLocation().getZipCode());
		dataVector.add(device.getNode().getLocation().getPhoneNumber());
		dataVector.add(device.getNode().getLocation().geteMailAddress());
		dataVector.add(device.getNode().getLocation().getGeoLatitude());
		dataVector.add(device.getNode().getLocation().getGeoLongitude());
		dataVector.add(device.getNode().getLocation().getType());
		dataVector.add(device.getNode().getLocation().getZone());
		dataVector.add(device.getNode().getLocation().getAltitude());

	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#getUserObject()
	 */
	public Object getUserObject() {
		if (sdhDevice != null)
			return sdhDevice;
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	public int getRowCount() {
		return columnIdentifiers.size();
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			if (sdhDevice == null)
				return;
			String name;
			switch (rowIndex) {
			case 0:
				name = AutobahnTopologyBuilder.project.checkNames(sdhDevice
						.getNode().getName(), (String) value);
				sdhDevice.getNode().setName(name);
				sdhDevice.setName(name);
				dataVector.setElementAt(name, 0);
				break;

			case 1:
				sdhDevice.getNode().setIpAddress((String) value);
				break;
			case 2:
				sdhDevice
						.setNsap(ModelTool.parseLong(sdhDevice.getNsap(), (String) value));
				dataVector.setElementAt(sdhDevice.getNsap(), rowIndex);
				break;
			case 3:
				sdhDevice.getNode().setDescription((String) value);
				break;
			case 4:
				sdhDevice.getNode().setStatus((String) value);
				break;
			case 5:
				sdhDevice.getNode().setVendor((String) value);
				break;
			case 6:
				sdhDevice.getNode().setModel((String) value);
				break;
			case 7:
				sdhDevice.getNode().setOsName((String) value);
				break;
			case 8:
				sdhDevice.getNode().setOsVersion((String) value);
				break;
			case 9:
				sdhDevice.getNode().getLocation().setName((String) value);
				break;
			case 10:
				sdhDevice.getNode().getLocation()
						.setDescription((String) value);
				break;
			case 11:
				sdhDevice.getNode().getLocation().setCountry((String) value);
				break;
			case 12:
				sdhDevice.getNode().getLocation()
						.setInstitution((String) value);
				break;
			case 13:
				sdhDevice.getNode().getLocation().setStreet((String) value);
				break;
			case 14:
				sdhDevice.getNode().getLocation().setFloor(
						ModelTool.parseInt(sdhDevice.getNode().getLocation().getFloor(),
								(String) value));
				dataVector.setElementAt(sdhDevice.getNode().getLocation()
						.getFloor(), rowIndex);
				break;
			case 15:
				sdhDevice.getNode().getLocation().setRoomSuite(
						ModelTool.parseInt(sdhDevice.getNode().getLocation()
								.getRoomSuite(), (String) value));
				dataVector.setElementAt(sdhDevice.getNode().getLocation()
						.getRoomSuite(), rowIndex);
				break;
			case 16:
				sdhDevice.getNode().getLocation().setCabinet(
						ModelTool.parseInt(
								sdhDevice.getNode().getLocation().getCabinet(),
								(String) value));
				dataVector.setElementAt(sdhDevice.getNode().getLocation()
						.getCabinet(), rowIndex);
				break;
			case 17:
				sdhDevice.getNode().getLocation().setZipCode((String) value);
				break;
			case 18:
				sdhDevice.getNode().getLocation()
						.setPhoneNumber((String) value);
				break;
			case 19:
				sdhDevice.getNode().getLocation().seteMailAddress(
						(String) value);
				break;
			case 20:
				sdhDevice.getNode().getLocation().setGeoLatitude(
						ModelTool.parseDouble(sdhDevice.getNode().getLocation()
								.getGeoLatitude(), (String) value));
				dataVector.setElementAt(sdhDevice.getNode().getLocation()
						.getGeoLatitude(), rowIndex);
				break;
			case 21:
				sdhDevice.getNode().getLocation().setGeoLongitude(
						ModelTool.parseDouble(sdhDevice.getNode().getLocation()
								.getGeoLongitude(), (String) value));
				dataVector.setElementAt(sdhDevice.getNode().getLocation()
						.getGeoLongitude(), rowIndex);
				break;

			case 22:
				sdhDevice.getNode().getLocation().setType((String) value);
				break;
			case 23:
				sdhDevice.getNode().getLocation().setZone((String) value);
				break;
			case 24:
				sdhDevice.getNode().getLocation().setAltitude(
						ModelTool.parseDouble(sdhDevice.getNode().getLocation()
								.getAltitude(), (String) value));
				dataVector.setElementAt(sdhDevice.getNode().getLocation()
						.getAltitude(), rowIndex);
				break;

			}
			AutobahnTopologyBuilder.view
					.cellViewsChanged(AutobahnTopologyBuilder.view
							.getAllViews());
		}
	}

	/*
	 * (non-Javadoc)
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
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
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
	 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#getTableModel()
	 */
	public TableModel getTableModel() {
		return this;
	}
}
