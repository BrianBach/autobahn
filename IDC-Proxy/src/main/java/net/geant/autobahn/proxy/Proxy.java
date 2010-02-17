package net.geant.autobahn.proxy;

import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.WebParam;
import java.util.List;
import java.io.IOException;

import net.geant.autobahn.network.Link;

/**
 * Bridge between the proxy and IDM
 * @author Michal
 *
 */
@WebService(targetNamespace = "http://proxy.autobahn.geant.net/", name = "Proxy")
public interface Proxy {

    public static int PROXY_GET_TOPOLOGY = 1;
    public static int PROXY_CREATE_RESEVATION = 2;
    public static int PROXY_CANCEL_RESERVATION = 3;
    public static int PROXY_QUERY_RESERVATION = 4;
    public static int PROXY_LIST_RESERVATIONS = 5;
    public static int PROXY_MODIFY_RESERVATION = 6;
    public static int PROXY_NOTIFY_RESERVATION = 7;

    @WebResult(name = "links")
    List<Link> getTopology() throws IOException;

    @WebResult(name = "reservationInfo")
    ReservationInfo createReservation(@WebParam(name = "resInfo") ReservationInfo resInfo) throws IOException;

    void cancelReservation(@WebParam(name = "resID") String resID) throws IOException;

    @WebResult(name = "reservationInfo")
    ReservationInfo queryReservation(@WebParam(name = "resID") String resID) throws IOException;

    @WebResult(name = "reservations")
    List<ReservationInfo> listReservations() throws IOException;

    @WebResult(name = "possible")
    boolean modifyReservation(@WebParam(name = "resInfo") ReservationInfo resInfo) throws IOException;

    void Notify(@WebParam(name = "resInfo") ReservationInfo resInfo) throws IOException;
}
