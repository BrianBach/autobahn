package net.geant.autobahn.framework.commands;

import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;

/**
 * @author kostas
 * 
 */
public class CancelServiceCommand implements AutobahnCommand {

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.geant.autobahn.framework.commands.AutobahnCommand#execute(net.geant
     * .autobahn.framework.Framework, java.lang.String[])
     */
    public String execute(Framework autobahn, String[] args) {

        if (args.length > 1) {
            if ("all".equalsIgnoreCase(args[1])) {
                autobahn.getIdm().cancelAllServices();
                return "All services cancelled";
            } else {
                try {
                    autobahn.getIdm().cancelService(args[1]);
                    return "Service " + args[1] + " cancelled";
                } catch (UserAccessPointException e) {
                    return "Service " + args[1] + " can not be cancelled: " + e.getMessage();
                }
            }
        } else {
            return "Incomplete command";
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.geant.autobahn.framework.commands.AutobahnCommand#commandInfo()
     */
    public String commandInfo() {
        return "Cancels services.\n"
                + "\t'cancel serv_id' to cancel the service with Id \"serv_id\"\n"
                + "\t'cancel all' to cancel all services submitted to this domain.";
    }
}
