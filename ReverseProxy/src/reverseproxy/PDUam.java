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
       String s = idAgente+";"+ram+";"+cpu;
       return s.getBytes();
    }
}
