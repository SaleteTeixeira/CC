package reverseproxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
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
public class LerAgente implements Runnable{
    private MulticastSocket s;
    private ReentrantLock l;
    private Condition c;
    
    public LerAgente(MulticastSocket sc, ReentrantLock lc, Condition cd){
        this.s = sc;
        this.l = lc;
        this.c = cd;
    }
    
    public void run(){
        //HMAC
        
        while(true){
            try {
                byte[] aReceber = new byte[1024];
                DatagramPacket recv = new DatagramPacket(aReceber,aReceber.length);
                s.receive(recv);
                
                if(aReceber.toString().equals("Send me information.")){
                     this.c.signalAll();
                }
            } catch (IOException ex) {
                Logger.getLogger(LerAgente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
