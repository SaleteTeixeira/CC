package reverseproxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            Info agenteEscolhido;
            InetAddress IPagente;
            Socket sc;
            BufferedReader in;
            BufferedWriter out;
            String str = null;
            
            this.lockTabela.lock();
            
            agenteEscolhido = this.tabela.algoritmoSelecao();
            
            if(agenteEscolhido == null){
                this.lockTabela.unlock();
                Thread.currentThread().interrupt();
            }
            
            IPagente = agenteEscolhido.getIpS();
            
            this.lockTabela.unlock();
            
            sc = new Socket(IPagente,80);
            
            Thread ler = new Thread(new LerHttp(this.socket,sc));
            ler.start();
            
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(sc.getOutputStream()));
            
            while((str = in.readLine())!= null){
                out.write(str);
                out.flush();
                System.out.println("ENVIEI PARA O AGENTE: "+str);
            }
            
            sc.shutdownOutput();
            this.socket.shutdownInput();
            sc.close();
            this.socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}