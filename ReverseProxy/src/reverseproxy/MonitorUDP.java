package reverseproxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread que recebe pacotes PDU, provenientes dos Agentes, com as devidas informações e atualiza a Tabela.
 * 
 * @author isabel, francisco, salete
 */
public class MonitorUDP implements Runnable{
    private TabelaEstado tabela;
    private ReentrantLock lockTabela;
    private long timeStamp;
    
    public MonitorUDP(){
        this.tabela = new TabelaEstado();
        this.lockTabela = new ReentrantLock();
        this.timeStamp = 0;
    }
    
    public MonitorUDP(TabelaEstado t, ReentrantLock l){
        this.tabela = t;
        this.lockTabela = l;
        this.timeStamp = 0;
    }
    
    public TabelaEstado getTabela(){
        return this.tabela;
    }
    
    public long getTimeStamp(){
        return this.timeStamp;
    }
    
    public void setTimeStamp(long t){
        this.timeStamp = t;
    }
    
    public void run(){
        int portaS=-1;
        long ram=-1, timeStampF=-1, rtt=-1;
        double cpu=-1, larguraBanda=0;String aux;
        String[] partes;
        byte[] buf = new byte[1024];
        InetAddress ipS;
        
        try {
            DatagramSocket s = new DatagramSocket();
            
            Thread tr = new Thread(new WriteMonitor(s,this));
            tr.start();
            
            while(true){
                System.out.println("MONITOR: Pronto a receber.");

                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                s.receive(recv);
                timeStampF = System.currentTimeMillis();
                
                System.out.println("MONITOR: Recebi info para atualizar a tabela.");

                aux = new String(buf, "UTF-8");
                aux = aux.trim();
                partes = aux.split(";");

                ram = Long.parseLong(partes[0]);
                cpu = Double.parseDouble(partes[1]);
                larguraBanda = Double.parseDouble(partes[2]);
                rtt = timeStampF - this.timeStamp;

                portaS = recv.getPort();
                ipS = recv.getAddress();
                
                this.lockTabela.lock();
                
                this.tabela.atualizaTabela(portaS, ipS, ram, cpu, rtt, larguraBanda,timeStampF);
                System.out.println("MONITOR: Acabei de atualizar a tabela.");
                System.out.println(this.tabela.toString());
                
                this.lockTabela.unlock();
            }
        } catch (IOException ex) {
            Logger.getLogger(MonitorUDP.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}