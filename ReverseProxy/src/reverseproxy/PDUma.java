package reverseproxy;

import java.net.InetAddress;

/**
 * Pacote PDU que define a informação enviada do Monitor a um Agente.
 * 
 * @author isabel, francisco, salete
 */
public class PDUma {
    private String msg;
    
    public PDUma(){
        this.msg = "Send me information.";
    }
    
    public PDUma(String s){
        this.msg = s;
    }
        
    public byte[] getBytes(){
        return this.msg.getBytes();
    }
}
