package net.geant2.jra3.intradomain.builder;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Class allow rendering the boolean property radio button in table
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 */
public class BooleanClassRanderer extends DefaultCellEditor implements
		TableCellRenderer, ItemListener {
	
	private static final long serialVersionUID = 2070299681109330742L;
	/**
	 * Radion button
	 */
	private JRadioButton button;

	/**
	 * Creates the BooleanClassRanderer
	 * 
	 * @param checkBox
	 *            use check box
	 */
	public BooleanClassRanderer(JCheckBox checkBox) {
		super(checkBox);
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null)
			return null;
		button = (JRadioButton) value;
		button.addItemListener(this);
		return (Component) value;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		button.removeItemListener(this);
		return button;
	}
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
			return null;
		return (Component) value;
	}
}
