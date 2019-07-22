package Server_Package;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Client_Package.ClientIF;
import ISP.IISP;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class vRouter extends UnicastRemoteObject implements ServerIF {

    public String username;
    private HashMap<String , Integer> sips; 
    public HashMap<String,ServerIF> connectedRouters;
    public IISP isp;
    private HashMap<String , ClientIF> devices;
    public HashMap<String , Socket> sockets;
    public HashMap<String,ObjectOutputStream>outs;
    private static int client_count = 0;
    public routercomun rc;
    public int port; 
    public  conwait cw;

   
    
    public vRouter(IISP isp) throws RemoteException, IOException {
        try {
            this.rc = new routercomun(this);
            this.isp = isp;
            this.username = "Rou0"+this.isp.getrcount();
            devices = new HashMap<>();
            this.sockets = new HashMap<>();
            //this.ssockets = new HashMap<>();
            this.port = isp.generateport();
            this.cw = new conwait(this , port);
            outs=new HashMap<>();
            connectedRouters=new HashMap<>();
        } catch (IOException ex) {
            Logger.getLogger(vRouter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public int get1port() throws RemoteException
    {
        for(int i = 0; i<4 ; i++)
            if(this.ports[1][i] == 1)
                return this.ports[0][i];
        return -1;
    }*/

    @Override
    public void StartClientComun(Socket socket,Boolean isRouter, HashMap<String,ObjectOutputStream> outs,String sip,int inv) throws IOException,RemoteException{
        
        try {
            ClientComun c=new ClientComun(this,socket,isRouter,outs,sip,inv);
        } catch (InterruptedException ex) {
            Logger.getLogger(vRouter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public synchronized void registerChatClient(ClientIF device)throws RemoteException {
        String rsip = this.getAvsip();
        int rsip0=Integer.parseInt(rsip.split("\\.")[0]);
             String sip="";
        sip=rsip0+".1";
         
       /* for(int i=1;i<255;i++){
           sip=rsip+"."+i;
            for(Map.Entry<String,ClientIF> en : this.devices.entrySet()){
            if(sip.equals(en.getValue().getSIP())){
                break;
            }
            
            }
            if(j==this.devices.size())
                break;
        }
        */
        this.rc.rt.rt.add(new routecolon(sip,rsip,0));
        System.out.println("added to  routing table");
        
        showroutingtable(this);
        
        this.devices.put(sip,device);
        System.out.println(sip);
        device.setSIP(sip);
        client_count++;
    }

    public synchronized void broadcastMessage(String message)throws RemoteException {
        int i = 0;
        for(Map.Entry<String,ClientIF> en : this.devices.entrySet()) {
           // en.getValue().getMessage(message);
        }
    }
    
    public void showroutingtable(ServerIF router) throws RemoteException
    {
        for(int  i = 0; i < this.getroutercomun().rt.rt.size(); i++)
        {
            System.out.println("to : " + this.getroutercomun().rt.rt.get(i).destination + " by : " + this.getroutercomun().rt.rt.get(i).nexthop + " with hop count :" + this.getroutercomun().rt.rt.get(i).hopcount );
        }
    }
    
    public boolean sendfrom(String message ,String sip, ServerIF sif) throws RemoteException
    {
    /*
        int i = 0;
        
        while (i < sif.getconnected().size()) {
            if(sif.getconnected().get(i).getSIP().equals(sip))
            {
                sif.getconnected().get(i).getMessage(message);
                return true;
            }
    }
        */
        return false;
    }
       
        
    public void sendmessage(String message ,String sip) throws RemoteException
    {
        /*
        boolean sent = false;
        int i=0;
        if(sip.split("\\.")[0].equals(prefix))
        {
              sent = sendfrom(message,sip,this);
            i=0;
        }
            else
        {
                ArrayList<ServerIF> conrout = isp.getAvailableRouters();
                while(i<conrout.size())
                {
                    sent = sendfrom(message,sip,conrout.get(i));
                }    
            }
        if(!sent)
        {
            System.out.println("not sent");
        }*/
        }
       
    public HashMap<String,ServerSocket>getServerSocket()throws RemoteException{
        //return ssockets;
        return null;
    }
    public routercomun getroutercomun() throws RemoteException{
        return rc;
    }
    
    public int getport() throws RemoteException
    {
        return this.port;
    }
    
    public String getusername() throws RemoteException
    {
        return this.username;
    }
    public static int getClients() {
        return client_count;
    }
    public void disconnectClient(ClientIF device) throws RemoteException {
        devices.remove(device);
        String sip=device.getSIP().split("\\.")[0];
        String freeSip=sip+".0";
        this.FreeSip(freeSip);
        System.out.println("Free "+freeSip);
        sockets.remove(device.getSIP());
        System.out.println("removed client "+device.getSIP());
        client_count--;
    }
    public void setSIP(HashMap<String , Integer> sips)throws RemoteException{ 
        this.sips = sips;
    }
    public HashMap<String , Integer> getSIP()throws RemoteException{
        return this.sips;
    }
    
    public void FreeSip(String sip){
        this.sips.put(sip, 1);
    }
    public String getAvsip() throws RemoteException
    {
        for(Map.Entry<String , Integer> en : this.sips.entrySet())
            if(en.getValue().equals(1))
            {
                en.setValue(0);
                return en.getKey();
            }
        return null;
    }

    @Override
    public HashMap<String,ClientIF> getconnected() throws RemoteException {
        return this.devices;
    }
      public InetAddress getInetAddress() throws RemoteException {
        return this.cw.ss.getInetAddress();
    }
      public HashMap<String,ObjectOutputStream> getOut() throws RemoteException{
          return this.outs;
      }
}
