/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Package;

import java.io.Serializable;

/**
 *
 * @author mehdi
 */
public class routecolon implements Serializable {
    public String destination;
    public String nexthop;
    public int hopcount;
    
    public routecolon(String des ,String nx , int hc )
    {
        this.destination = des;
        this.nexthop = nx;
        this.hopcount = hc;
    }
    
}
