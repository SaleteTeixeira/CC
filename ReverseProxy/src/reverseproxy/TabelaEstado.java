package reverseproxy;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe que armazena toda a informação dos vários Agentes UDPs.
 * Esta classe contem também o método que remove os Agentes da Tabela passado um certo tempo e o método de escolha de um Agente para comunicar com um Cliente.
 * 
 * @author isabel, francisco, salete
 */
public class TabelaEstado {
    private Map<InetAddress,Info> estado;
    private int posicao;
    
    public TabelaEstado(){
        this.estado = new HashMap<>();
        this.posicao = 0;
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
    
    public Info algoritmoSelecao(){
        List<Info> auxiliar;
        Info escolhido = new Info();
        int i=0;
        
        auxiliar = this.estado.values().stream().collect(Collectors.toList());
        
        while(escolhido.getLarguraBanda() <= 0 && i < auxiliar.size()){
            if(posicao > auxiliar.size()) posicao = 0;
            escolhido = auxiliar.get(posicao);
            posicao++;
            i++;
        }
        
        if(escolhido.getLarguraBanda() <= 0) return null;
        
        return escolhido;
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
