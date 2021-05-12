/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author darra
 */
public class PseudoAlreadyExistException extends Exception {
    
    public PseudoAlreadyExistException (String message) {
        super(message);
    }
}
