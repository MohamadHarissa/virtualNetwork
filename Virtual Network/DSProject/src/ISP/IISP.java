package ISP;
import Server_Package.*;
import Client_Package.ClientIF;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IISP extends Remote {
    HashMap<String , Integer> registerRouter(ServerIF router) throws RemoteException;
   // void broadcastMessage(String message) throws RemoteException;
    void disconnectRouter(ServerIF router) throws RemoteException;
    public HashMap<ServerIF , HashMap<String , Integer>> getAvailableRouters()throws RemoteException;
    public ArrayList<ServerIF> getAvailableRouterss() throws RemoteException;
    //public int[][] generateports() throws RemoteException;
    public int generateport() throws RemoteException;
    public int getrcount() throws RemoteException;
    public String getsipbyusername(String input) throws RemoteException;
}
