package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

public class ConfigurationREA extends AItem<ConfigurationREA> {

    private int outputCount;

    // region Constructors
    public ConfigurationREA(String name) {
        super(name);
    }
    // endregion

    // region Private methods
    // endregion

    // region Public methods
    @Override
    public ConfigurationREA duplicate(String newName) {
        return null;
    }


    // endregion

    // region Getters & Setters
    public int getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(int outputCount) {
        this.outputCount = outputCount;
    }

    // endregion
}
