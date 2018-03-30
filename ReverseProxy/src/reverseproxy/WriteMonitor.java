package reverseproxy;


import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author salete
 */
public class WriteMonitor implements Runnable{
    private DatagramSocket s;
    
    public WriteMonitor(DatagramSocket sc){
        this.s = sc;
    }
    
    public void run(){
        try {
            sleep(5000);
            InetAddress addr = InetAddress.getByName("239.8.8.8");
            
            PDUma msg = new PDUma();
            DatagramPacket dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,addr,8888);
            this.s.send(dp);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(WriteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
