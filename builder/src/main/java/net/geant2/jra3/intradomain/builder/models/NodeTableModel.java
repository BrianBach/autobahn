package net.geant2.jra3.intradomain.builder.models;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.geant2.jra3.intradomain.builder.AutobahnTopologyBuilder;
import net.geant2.jra3.intradomain.builder.MessagesProvider;
import net.geant2.jra3.intradomain.common.Node;

/**
 * Table properties model for {@link Node}
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class NodeTableModel extends DefaultTableModel implements ObjectPropertiesModel{ 
	
	private static final long serialVersionUID = 6401571113954801711L;

	/**
	 * Table columns names
	 */
	private static final String columnNames[] = { MessagesProvider.getMessage("element.type"),
	        MessagesProvider.getMessage("generic.node")};

	/**
	 * Node related with properties table model
	 */
	Node  node;
    
	/**
	 * Creates NodeTableModel object
	 */
	public NodeTableModel() {
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.node.name"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.node.ipAddress"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.node.description"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.node.status"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.node.vendor"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.node.model"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.node.osName"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.node.osVersion"));
	    	
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.name"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.description"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.country"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.institution"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.street"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.floor"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.roomSuite"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.cabinet"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.zipCode"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.phoneNumber"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.emailAddress"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.geoLatitude"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.geoLongitude"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.type"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.zone"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("location.attitude"));
	    	
	    	setUserObject(null);
	    	
	    }
	 
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#removeUserObject()
	 */
	public void removeUserObject() {
		setUserObject(null);
	 } 
	/*
	 *  (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#setUserObject(java.lang.Object)
	 */
	public void setUserObject(Object object) {
		 
	    	if (object == null)
	    	{
	    	
	    		int length = columnIdentifiers.size();
		    	for (int i=1;i<length;i++)
		    		dataVector.add("");
		    	node = null;
		    	return;
		    	
	    	}
	    	
	    	Node device=null;
	    	try{ 
	    		device =(Node)object;
	    	}catch (ClassCastException e){setUserObject (null);return;}	
	   
	    	node = device;
	    	
	    	dataVector.removeAllElements();
	    	dataVector.add(node.getName());
	    	dataVector.add(node.getIpAddress());
	    	dataVector.add(node.getDescription());
	    	dataVector.add(node.getStatus());
	    	dataVector.add(node.getVendor());
	    	dataVector.add(node.getModel());
	    	dataVector.add(node.getOsName());
	    	dataVector.add(node.getOsVersion());
	    	
	    	dataVector.add(node.getLocation().getName());
	    	dataVector.add(node.getLocation().getDescription());
	    	dataVector.add(node.getLocation().getCountry());
	    	dataVector.add(node.getLocation().getInstitution());
	    	dataVector.add(node.getLocation().getStreet());
	    	dataVector.add(node.getLocation().getFloor());
	    	dataVector.add(node.getLocation().getRoomSuite());
	    	dataVector.add(node.getLocation().getCabinet());
	    	dataVector.add(node.getLocation().getZipCode());
	    	dataVector.add(node.getLocation().getPhoneNumber());
	    	dataVector.add(node.getLocation().geteMailAddress());
	    	dataVector.add(node.getLocation().getGeoLatitude());
	    	dataVector.add(node.getLocation().getGeoLongitude());
	    	dataVector.add(node.getLocation().getType());
	    	dataVector.add(node.getLocation().getZone());
	    	dataVector.add(node.getLocation().getAltitude());
	    	
	    	
	    	
	    }
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#getUserObject()
	 */
	    public Object getUserObject() {
	    	return node;
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
	    	if (columnIndex==1 ){
	    		if (node== null)
	    			return;
	    		dataVector.setElementAt(value,rowIndex);
	    		String name;
	    		switch (rowIndex){
	    			case 0: 
	    				name = AutobahnTopologyBuilder.project.checkNames(node.getName(), (String)value);
	    				node.setName(name);
	    				dataVector.setElementAt(name, 0);
	    			break;
	    			case 1: node.setIpAddress((String)value);break;
	    			case 2: node.setDescription((String)value);break;
	    			case 3: node.setStatus((String)value);break;
	    			case 4: node.setVendor((String)value);break;
	    			case 5: node.setModel((String)value);break;
	    			case 6: node.setOsName((String)value);break;
	    			case 7: node.setOsVersion((String)value);break;
	    			case 8: node.getLocation().setName((String)value);break;
	    	    	case 9: node.getLocation().setDescription((String)value);break;
	    	    	case 10: node.getLocation().setCountry((String)value);break;
	    	    	case 11: node.getLocation().setInstitution((String)value);break;
	    	    	case 12: node.getLocation().setStreet((String)value);break;
	    	    	case 13: 
	    	    		node.getLocation().setFloor(ModelTool.parseInt (node.getLocation().getFloor(),(String)value));
	    	    		dataVector.setElementAt(node.getLocation().getFloor(),rowIndex);
	    	    		break;
	    	    	case 14: 
	    	    		node.getLocation().setRoomSuite(ModelTool.parseInt(node.getLocation().getRoomSuite(),(String)value));
	    	    		dataVector.setElementAt(node.getLocation().getRoomSuite(),rowIndex);
	    	    		break;
	    	    	case 15: 
	    	    		node.getLocation().setCabinet(ModelTool.parseInt(node.getLocation().getCabinet(),(String)value));
	    	    		dataVector.setElementAt(node.getLocation().getCabinet(),rowIndex);
	    	    		break;
	    	    	case 16: node.getLocation().setZipCode((String)value);break;
	    	    	case 17: node.getLocation().setPhoneNumber((String)value);break;
	    	    	case 18: node.getLocation().seteMailAddress((String)value);break;
	    	    	case 19: 
	    	    		node.getLocation().setGeoLatitude(ModelTool.parseDouble(node.getLocation().getGeoLatitude(),(String)value));
	    	    		dataVector.setElementAt(node.getLocation().getGeoLatitude(),rowIndex);
	    	    		break;
	    	    	case 20: 
	    	    		node.getLocation().setGeoLongitude(ModelTool.parseDouble(node.getLocation().getGeoLongitude(),(String)value));
	    	    		dataVector.setElementAt(node.getLocation().getGeoLongitude(),rowIndex);
	    	    		break;
	    	    	
	    	    	case 21: node.getLocation().setType((String)value);break;
	    	    	case 22: node.getLocation().setZone((String)value);break;
	    	    	case 23: 
	    	    		node.getLocation().setAltitude(
						ModelTool.parseDouble(node.getLocation().getAltitude(),(String)value));
	    	    		dataVector.setElementAt(node.getLocation().getAltitude(),rowIndex);
	    	    		break;
	    	    	
	    		}
	    		AutobahnTopologyBuilder.view.cellViewsChanged(AutobahnTopologyBuilder.view.getAllViews());
	    	}
	    }
	   
	    /*
	     * (non-Javadoc)
	     * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	     */
	    public boolean isCellEditable(int rowIndex, int columnIndex) {
	    	// TODO Auto-generated method stub
	    	if (columnIndex==1 )
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
