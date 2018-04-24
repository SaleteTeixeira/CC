/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isabel, francisco, salete
 */
public class MonitorUDP {
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
    
    public static void main(String[] args) throws InterruptedException{
        int portaS=-1;
        long ram=-1, timeStampF=-1, rtt=-1;
        double cpu=-1, larguraBanda=0;String aux;
        String[] partes;
        byte[] buf = new byte[1024];
        InetAddress ipS;
        
        try {
            MonitorUDP m = new MonitorUDP();
            DatagramSocket s = new DatagramSocket();
            
            Thread tr = new Thread(new WriteMonitor(s,m));
            tr.start();
            
            while(true){
                System.out.println("Pronto a receber.");

                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                s.receive(recv);
                timeStampF = System.currentTimeMillis();
                
                System.out.println("Recebi info para atualizar a tabela.");

                aux = new String(buf, "UTF-8");
                aux = aux.trim();
                partes = aux.split(";");

                ram = Long.parseLong(partes[0]);
                cpu = Double.parseDouble(partes[1]);
                rtt = timeStampF - m.getTimeStamp();
                
                if(rtt!=0) larguraBanda = (buf.length * 8) / rtt;

                portaS = recv.getPort();
                ipS = recv.getAddress();

                m.getTabela().atualizaTabela(portaS, ipS, ram, cpu, rtt, larguraBanda);

                System.out.println("Acabei de atualizar a tabela.");
            }
        } catch (IOException ex) {
            Logger.getLogger(MonitorUDP.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}