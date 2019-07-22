package Server_Package;
import Client_Package.ClientIF;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ServerIF extends Remote {
    void registerChatClient(ClientIF chatClient) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
    void disconnectClient(ClientIF client) throws RemoteException;
    HashMap<String , Integer> getSIP()throws RemoteException;;
    void setSIP(HashMap<String , Integer> sip)throws RemoteException;
    HashMap<String , ClientIF> getconnected() throws RemoteException;
    void sendmessage(String message ,String sip) throws RemoteException;
    routercomun getroutercomun() throws RemoteException ;
    //int get1port() throws RemoteException;
    public String getAvsip() throws RemoteException;
     public HashMap<String,ServerSocket>getServerSocket()throws RemoteException;
     public String getusername() throws RemoteException;
     public int getport() throws RemoteException;
     public void FreeSip(String sip) throws RemoteException;
     public InetAddress getInetAddress() throws RemoteException;
     public void StartClientComun(Socket socket,Boolean isRouter, HashMap<String,ObjectOutputStream> outs,String sip,int inv) throws RemoteException,IOException;
      public HashMap<String,ObjectOutputStream> getOut() throws RemoteException;
}
