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
    private Map<Integer,Info> estado;
    
    public TabelaEstado(){
        this.estado = new HashMap<>();
    }  
    
    public void atualizaTabela(int servidor, int portaS, InetAddress ipS, long ram, double cpu, long rtt, double larguraBanda){
        Info i = new Info(portaS, ipS, ram, cpu, rtt, larguraBanda);
        this.estado.put(servidor, i);
    }
    
    public void removerServidorTab(int servidor) throws ServidorInexistenteException {
        if(this.estado.remove(servidor)==null){
            throw new ServidorInexistenteException("Servidor inexistente na tabela de estado.");
        }
    }
    
    public int algoritmoSelecao(){
        //pensar
        return 1; //portaS
    }
}
