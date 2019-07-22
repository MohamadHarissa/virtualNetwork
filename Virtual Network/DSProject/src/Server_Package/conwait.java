/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Package;

import com.sun.xml.internal.ws.server.ServerSchemaValidationTube;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mehdi
 */
public class conwait extends Thread {
    
    private vRouter router;
    private int port;
    ServerSocket ss;
  
    public conwait(ServerIF router,int port) throws RemoteException, IOException
    {
        this.router = (vRouter) router;
        this.port=port;
        ss = new ServerSocket(port);
      
        this.start();
    }
    
    public void run()
    {
        String creds;
        while(true){
            
            int ret=1;
            if( this.router.sockets.size()<4){
            try {
                Boolean isrouter=false;
                Socket s = ss.accept();
                Scanner sc = new Scanner(s.getInputStream());
                creds = sc.nextLine();
                System.out.println("in conwait-->" +creds);
                this.router.sockets.put(creds.split("#")[0]  , s);
                if(creds.split("#")[0].split("\\.")[1].equals("0")){
                    //System.out.println("check router ");
                    //this.router.getroutercomun().connect(creds.split("#")[0], creds.split("#")[1], Integer.parseInt(creds.split("#")[2]),false); // parameters got by the nexline from the other side
                    isrouter=true;
                    ret=0;
                }
                    System.out.println("conwait is connected with " + creds.split("#")[0]);
                    ClientComun cc = new ClientComun(this.router,s,isrouter,this.router.outs,creds.split("#")[0],ret);
                    System.out.println("CLientComun is created");
                    //ServerIF connectedRouter=this.router.connectedRouters.get(creds.split("#")[0]);
                //this.router.getServerSocket().put(sip, ss);

                //this.router.ssockets.put(sip, ss);
                //this.router.getroutercomun().rt.rt.put(sip, "0");
                //PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
            } catch (Exception ex) {
                Logger.getLogger(conwait.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            else{
                try {
                    sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(conwait.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
    }
}
