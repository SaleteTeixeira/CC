/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

/**
 *
 * @author sofia
 */
class ServidorInexistenteException extends Exception {
    public ServidorInexistenteException(String msg) {
        super(msg);
    }
}
