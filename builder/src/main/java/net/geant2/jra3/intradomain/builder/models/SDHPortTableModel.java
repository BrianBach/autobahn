package net.geant2.jra3.intradomain.builder.models;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.geant2.jra3.intradomain.builder.AutobahnTopologyBuilder;
import net.geant2.jra3.intradomain.builder.MessagesProvider;
import net.geant2.jra3.intradomain.sdh.SdhPort;

/**
* Table properties model for {@link SdhPort}
* 
* @author Lucas Dolata <ldolata@man.poznan.pl>
* 
*/
public class SDHPortTableModel extends DefaultTableModel implements ObjectPropertiesModel {
	
    /**
     * Table columns names
     */
    private static final String columnNames[] = { MessagesProvider.getMessage("element.type"),
        MessagesProvider.getMessage("sdh.port")};

	/**
	 * SdhPort realted with table model
	 */
	SdhPort sdhPort;
    /**
     * Creates SDHPortTableModel object
     */
	public SDHPortTableModel() {
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.name"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.bandwitdh"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.clientPort"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.status"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.mtu"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.domainId"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.description"));	
	    	columnIdentifiers.add(MessagesProvider.getMessage("sdh.interface.address"));
	    	columnIdentifiers.add(MessagesProvider.getMessage("sdh.interface.phyPortType"));	    	
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
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#setUserObject(java.lang.Object)
	 */
	 public void setUserObject(Object object) {
		 
	    	if (object == null)
	    	{
	    		int length = columnIdentifiers.size();
	    		dataVector.add(MessagesProvider.getMessage("sdh.interface.type"));
		    	for (int i=1;i<length;i++)
		    		dataVector.add("");
		    	sdhPort = null;
		    	return;
		    	
	    	}
	    	
	    	SdhPort device=null;
	    	try{ 
	    		device =(SdhPort)object;
	    	}catch (ClassCastException e){setUserObject (null);return;}	
	    	
	    	sdhPort = device;
	    	
	    	dataVector.removeAllElements();
	    	dataVector.add(device.getGenericInterface().getName());
	    	dataVector.add(device.getGenericInterface().getBandwidth());
	    	dataVector.add(device.getGenericInterface().isClientPort());
	    	dataVector.add(device.getGenericInterface().getStatus());
	    	dataVector.add(device.getGenericInterface().getMtu());
	    	dataVector.add(device.getGenericInterface().getDomainId());
	    	dataVector.add(device.getGenericInterface().getDescription());
	    	dataVector.add(device.getAddress());
	    	dataVector.add(device.getPhyPortType());
	    	
	    	
	    }
	 /*
	  * (non-Javadoc)
	  * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#getUserObject()
	  */
	    public Object getUserObject() {
	    	if (sdhPort!=null)
	    		return sdhPort;
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
	    	if (columnIndex==1){
	    		if (sdhPort== null)
	    			return;
	    		dataVector.setElementAt(value,rowIndex);
	    		columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.name"));
		    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.bandwitdh"));
		    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.clientPort"));
		    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.status"));
		    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.mtu"));
		    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.domainId"));
		    	columnIdentifiers.add(MessagesProvider.getMessage("generic.interface.description"));	
		    	columnIdentifiers.add(MessagesProvider.getMessage("sdh.interface.address"));
		    	columnIdentifiers.add(MessagesProvider.getMessage("sdh.interface.phyPortType"));
		    	String name=null;
	    		switch (rowIndex){
	    			case 0: 
	    				name = AutobahnTopologyBuilder.project.checkNames(sdhPort.getGenericInterface().getName(), (String)value);
	    				sdhPort.getGenericInterface().setName(name);
	    				dataVector.setElementAt(name, 0);
	    				break;
	    			case 1: sdhPort.getGenericInterface().setBandwidth(ModelTool.parseLong(sdhPort.getGenericInterface().getBandwidth(),(String)value));break;	    			
	    			case 2: sdhPort.getGenericInterface().setClientPort(ModelTool.parseBoolean(sdhPort.getGenericInterface().isClientPort(),(String)value));dataVector.set(2, sdhPort.getGenericInterface().isClientPort());break;
	    			case 3: sdhPort.getGenericInterface().setStatus((String)value);break;
	    			case 4: sdhPort.getGenericInterface().setMtu((String)value);break;
	    			case 5: sdhPort.getGenericInterface().setDomainId((String)value);break;
	    			case 6: sdhPort.getGenericInterface().setDescription((String)value);break;
	    			case 7: sdhPort.setAddress((String)value);break;
	    			case 8: sdhPort.setPhyPortType((String)value);break;
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
	    	if (columnIndex==1)
	    		return true;
	    	else
	    		return false;
	    }	
	    /*
	     * (non-Javadoc)
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
		 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#getTableModel()
		 */
	    public TableModel getTableModel() {
			return this;
		}		
}
