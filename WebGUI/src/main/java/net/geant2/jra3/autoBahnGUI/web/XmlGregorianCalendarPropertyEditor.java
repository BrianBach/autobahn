package net.geant2.jra3.autoBahnGUI.web;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Property editor for bean {@link XMLGregorianCalendar}
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 */
public class XmlGregorianCalendarPropertyEditor extends PropertyEditorSupport {
	/**
	 * Used simple date format
	 */
    private SimpleDateFormat simpleDateFormat;

    public XmlGregorianCalendarPropertyEditor(String dateFormat) {
        simpleDateFormat = new SimpleDateFormat(dateFormat);
    }

    /**
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    public String getAsText() {
        XMLGregorianCalendar calendar = (XMLGregorianCalendar) getValue();
        if (calendar != null) {
            Date date = calendar.toGregorianCalendar().getTime();
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }

    /**
     * @see java.beans.PropertyEditorSupport#setAsText(String)
     */
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null && text.trim().equals("") == false) {
            try {
                Date birthDate = simpleDateFormat.parse((String) text);
                GregorianCalendar birthCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
                birthCalendar.setTime(birthDate);
                setValue(DatatypeFactory.newInstance().newXMLGregorianCalendar(birthCalendar));
            } catch (Exception ex) {
                setValue(null);
            }
        } else {
            setValue(null);
        }
    }
}