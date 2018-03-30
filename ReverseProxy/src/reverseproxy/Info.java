/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.net.InetAddress;

/**
 *
 * @author isabel, francisco, salete
 */
public class Info {
    private int portaS;
    private InetAddress ipS;
    private long ram, rtt;
    private double cpu, larguraBanda;
    
    public Info(int portaS, InetAddress ipS, long ram, double cpu, long rtt, double larguraBanda){
        this.portaS=portaS;
        this.ipS=ipS;
        this.ram=ram;
        this.cpu=cpu;
        this.rtt=rtt;
        this.larguraBanda=larguraBanda;
    }

    public int getPortaS(){
        return this.portaS;
    }
    
    public InetAddress getIpS(){
        return this.ipS;
    }
    
    public long getRam(){
        return this.ram;
    }
    
    public double getCpu(){
        return this.cpu;
    }
    
    public long getRtt(){
        return this.rtt;
    }
    
    public double getLarguraBanda(){
        return this.larguraBanda;
    }
}
