/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

/**
 *
 * @author isabel, francisco, salete
 */
public class PDUam {
    private int idAgente;
    private long ram;
    private double cpu;
    
    public PDUam(int id, long r, double c){
        this.idAgente=id;
        this.ram=r;
        this.cpu=c;
    }
    
    public int getIdAgente(){
        return this.idAgente;
    }
    
    public long getRam(){
        return this.ram;
    }
    
    public double getCpu(){
        return this.cpu;
    }
    
    public byte[] getBytes(){
       String s = "I"+idAgente+";R"+ram+";C"+cpu;
       return s.getBytes();
    }
    
    public int getLength(){
        return Integer.toString(idAgente).length() + Long.toString(ram).length() + Double.toString(cpu).length();
    }
}
