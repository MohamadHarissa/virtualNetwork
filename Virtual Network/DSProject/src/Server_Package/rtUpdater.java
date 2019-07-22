/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Package;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author mehdi
 */
public class rtUpdater extends Thread {
    
    private Scanner sc; 
    private PrintWriter pw;
    
    
    public rtUpdater(Socket s) throws IOException
    {
        this.sc = new Scanner(s.getInputStream());
        this.pw = new PrintWriter(s.getOutputStream(),true);
        this.start();
    }
                  
    public void run(){
        
        while(true)
        {
            String msg = this.sc.nextLine();
            
            System.out.println(msg);
            //HashMap<String ,String> rt = this.StringToHash(msg);
            //updatert(rt);
                      }
                  }
                  
                  public void updatert(HashMap<String, String> neww)
                  {
                      for(Map.Entry en : neww.entrySet())
                      {
                          
                      }
                      
                  }
                  
                  
    
}