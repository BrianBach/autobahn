package net.geant2.jra3.intradomain.builder;

import javax.swing.table.AbstractTableModel;

/**
 * Table model for not recognized objects
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 * 
 */
public class EmptyPropertyModel extends AbstractTableModel {
	/**
	 * Names for columns
	 */
	private static final String columnNames[] = {
			MessagesProvider.getMessage("table.property"),
			MessagesProvider.getMessage("table.value") };
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
}
