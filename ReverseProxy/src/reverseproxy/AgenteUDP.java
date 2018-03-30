/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

/**
 *
 * @author isabel, francisco, salete
 */
public class AgenteUDP  {
    private int id;
    
    public AgenteUDP(int id){
        this.id=id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public static void main(String[] args){
        for(int i =0; i< args.length; i++)
            System.out.println(args[i]);
        
        try {
            //AgenteUDP a = new AgenteUDP(Integer.parseInt(args[1]));
            
            AgenteUDP a = new AgenteUDP(1);
            InetAddress group = InetAddress.getByName("239.8.8.8");
            MulticastSocket s = new MulticastSocket(8888);
            s.joinGroup(group);
            
            long ram, timeStamp;
            double cpu;
            
            while(true){
                byte[] aReceber = new byte[1024];
                DatagramPacket recv = new DatagramPacket(aReceber,aReceber.length);
                s.receive(recv);

                //HMAC

                if(aReceber.toString().equals("Send me information.")){
                    com.sun.management.OperatingSystemMXBean osMBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                    ram = osMBean.getFreePhysicalMemorySize();
                    cpu = osMBean.getProcessCpuLoad();
                    
                    PDUam msg = new PDUam(a.getId(),ram,cpu);
                    DatagramPacket dp = new DatagramPacket(msg.getBytes(),msg.getLength(),group,8888);
                    s.send(dp);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AgenteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}