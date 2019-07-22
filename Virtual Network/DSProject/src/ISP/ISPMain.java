package ISP;
import Server_Package.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class ISPMain {
    public static void main (String [] args) throws RemoteException, MalformedURLException {
      
              Registry r = LocateRegistry.createRegistry(123);
              ServerGUIIsp g = new ServerGUIIsp();
              r.rebind("ISP",new ISP());
     
    }
}
