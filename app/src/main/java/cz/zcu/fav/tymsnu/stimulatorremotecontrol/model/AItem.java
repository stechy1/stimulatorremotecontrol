package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


public class AItem {

    protected String name;

    public boolean loaded;
    public boolean selected;

    public AItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
