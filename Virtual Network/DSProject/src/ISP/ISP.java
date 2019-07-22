package ISP;
import Server_Package.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Client_Package.ClientIF;
import Client_Package.ClientIF;
import java.util.*;

public class ISP extends UnicastRemoteObject implements IISP {

    private final HashMap<ServerIF , HashMap<String,Integer>> routers;
    private static int sip_count = 0;
    private static int port = 6000; 

    
    public ISP() throws RemoteException {
        routers = new HashMap<>();
        //routers = new ArrayList<>();
    }
    
    public int getrcount() throws RemoteException
    {    
        return this.routers.size();
    }
    
    public String getsipbyusername(String input) throws RemoteException
    {
        for(Map.Entry<ServerIF , HashMap<String , Integer> > en : this.routers.entrySet()){
            if(en.getKey().getusername().equals(input))
                return en.getKey().getAvsip();
        }
        return null;
        
    }
    
    public HashMap<String , Integer> generatesip()
    {
        HashMap<String , Integer> sip = new HashMap<>();
                    
            for(int k = 0; k<4 ; k++)
            {
                sip.put((sip_count++) + ".0", 1);
            }
        return sip;      
    }

    @Override
    public synchronized HashMap<String , Integer> registerRouter(ServerIF router)throws RemoteException {
        
                
        //this.routers.add(router);
        
        HashMap<String , Integer> sip = this.generatesip();
        this.routers.put(router , sip);
        router.setSIP(sip);
        for(Map.Entry<ServerIF , HashMap<String , Integer> > en : this.routers.entrySet()){
            System.out.println(en.getKey().getSIP().values()+ "->>" + en.getValue().keySet());
        }
        return sip;
    }

    public synchronized void broadcastMessage(String message)throws RemoteException {
        
        for(Map.Entry<ServerIF,HashMap<String , Integer>> en : this.routers.entrySet())
        {
            en.getKey().broadcastMessage(message);
        }
    }

    public void disconnectRouter(ServerIF router) throws RemoteException {
        //routers.remove(router);
        routers.remove(router);
    }
    
    
    @Override
    public HashMap<ServerIF , HashMap<String , Integer>> getAvailableRouters(){
        return routers;
    }
    
    @Override
    public ArrayList<ServerIF> getAvailableRouterss() throws RemoteException{
        ArrayList<ServerIF> temp =  new ArrayList<>();
        for(Map.Entry< ServerIF , HashMap<String , Integer>> en : this.routers.entrySet()  ) 
            temp.add(en.getKey());
        return temp;
    }

    /*@Override
    public int[][] generateports() throws RemoteException {
        int[][] ports = {{this.port + 1 , this.port + 2 , this.port + 3 , this.port + 4 },{1,1,1,1}};
        port = port+4;
        return ports;
        
    }*/

    /**
     *
     * @return
     * @throws RemoteException
     */

    
    public synchronized int generateport() throws RemoteException {
        
        return (++this.port);

    }
}
