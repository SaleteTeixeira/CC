package reverseproxy;

/**
 * Pacote PDU que define a informação enviada de um Agente ao Monitor.
 * 
 * @author isabel, francisco, salete
 */
public class PDUam {
    private long ram;
    private double cpu, larguraBanda;
    
    public PDUam(long r, double c, double lb){
        this.ram=r;
        this.cpu=c;
        this.larguraBanda=lb;
    }
    
    public long getRam(){
        return this.ram;
    }
    
    public double getCpu(){
        return this.cpu;
    }
    
    public double getLarguraBanda(){
        return this.larguraBanda;
    }
    
    public byte[] getBytes(){
       String s = ram+";"+cpu+";"+larguraBanda;
       return s.getBytes();
    }
}
