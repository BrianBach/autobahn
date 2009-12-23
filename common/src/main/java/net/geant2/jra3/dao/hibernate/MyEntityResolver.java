package net.geant2.jra3.dao.hibernate;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MyEntityResolver implements EntityResolver {
	
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {

		if (publicId.contains("Hibernate Mapping")) {
			return new InputSource(HibernateUtil.class.getClassLoader()
					.getResourceAsStream("hibernate-mapping-3.0.dtd"));
		} else if (publicId.contains("Hibernate Configuration")) {
			return new InputSource(HibernateUtil.class.getClassLoader()
					.getResourceAsStream("hibernate-configuration-3.0.dtd"));
		}

		return null;
	}
}
