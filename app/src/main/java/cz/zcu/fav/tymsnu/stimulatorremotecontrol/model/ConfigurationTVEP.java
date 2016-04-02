package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

public class ConfigurationTVEP extends AItem<ConfigurationTVEP> {

    private int outputCount;

    public ConfigurationTVEP(String name) {
        super(name);
    }

    @Override
    public ConfigurationTVEP duplicate(String newName) {
        return null;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(int outputCount) {
        this.outputCount = outputCount;
    }
}
