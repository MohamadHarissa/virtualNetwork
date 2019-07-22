package Server_Package;
import ISP.IISP;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class ServerDriver {

    public static void main (String [] args) throws RemoteException, MalformedURLException, NotBoundException, IOException {
      String ISPURL = "ISP";
        Registry r=LocateRegistry.getRegistry("localhost",123);
      
       // r.rebind("RMIChatServer",new vRouter());
         IISP isp=(IISP) r.lookup(ISPURL);
            
         vRouter v = new vRouter(isp);
         v.setSIP(isp.registerRouter(v));
         v.isp = isp;
        //System.out.println(v.getSIP());
        //v.prefix = Integer.parseInt(v.getSIP().split("\\.")[0]);
        //System.out.println(v.prefix);
         ServerGUI g=new ServerGUI(isp,v);
       g.show();
         
     
    }
}
