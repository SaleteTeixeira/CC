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
    private MonitorUDP monitor;
    
    public WriteMonitor(DatagramSocket sc, MonitorUDP m){
        this.s = sc;
        this.monitor = m;
    }
    
    public void run(){
        long timeStamp;
        
        try {
            while(true){
                System.out.println("Vamos começar.");
                sleep(10000);
                InetAddress addr = InetAddress.getByName("239.8.8.8");
                
                PDUma msg = new PDUma();
                DatagramPacket dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,addr,8888);
                
                //ESTA PORCARIA NAO FUNCIONAAAAAAAAAAAAAAAAAAAAAAAAAA
                timeStamp = System.currentTimeMillis();
                System.out.println("INICIAL "+timeStamp);
                
                this.s.send(dp);
                System.out.println("Enviei pedido de info aos Agentes.");
                this.monitor.setTimeStamp(timeStamp);
            }
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(WriteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
