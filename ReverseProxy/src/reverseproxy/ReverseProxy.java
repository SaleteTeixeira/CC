package reverseproxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe principal que proporciona uma comunicação entre Clientes e Agentes.
 * 
 * @author isabel, francisco, salete
 */
public class ReverseProxy {
    private TabelaEstado tabela;
    private ReentrantLock lockTabela;
   
    public ReverseProxy(){
        this.tabela = new TabelaEstado();
        this.lockTabela = new ReentrantLock();
    }
    
    public static void main(String[] args) {
        try {
            ReverseProxy rp = new ReverseProxy();
            ServerSocket Server;
            
            Server = new ServerSocket(80);
            
            Thread monitor = new Thread(new MonitorUDP(rp.tabela, rp.lockTabela));
            monitor.start();
                        
            while(true){
                Socket clientSocket = null;
                clientSocket = Server.accept();
                               
                Thread worker = new Thread(new Worker(rp.tabela, rp.lockTabela, clientSocket));
                worker.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ReverseProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}