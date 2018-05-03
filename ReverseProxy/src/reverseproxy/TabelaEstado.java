/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author isabel, francisco, salete
 */
public class TabelaEstado {
    private Map<InetAddress,Info> estado;
    
    public TabelaEstado(){
        this.estado = new HashMap<>();
    }  
    
    public void atualizaTabela(int portaS, InetAddress ipS, long ram, double cpu, long rtt, double larguraBanda, long time){
        Info i = new Info(portaS, ipS, ram, cpu, rtt, larguraBanda, time);
        this.estado.put(ipS, i);
    }
    
    public void removerServidorTab() {
        long rem, timeOut = 5000;
        
        for(Map.Entry<InetAddress,Info> s: estado.entrySet()){
            rem = System.currentTimeMillis() - s.getValue().getTime();
            if(rem>timeOut){
                this.estado.remove(s);
                System.out.println("Removido Agente "+s.getKey());
            }
        }
    }
    
    public int algoritmoSelecao(){
        //pensar
        return 1; //portaS
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n###############TABELA###############\n");
        for(Map.Entry<InetAddress,Info> s: estado.entrySet()){  
            sb.append("Agente: "+s.getKey()+"\n");
            sb.append(s.getValue().toString());
            sb.append("------------------------------------\n");
        }
        sb.append("####################################\n");
        
        return sb.toString();
    }
}
