/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.Date;
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
    
    public MonitorUDP(){
        this.tabela = new TabelaEstado();
        this.lockTabela = new ReentrantLock();
    }
    
    public MonitorUDP(TabelaEstado t, ReentrantLock l){
        this.tabela = t;
        this.lockTabela = l;
    }
    
    public TabelaEstado getTabelaEstado(){
        return this.tabela;
    }
    
    public static void main(String[] args) throws InterruptedException{
        try {
            String aux;
            char[] tmp;
            char c;
            int id=-1, j=0, i=0, n=0, portaS=-1;
            long ram=-1, timeStampI=-1, timeStampF=-1, rtt=-1;
            double cpu=-1, larguraBanda=0;
            byte[] buf = new byte[1024];
            Date d = new Date();
            
            MonitorUDP m = new MonitorUDP();
            DatagramSocket s = new DatagramSocket(8888);
            
           Thread tr = new Thread(new WriteMonitor(s));
           tr.start();
            
            while(true){
                //InetAddress[] ia = InetAddress.getAllByName("239.8.8.8");
                
                //for(int g=0; g<ia.length; g++){
                    timeStampI = d.getTime();
                    
                    DatagramPacket recv = new DatagramPacket(buf, buf.length);
                    s.receive(recv);

                    timeStampF = d.getTime();

                    aux = buf.toString();
                    tmp = new char[aux.length()];

                    for(i=0; i<aux.length();i++){
                        n=i++;
                        j=0;

                        while((c = aux.charAt(n)) != ';' && n<aux.length()-1 && j<tmp.length){
                                tmp[j] = c;
                                n++;
                                j++;
                        }

                        if(aux.charAt(i) == 'I'){
                            id = Integer.parseInt(Arrays.toString(tmp));
                        }
                        else if(aux.charAt(i) == 'R'){
                            ram = Long.parseLong(Arrays.toString(tmp));
                        }
                        else if(aux.charAt(i) == 'C'){
                            cpu = Double.parseDouble(Arrays.toString(tmp));
                        }
                        
                        tmp = null;
                        i=n;
                    //}

                    rtt = timeStampF - timeStampI;
                    if(rtt!=0) larguraBanda = (buf.length * 8) / rtt;

                    portaS = recv.getPort();
                    InetAddress ipS = recv.getAddress();

                    m.getTabelaEstado().atualizaTabela(id, portaS, ipS, ram, cpu, rtt, larguraBanda);
                }
            }
            
            //tr.join();
            //s.close();
            
        } catch (IOException ex) {
            Logger.getLogger(MonitorUDP.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}