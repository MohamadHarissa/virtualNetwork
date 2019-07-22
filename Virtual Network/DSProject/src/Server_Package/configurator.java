/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Package;

import java.rmi.RemoteException;

/**
 *
 * @author mehdi
 */
public class configurator {

        
    private boolean ripOn ;
    private ServerIF router;
    
    public configurator(String command , ServerIF router)
    {
        ripOn  = false;
        this.router = router;
    }
    
    public void config(String input) throws RemoteException
    {
        String[] splitted = input.toLowerCase().split(" ");
            switch(splitted[0])
          {
              case "route":
              {
                  if(!splitted[1].equals("rip"))
                  {
                      System.out.println("protocol not implemented.");
                  }
                  else      
                  {
                   ripOn = true; 
              
                  }
          }
              case "network":
              {
                  if(ripOn)
                  {
                   //this.router.;
                  this.router.getroutercomun().rt.rt.add(new routecolon(splitted[1],"d",1));            
                  }
                  else
                      System.out.println("missing routing protocol initialization.");
                  
              }
              
              case "traceroute":
              {
                  rPath(splitted[1]);
              }
              
              case "ping":
              {
                  sendPing(splitted[1]);  
              }
              
              default:
                  System.out.println("command not recognized.");
            
          }        
    }
    
    public void sendPing(String sip)
    {
        
    }
    
    public void rPath(String sip)
    {
        
    }

    
}
