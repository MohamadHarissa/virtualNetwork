/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Package;

import Client_Package.AppendingObjectOutputStream;
import Client_Package.Packet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohamad
 */
public class ClientComun extends Thread{
    Boolean run=true;
    ObjectInputStream in;
    ServerIF router;
    Boolean isRouter;
    ObjectOutputStream out;
    HashMap<String,ObjectOutputStream> outs;
    
  public ClientComun(ServerIF router ,Socket socket,Boolean isRouter, HashMap<String,ObjectOutputStream> outs,String sip,int inv) throws IOException, InterruptedException{
     
      this.router = router;
      if(inv == 1)
      {
          Thread.sleep(1000);
          out=new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            System.out.println("out created");
            in=new ObjectInputStream(socket.getInputStream());
            System.out.println("in created");
      }
      else
      {
            in=new ObjectInputStream(socket.getInputStream());
            System.out.println("in created");
            out=new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            System.out.println("out created");   
      }
      
      this.isRouter=isRouter;
      this.outs=outs;
      outs.put(sip, out);
      this.start();
  }  
    
  public void run(){
      while(run){
          try {
              Packet p;
              if(in!=null){
              p=(Packet)in.readObject();
              System.out.println(p.getMessage()+" "+p.getSource()+" "+p.getDistination());
          if(p.getMessage().equals("disconnect"))
          {
              System.out.println("Bye Bye");
              p.setMessage("Bye Bye");
              out.writeObject(p);
              run=false;
          }
          else
              if(p.getMessage().split("$")[0].equals("routingtable"))
              {
                  this.router.getroutercomun().rt.updateRt(this.StringToList(p.getMessage()));
               }
          else if(p.getMessage().equals("connect"))
          {
              System.out.println("Welcome "+p.getSource());
              p.setMessage("Welcome "+p.getSource());
              out.writeObject(p);
              
          }
          else{
              
              ObjectOutputStream temp=outs.get(p.getDistination());
              if(temp!=null){
                
             // temp.reset();
              temp.writeObject(p);
              //temp.flush();
              
              }
              else{
                  int first=Integer.parseInt(p.getDistination().split("\\.")[0]);
                  System.out.println(first+".0");
                  int index=first/4;
                  index=index*4;
                  Boolean found=false;
                  Set<String> keys = outs.keySet();
                for(String key: keys){
                    System.out.println(key);
                }
                for(int i=0;i<4;i++){
                    
                    temp=outs.get(index+".0");
                   if(temp!=null){
                         System.out.println("distination gateway found");
                  //temp.reset();
                  found=true;
                   temp.writeObject(p);
                   break;
                }
                   index++;
                  // temp.flush();
                   //temp.close();
                   }
                   if(!found)
                   {
                       System.out.println("not found");
                        p.setMessage("Distination unreachable");
                         out.writeObject(p);
                   }
              }
          }
         }
              else
                  sleep(100);
          } catch (Exception ex) {
              Logger.getLogger(ClientComun.class.getName()).log(Level.SEVERE, null, ex);
          }         
      }
  }
   
  public ArrayList<routecolon> StringToList(String hts)
                  {
                      String data = hts.split("$")[1];
                        ArrayList<routecolon> rt = new ArrayList<>();
                        String[] rts = data.split("#");
                        for(int i = 0 ; i< rts.length ; i++)
                            rt.add(new routecolon(rts[i].split(",")[0], rts[i].split(",")[1], Integer.parseInt(rts[i].split(",")[2])));
                                return rt;      
                    }
   public void sendback(Packet p) throws IOException{
       int first=Integer.parseInt(p.getDistination().split("\\.")[0]);
       ObjectOutputStream temp=outs.get(p.getDistination());
                  System.out.println(first+".0");
                  int index=first/4;
                  index=index*4;
                  Boolean found=false;
                  Set<String> keys = outs.keySet();
                for(String key: keys){
                    System.out.println(key);
                }
                for(int i=0;i<4;i++){
                    
                    temp=outs.get(index+".0");
                   if(temp!=null){
                         System.out.println("distination gateway found");
                  //temp.reset();
                  found=true;
                   temp.writeObject(p);
                   break;
                }
                   index++;
                  // temp.flush();
                   //temp.close();
                   }
                   if(!found)
                   {
                       System.out.println("not found");
                        p.setMessage("Distination unreachable");
                         out.writeObject(p);
                   }
  }
  
}
