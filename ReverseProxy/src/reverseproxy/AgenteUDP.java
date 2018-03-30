/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author isabel, francisco, salete
 */
public class AgenteUDP  {
    private int id;
    private ReentrantLock lock;
    private Condition receber;
    
    public AgenteUDP(int id){
        this.id=id;
        this.lock = new ReentrantLock();
        this.receber = lock.newCondition();
    }
    
    public int getId(){
        return this.id;
    }
    
    public ReentrantLock getLock(){
        return this.lock;
    }
    
    public Condition getReceber(){
        return this.receber;
    }
    
    public void lock(){
        this.lock.lock();
    }
    
    public void unlock(){
        this.lock.unlock();
    }
    
    public static void main(String[] args){
        long ram, timeStamp;
        double cpu;
        
        for(int i =0; i< args.length; i++)
            System.out.println(args[i]);
        
        try {
            //AgenteUDP a = new AgenteUDP(Integer.parseInt(args[1]));
            AgenteUDP a = new AgenteUDP(1);
            InetAddress group = InetAddress.getByName("239.8.8.8");
            MulticastSocket s = new MulticastSocket(8888);
            s.joinGroup(group);
            
          
            Thread tr = new Thread(new LerAgente(s,a.getLock(),a.getReceber()));
            tr.start();
            
            while(true){
                    a.getReceber().await();
                
                    com.sun.management.OperatingSystemMXBean osMBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                    ram = osMBean.getFreePhysicalMemorySize();
                    cpu = osMBean.getProcessCpuLoad();
                    
                    PDUam msg = new PDUam(a.getId(),ram,cpu);
                    DatagramPacket dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,group,8888);
                    s.send(dp);
            }
            
            //tr.join();
            //s.leaveGroup(group);
            //s.close();
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(AgenteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}