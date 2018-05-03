/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.net.InetAddress;

/**
 * Classe que tem como objetivo armazenar a informação de um Agente.
 * 
 * @author isabel, francisco, salete
 */
public class Info {
    private int portaS;
    private InetAddress ipS;
    private long ram, rtt, time;
    private double cpu, larguraBanda;
    
    public Info(){
        this.ram=-1;
        this.cpu=-1;
        this.rtt=-1;
        this.larguraBanda=-1;
    }
    
    public Info(int portaS, InetAddress ipS, long ram, double cpu, long rtt, double larguraBanda, long time){
        this.portaS=portaS;
        this.ipS=ipS;
        this.ram=ram;
        this.cpu=cpu;
        this.rtt=rtt;
        this.larguraBanda=larguraBanda;
        this.time=time;
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
    
    public long getTime(){
        return this.time;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
         
        sb.append("Porta: "+this.portaS+"\n");
        sb.append("IP: "+this.ipS+"\n");
        sb.append("RAM: "+this.ram+"\n");
        sb.append("CPU: "+this.cpu+"\n");
        sb.append("RTT: "+this.rtt+"\n");
        sb.append("Largura de Banda: "+this.larguraBanda+"\n");
        sb.append("TIME: "+this.time+"\n");
        
        return sb.toString();
    }
}
