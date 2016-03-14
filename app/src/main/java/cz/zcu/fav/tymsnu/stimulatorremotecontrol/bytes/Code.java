package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

/**
 * Created by Vrbik on 14.03.2016.
 */
public class Code {
    private byte code;
    private String description;
    private Code next;

    public Code(byte code, String description) {
        this.code = code;
        this.description = description;
    }

    public Code(byte code, String description, Code next) {
        this(code, description);
        this.next = next;
    }

    public byte getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Code getNext() {
        return next;
    }
}
