package chillyourfunds.server;

import java.awt.*;

public enum CYFProtocol {

    LOGIN,
    LOGGEDIN,
    CHOOSEGROUP,
    ADDEXPENSE,
    HISTORY,

    CYF,
    LOGOUT,
    LOGGEDOUT,
    STOP,
    STOPPED,
    NULLCOMMAND;

    public static final Color[] colors = { Color.black, Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange,
            Color.pink, Color.red, Color.yellow };
}