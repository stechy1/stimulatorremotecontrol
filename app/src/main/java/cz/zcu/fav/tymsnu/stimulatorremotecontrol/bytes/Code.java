package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

/**
 * Třída reprezentující kóvou značku
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

    /**
     * Vrátí kódovou značku (typ zprávy)
     * @return typ zprávy
     */
    public byte getCode() {
        return code;
    }

    /**
     * Vrátí popis kódové značky
     * @return popis značky
     */
    public String getDescription() {
        return description;
    }

    /**
     * Vrátí následující značku (následné značky se používají v případě,
     * že má kódová značka následovníka např. jas se nastavuje zvlášť)
     * @return následující kódová značka
     */
    public Code getNext() {
        return next;
    }
}
