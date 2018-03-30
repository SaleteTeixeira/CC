/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Random rand;
        int sec;
        long ram, timeStamp;
        double cpu;
        String pedido;
        InetAddress group;
        SocketAddress monitorA;
        
        try {
            //AgenteUDP a = new AgenteUDP(Integer.parseInt(args[1]));
            AgenteUDP a = new AgenteUDP(1);
            group = InetAddress.getByName("239.8.8.8");
            MulticastSocket s = new MulticastSocket(8888);
            s.joinGroup(group);
            
            while(true){
                System.out.println("Agente "+a.getId()+". Estou à escuta.");
                byte[] aReceber = new byte[1024];
                DatagramPacket recv = new DatagramPacket(aReceber,aReceber.length);
                s.receive(recv);
                
                monitorA = recv.getSocketAddress();
        
                System.out.println("Agente "+a.getId()+". Recebi uma mensagem.");
                
                pedido = new String(aReceber, "UTF-8");
                pedido = pedido.trim();
                
                if(pedido.equals("Send me information.")){
                    
                    rand = new Random();
                    sec = rand.nextInt(10);
                    sleep(sec);
                    
                    System.out.println("Agente "+a.getId()+". É do MonitorUDP a pedir informações.");
                    
                    com.sun.management.OperatingSystemMXBean osMBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                    ram = osMBean.getFreePhysicalMemorySize();
                    cpu = osMBean.getProcessCpuLoad();
                    
                    PDUam msg = new PDUam(a.getId(),ram,cpu);
                    DatagramPacket dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,monitorA);
                    s.send(dp);
                    
                    System.out.println("Agente "+a.getId()+". Enviei as minhas informações ao Monitor.");
                    System.out.println(ram+" "+cpu);
                }
            }
            
            //s.leaveGroup(group);
            //s.close();
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(AgenteUDP.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}