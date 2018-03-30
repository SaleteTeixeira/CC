/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

/**
 *
 * @author isabel, francisco, salete
 */
public class PDUma {
    private String msg;
    
    public PDUma(){
        this.msg="Send me information.";
    }
    
    public PDUma(String s){
        this.msg=s;
    }
        
    public byte[] getBytes(){
        return this.msg.getBytes();
    }
            
    public int getLength(){
        return this.msg.length();
    }
}
