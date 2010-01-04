package net.geant2.jra3.intradomain.builder.models;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.geant2.jra3.intradomain.builder.AutobahnTopologyBuilder;
import net.geant2.jra3.intradomain.builder.MessagesProvider;
import net.geant2.jra3.intradomain.sdh.StmLink;

/**
 * Table properties model for {@link StmLink}
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class SDHLinkTableModel extends DefaultTableModel implements
		ObjectPropertiesModel {
	
	private static final long serialVersionUID = 2082371351284811619L;

	/**
	 * Table columns names
	 */
	private static final String columnNames[] = {
		MessagesProvider.getMessage("element.type"),
		MessagesProvider.getMessage("sdh.link") };

	/**
	 * Object related with properties model
	 */
	StmLink stmLink;

	/**
	 * Creates SDHLinkTableModel object
	 */
	public SDHLinkTableModel() {
		columnIdentifiers
				.add(MessagesProvider.getMessage("generic.link.delay"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.link.protection"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("generic.link.direction"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("sdh.link.och.payload"));
		columnIdentifiers.add(MessagesProvider
				.getMessage("sdh.link.och.status"));

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
	 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#setUserObject(java.lang.Object)
	 */
	public void setUserObject(Object object) {

		if (object == null) {
			int length = columnIdentifiers.size();
			for (int i = 1; i < length; i++)
				dataVector.add("");
			stmLink = null;
			return;

		}

		StmLink link = null;
		try {
			link = (StmLink) object;
		} catch (ClassCastException e) {
			setUserObject(null);
			return;
		}

		stmLink = link;

		dataVector.removeAllElements();
		dataVector.add(link.getStmLink().getPropDelay());
		dataVector.add(link.getStmLink().getProtection());
		dataVector.add(link.getStmLink().getDirection());
		dataVector.add(link.getOch().getPayload());
		dataVector.add(link.getOch().getStatus());

	}
	/*
	 * (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.builder.models.ObjectPropertiesModel#getUserObject()
	 */
	public Object getUserObject() {
		if (stmLink != null)
			return stmLink;
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
			if (stmLink == null)
				return;
			dataVector.setElementAt(value, rowIndex);

			switch (rowIndex) {
			case 0:
				stmLink.getStmLink().setPropDelay(
						ModelTool.parseDouble(stmLink.getStmLink().getPropDelay(),
								(String) value));
				break;
			case 1:
				stmLink.getStmLink().setProtection(
						ModelTool.parseBoolean(stmLink.getStmLink().getProtection(),
								(String) value));
				dataVector
						.setElementAt(stmLink.getStmLink().getProtection(), 1);
				break;
			case 2:
				stmLink.getStmLink().setDirection((String) value);
				break;
			case 3:
				stmLink.getOch().setPayload((String) value);
				break;
			case 4:
				stmLink.getOch().setStatus((String) value);
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
