package reverseproxy;

import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread que recebe mensagens de um Cliente, atribuindo-lhe um Agente da Tabela e mantem a comunicação do cliente para esse Agente
 * 
 * @author isabel, francisco, salete
 */
public class Worker implements Runnable{
    private TabelaEstado tabela;
    private ReentrantLock lockTabela;
    private Socket socket;

    public Worker(TabelaEstado t, ReentrantLock l, Socket s){
        this.tabela = t;
        this.lockTabela = l;
        this.socket = s;
    }
    
    public void run(){
        Info agenteEscolhido;
        InetAddress IPagente;
        int portaAgente;
        
        this.lockTabela.lock();
            
        agenteEscolhido = this.tabela.algoritmoSelecao();
            
        if(agenteEscolhido != null){
            IPagente = agenteEscolhido.getIpS();
            portaAgente = agenteEscolhido.getPortaS();
        }
            
        this.lockTabela.unlock();
        
        //abre uma conexão TCP para o servidor escolhido
        
        while(true){
            //recebe pedidos cliente
        }
    }
}