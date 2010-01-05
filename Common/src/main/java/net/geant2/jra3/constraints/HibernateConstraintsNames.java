package net.geant2.jra3.constraints;

import net.geant2.jra3.dao.hibernate.IntEnumUserType;

public class HibernateConstraintsNames extends IntEnumUserType<ConstraintsNames> {

	public HibernateConstraintsNames() { 
        // we must give the values of the enum to the parent.
        super(ConstraintsNames.class, ConstraintsNames.values()); 
    } 
}
