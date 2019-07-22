/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client_Package;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author mohamad
 */
public class Packet implements Serializable{
    private String src;
    private String dis;
    private int hop;
    private String message;
    private Vector<String> path;
    private Boolean direction;
    private Boolean returns;
    
    public Packet(String msg){
    this.message = msg;
    }
    
    public Packet(String src,String dist,String message,Boolean returns){
        this.src=src;
        this.dis=dist;
        hop=0;
        this.message=message;
        path=new Vector<>();
        direction=true;
        this.returns=returns;
        
    }
     public String getSource(){
     return this.src;
     }
    public String getDistination(){
     return this.dis;
     }
    public String getMessage(){
     return this.message;
     }
    public Boolean getDirection(){
       return this.direction;
    }
    
    public Boolean getReturn(){
       return this.returns;
    }
    public int getHop(){
       return this.hop;
    }
    
    public Vector<String> getPath(){
        return path;
    }
    
     public void setSource(String src){
     this.src=src;
     }
    public void setDistination(String dist){
     this.dis=dist;
     }
   
    public void setDirection(Boolean direct){
       this.direction=direct;
    }
    
    public void setHop(int hop){
       this.hop=hop;
    }
    public void setMessage(String Message){
        this.message=Message;
    }
      public void setReturn(Boolean returns){
        this.returns=returns;
    }
}
