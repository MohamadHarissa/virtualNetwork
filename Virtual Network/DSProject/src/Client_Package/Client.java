package Client_Package;
import ISP.IISP;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Server_Package.ServerIF;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends UnicastRemoteObject implements ClientIF,Runnable {
    Boolean trytoread=false;
    Socket connect;
  
    ObjectInputStream in;
    ObjectOutputStream out;
    Boolean Connected=false;    
int i=0;
    Boolean isrunning=true;
    private ServerIF router;
    private String sip = null;
    Boolean candissconnect=false;
    Client(String name, ServerIF router)throws RemoteException, IOException, InterruptedException {
        
        this.sip = name;
        this.router = router;
        Connect(router);
        router.registerChatClient(this);
        Connected=true;
    }

    public void getMessage(Packet p) throws RemoteException {
        System.out.println(p.getMessage());
        if(!p.getMessage().trim().equals(""))
        ClientGUI.area.append("\n\n"+p.getSource()+" Says : " + p.getMessage());
    }

    public void run() {
      //  int i=0;
        //noinspection InfiniteLoopStatement
        while (isrunning) {
            //System.out.println("started");
          
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(in!=null){
            
            
            try {
               trytoread=true;
               Packet p=(Packet)in.readObject();
               getMessage(p);
               if(p.getMessage().equals("Bye Bye"))
                   candissconnect=true;
               trytoread=false;
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
        System.out.println("dead");
        
       return;
    }



    public void Disconnect() throws RemoteException, InterruptedException, IOException {
        if(Connected){
            String host=sip.split("\\.")[0]+".0";
            Packet p=new Packet(this.sip,host,"disconnect",true);
            out.writeObject(p);
           Connected=false;
            router.disconnectClient(this);
            System.out.println("disconnect");
            while(!candissconnect)
            sleep(100);
            candissconnect=true;
                out=null;
                in=null;
        }
        
    }
      public synchronized void ChangeRouter(ServerIF router) throws RemoteException, InterruptedException, IOException {
          System.out.println("starting connect");
             sleep(100);
             System.out.println("change router");
            this.router = router;
             this.router.registerChatClient(this);
           Connect(router);
           Connected=true;
            i++;
      // isrunning=false;
            
        
    }
        public void setSIP(String sip)throws RemoteException{
        this.sip=sip;
    }
    public String getSIP()throws RemoteException{
        return sip;
    }
public void Connect(ServerIF router) throws RemoteException, IOException, InterruptedException{
    System.out.println("connecting to "+router.getInetAddress()+" port "+router.getport());
    connect=new Socket(router.getInetAddress(),router.getport());
    System.out.println("socket get");
    PrintWriter pw=new PrintWriter(connect.getOutputStream(),true);
    System.out.println("pw creaetd");
    while(this.sip.split("\\.")[1].equals("0"))
    {
        this.sip=this.sip.split("\\.")[0]+".1";
        sleep(100);
    }
    System.out.println("this sip "+this.sip);
    pw.println(this.sip.split("\\.")[0]+".1");
    
    in=new ObjectInputStream(connect.getInputStream());
    System.out.println("ObjectReader created");
    out=new ObjectOutputStream(connect.getOutputStream());
    System.out.println("ObjectWriter created");
     System.out.println(this.sip);
     Packet p=new Packet(this.sip,"","connect",true);
     out.writeObject(p);
  
}
public void send(String message,String distination) throws IOException{
    if(out!=null){
        Boolean returns=false;
    if(message.split(" ")[0].equals("ping")||message.split(" ")[0].equals("tracerout")){
        returns=true;
        
        
    }
    Packet p;
    if(distination.trim().equals("")){
    p=new Packet(this.sip,this.sip,message,returns);
        System.out.println("message to self");
    }
    else
        p=new Packet(this.sip,distination,message,returns);
    out.writeObject(p);
    if(p.getReturn()){
       String dist= p.getDistination();
       p.setSource(p.getDistination());
       p.setSource(dist);
        getMessage(p);
    }
    ClientGUI.field.setText("");
    }
    
}
}
