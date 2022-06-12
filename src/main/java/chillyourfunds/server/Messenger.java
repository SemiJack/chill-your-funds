package chillyourfunds.server;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa, której obiekty są wysyłane do klienta.
 * Wysyłane komunikaty muszą zawietać pole CYFProtocol i mogą zawierać inne dane (Obiekty).
 * @author Jacek Pelczar
 */
public class Messenger implements Serializable {
    public CYFProtocol command;

    public Object data;
    public String option;

    public Messenger( CYFProtocol command, Object data){
        this.data = data;
        this.command = command;
    }
    public Messenger(CYFProtocol command, String option, Object data){
        this.data = data;
        this.command = command;
        this.option = option;
    }
    public Messenger( CYFProtocol command){
        this.command = command;
    }

}
