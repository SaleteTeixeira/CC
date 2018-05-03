package reverseproxy;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread que de 10 em 10 segundos manda um pedido UDP aos Agentes
 * 
 * @author isabel, francisco, salete
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
        InetAddress addr;
        
        System.out.println("MONITOR: A inicializar.");
        
        try {
            while(true){
                sleep(10000);
                
                addr = InetAddress.getByName("239.8.8.8");
                PDUma msg = new PDUma();
                DatagramPacket dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,addr,8888);
                
                timeStamp = System.currentTimeMillis();
                
                this.s.send(dp);
                this.monitor.setTimeStamp(timeStamp);
                
                System.out.println("MONITOR: Enviei pedido de info aos Agentes.");
                
                this.monitor.getTabela().removerServidorTab();
            }
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(WriteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
