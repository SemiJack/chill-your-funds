package chillyourfunds.server;

import java.awt.*;

public enum CYFProtocol {

    LOGIN,
    LOGGEDIN,
    MOUSEPRESSED,
    MOUSEDRAGGED,
    MOUSERELEASED,
    DRAW,
    LOGOUT,
    LOGGEDOUT,
    STOP,
    STOPPED,
    NULLCOMMAND;

    public static final Color[] colors = { Color.black, Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange,
            Color.pink, Color.red, Color.yellow };
}