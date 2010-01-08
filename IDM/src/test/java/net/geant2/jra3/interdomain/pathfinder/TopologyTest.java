package net.geant2.jra3.interdomain.pathfinder;

import java.util.List;
import java.util.ArrayList;

import net.geant2.jra3.network.AdminDomain;
import net.geant2.jra3.network.Link;
import net.geant2.jra3.network.Node;

public class TopologyTest implements Topology {

    List<AdminDomain> ads;
    List<Link> ls;
    List<Node> ns;

   /*  public TopologyTest(List<AdminDomain> ads, List<Link> ls, List<Node> ns) {
        this.ads = new ArrayList<AdminDomain>(ads);
        this.ls = new ArrayList<Link>(ls);
        this.ns = new ArrayList<Node>(ns);
    } */
    
    public List<AdminDomain> getDomains() {
        return ads;
    }

    public List<Link> getLinks() {
        return ls;
    }

    public List<Node> getNodes() {
        return ns;
    }

    public boolean insertLink(Link link) {
        ls.add(link);
        return true;
    }

    public boolean removeLink(Link link) {
        ls.remove(link);
        return true;
    }

}
