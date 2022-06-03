package chillyourfunds.server;

import java.io.Serializable;

public class Messenger implements Serializable {
    public CYFProtocol command;

    public Object data;

    public Messenger( CYFProtocol command, Object data){
        this.data = data;
        this.command = command;
    }
    public Messenger( CYFProtocol command){
        this.command = command;
    }

}