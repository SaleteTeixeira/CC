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
 * Classe que define um Agente.
 * Este encontra-se sempre à escuta de pedidos por parte do Monitor, enviando-lhe as suas informações
 * 
 * .................
 * 
 * 
 * @author isabel, francisco, salete
 */
public class AgenteUDP  {
    
    public AgenteUDP(){
    }
    
    public static void main(String[] args){
        int sec;
        long ram;
        double cpu, larguraBanda;
        Random rand;
        String pedido;
        byte[] aReceber;
        InetAddress group;
        SocketAddress monitorA;
        
        try {
            AgenteUDP a = new AgenteUDP();
            
            group = InetAddress.getByName("239.8.8.8");
            MulticastSocket s = new MulticastSocket(8888);
            s.joinGroup(group);
            
            while(true){
                System.out.println("Estou à escuta.");
                
                aReceber = new byte[1024];
                DatagramPacket recv = new DatagramPacket(aReceber,aReceber.length);
                s.receive(recv);
                
                monitorA = recv.getSocketAddress();
        
                System.out.println("Recebi uma mensagem.");
                
                pedido = new String(aReceber, "UTF-8");
                pedido = pedido.trim();
                
                if(pedido.equals("Send me information.")){
                    System.out.println("É do MonitorUDP a pedir informações.");
                    
                    rand = new Random();
                    sec = rand.nextInt(10);
                    sleep(sec);
                    
                    com.sun.management.OperatingSystemMXBean osMBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                    ram = osMBean.getFreePhysicalMemorySize();
                    cpu = osMBean.getSystemCpuLoad();
                    larguraBanda = 1; //MAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL
                    
                    PDUam msg = new PDUam(ram,cpu,larguraBanda);
                    DatagramPacket dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,monitorA);
                    s.send(dp);
                    
                    System.out.println("Enviei as minhas informações ao Monitor.");
                }
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(AgenteUDP.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}