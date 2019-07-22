/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Package;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mehdi
 */
public class routercomun extends UnicastRemoteObject implements Runnable {
 
    private vRouter router;
    private HashMap<String,PrintWriter> pws;
    private HashMap<String,Scanner> scs;
    public routingtable rt;
    
    
    
    
    public routercomun(ServerIF router) throws IOException
    {
        this.router = (vRouter) router;
        this.rt = new routingtable(this.router);
        this.pws=new HashMap<>();
        this.scs=new HashMap<>();
        Thread t = new Thread(this);
        t.start();
    }
    
    public boolean connect(String sip ,String ip , int port) throws IOException, InterruptedException
    {
        //System.out.println("in Connect");
        String avsip = this.router.getAvsip();
        System.out.println(" in connect function : router : " + this.router.username + " sip : "+avsip);
        Socket s= new Socket(ip,port);
        this.router.sockets.put(sip , s);
        PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
        this.pws.put(sip, pw);
        Scanner sc = new Scanner(s.getInputStream());
        this.scs.put(sip, sc);
        pw.println(avsip + "#" + this.router.cw.ss.getInetAddress().getHostAddress() + "#" + this.router.port);
        ClientComun cc = new ClientComun(this.router,s,(avsip.split("\\.")[1].equals("0")? true:false),this.router.outs,sip,1);
        System.out.println(avsip + " connected to " + sip);
        return true ;

    }

    
    @Override
    public void run() {
        
        while(true)
        {
            try {
                sleep(30000);
                //this.rt.share();
                //System.out.println("sharing");
                
            } catch (Exception ex) {
                Logger.getLogger(routingtable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
