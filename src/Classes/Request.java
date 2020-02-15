package Classes;

import java.io.Serializable;

public class Request implements Serializable {
    public enum TYPE implements Serializable {
        USER_NEW,
        USER,
        USER_APPROVED,
        USER_DISAPPROVED,
        USER_CONNECTED,
        USER_LOG_OUT,
        NEW_USER_APPROVED,
        NEW_USER_DISAPPROVED,
        USER_ONLINE,
        MESSAGE
    }

    public TYPE type;
    private Object toBeSent;

    public Request(TYPE type, Object toBeSent) {
        this.type = type;
        this.toBeSent = toBeSent;
    }

    public Object getToBeSent() {
        return toBeSent;
    }
}
