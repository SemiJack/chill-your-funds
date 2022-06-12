package chillyourfunds.server;

/**
 * Protokół używany w celu komunikacji serwer-klient\
 * @author Jacek Pelczar
 */
public enum CYFProtocol {

    LOGIN,
    LOGGEDIN,
    CREATEGROUP,
    GROUPCREATED,
    CHOOSEGROUP,
    GROUPCHOOSED,
    EXPENSEADDED,
    ADDEXPENSE,
    ADDPERSON,
    REMOVEPERSON,
    SIMPlify,
    PERSONADDED,

    UPDATE,
    HISTORY,
    REGISTER,
    REGISTERED,
    COMMENT,
    LOGOUT,
    PERSONREMOVED,
    SNAPSHOT,
    PERSON,
    LOGINDENY,
    LOGGEDOUT,
    STOP,
    STOPPED,
    NULLCOMMAND,
    PERCENTSPLIT,
    EQUALSPLIT,
    EXACTSPLIT;


}