package chillyourfunds.server;

import java.io.Serializable;
import java.util.ArrayList;

public class Messenger implements Serializable {
    public CYFProtocol command;

    public Object data;
    public String option;

    public Messenger( CYFProtocol command, Object data){
        this.data = data;
        this.command = command;
    }
//    public Messenger( CYFProtocol command, Object data1, Object data2){
//        this.data = new Object[]{data1,data2};
//        this.command = command;
//    }

    public Messenger(CYFProtocol command, String option, Object data){
        this.data = data;
        this.command = command;
        this.option = option;
    }
    public Messenger( CYFProtocol command){
        this.command = command;
    }

}
