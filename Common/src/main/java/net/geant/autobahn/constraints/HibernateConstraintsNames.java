package net.geant.autobahn.constraints;

import net.geant.autobahn.dao.hibernate.IntEnumUserType;

public class HibernateConstraintsNames extends IntEnumUserType<ConstraintsNames> {

	public HibernateConstraintsNames() { 
        // we must give the values of the enum to the parent.
        super(ConstraintsNames.class, ConstraintsNames.values()); 
    } 
}
