package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

public class ConfigurationCVEP extends AItem<ConfigurationCVEP> {



    public ConfigurationCVEP(String name) {
        super(name);
    }

    @Override
    public ConfigurationCVEP duplicate(String newName) {
        return null;
    }

    public int getOutputCount() {
        return 0;
    }
}
