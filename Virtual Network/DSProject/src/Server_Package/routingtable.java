/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Package;

import Client_Package.Packet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mehdi
 */
public class routingtable implements Serializable {

    public ArrayList<routecolon> rt;
    private  vRouter router;
    
    public routingtable(ServerIF router)
    {
        this.rt = new ArrayList<>();
        this.router = (vRouter)router;
    }
    
    public void updateRt(ArrayList<routecolon> rt)
    {
        System.out.println("updating routing table");
        
    }
    
    
    public void share() throws IOException
    {
        Packet p = new Packet(this.listToString(rt));
        for(Map.Entry<String , Socket> en : this.router.sockets.entrySet()) {
            if(en.getKey().split("\\.")[1].equals("0"))
            {
                ObjectOutputStream out =new ObjectOutputStream(en.getValue().getOutputStream());
                out.writeObject(p);
                out.close();
                //PrintWriter pw = new PrintWriter(en.getValue().getOutputStream(),true);
                //pw.println();        
            }
        }
    }
    
    public String listToString(ArrayList<routecolon> rt)
    {
        String hts = "routingtable$";
        for(int i = 0 ; i<rt.size() ; i++)
        {
            hts = hts + rt.get(i).destination + "," + rt.get(i).nexthop +"," + rt.get(i).hopcount + "#";
        }
        
        return hts.substring(0, hts.length()-1);
        
    }
    
}
