package reverseproxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread que lê as mensagens enviadas por um Agente e envia para o Cliente a si associado.
 * 
 * @author isabel, francisco, salete
 */
public class LerHttp implements Runnable{
    private Socket scCliente;
    private Socket scHttp;
    
    public LerHttp(Socket s1, Socket s2){
        this.scCliente = s1;
        this.scHttp = s2;
    }
    
    public void run(){
        try {
            String str = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(this.scHttp.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.scCliente.getOutputStream()));
            
            while((str = in.readLine())!= null){
                out.write(str);
                out.flush();
                System.out.println("ENVIEI PARA O CLIENTE: "+str);
            }
            
            this.scCliente.shutdownOutput();
            this.scHttp.shutdownInput();
            
        } catch (IOException ex) {
            Logger.getLogger(LerHttp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
