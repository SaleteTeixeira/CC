package reverseproxy;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author isabel, francisco, salete
 */
public class PDUam {
    private long ram;
    private double cpu;
    
    public PDUam(long r, double c){
        this.ram=r;
        this.cpu=c;
    }
    
    public long getRam(){
        return this.ram;
    }
    
    public double getCpu(){
        return this.cpu;
    }
    
    public byte[] getBytes(){
       String s = ram+";"+cpu;
       return s.getBytes();
    }
}
