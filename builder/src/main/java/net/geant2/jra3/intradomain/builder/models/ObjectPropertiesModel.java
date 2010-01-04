package net.geant2.jra3.intradomain.builder.models;
import javax.swing.table.TableModel;

/**
 * Interface for object properties model
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface ObjectPropertiesModel {
	/**
	 * Sets object related with table properties model
	 * @param object object related with table properties model
	 */
	public void setUserObject (Object object);
	/**
	 * Gets object related with table properties model
	 * @return object object related with table properties model
	 */
	public Object getUserObject ();
	/**
	 * Removes object related with table properties model
	 */
	public void removeUserObject();
	/**
	 * Gets table model
	 * @return table model
	 */
	public TableModel getTableModel();
}
