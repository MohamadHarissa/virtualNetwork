package Client_Package;
import ISP.IISP;
import Server_Package.ServerIF;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientIF extends Remote {
    void getMessage(Packet p) throws RemoteException;
    void setSIP(String sip)throws RemoteException;
    String getSIP()throws RemoteException;
}
