package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

public class ConfigurationREA extends AItem<ConfigurationREA> {

    private int outputCount;
    private int cycleCount;
    private int waitFixed;
    private int waitRandom;
    private int missTime;
    private int brightness;

    // region Constructors
    public ConfigurationREA(String name) {
        this(name, 1, 0, 0, 0, 0, 0);
    }

    public ConfigurationREA(String name, int outputCount, int cycleCount, int waitFixed, int waitRandom, int missTime, int brightness) {
        super(name);
        this.outputCount = outputCount;
        this.cycleCount = cycleCount;
        this.waitFixed = waitFixed;
        this.waitRandom = waitRandom;
        this.missTime = missTime;
        this.brightness = brightness;
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
    /**
     * Vrátí počet výstupů
     * @return Počet výstupů
     */
    public int getOutputCount() {
        return outputCount;
    }

    /**
     * Nastaví počet výstupů
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param outputCount Počet výstupů
     */
    public void setOutputCount(int outputCount) {setOutputCount(outputCount, null);}
    /**
     * Nastaví počet výstupů
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param outputCount Počet výstupů
     * @param onValueChanged Callback, který se zavolá po nastavení počtu výstupů
     */
    public void setOutputCount(int outputCount, OnValueChanged onValueChanged) {
        if (this.outputCount == outputCount)
            return;

        this.outputCount = outputCount;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(int cycleCount) {setCycleCount(cycleCount, null);}
    public void setCycleCount(int cycleCount, OnValueChanged onValueChanged) {
        if (this.cycleCount == cycleCount)
            return;

        this.cycleCount = cycleCount;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getWaitFixed() {
        return waitFixed;
    }

    public void setWaitFixed(int waitFixed) {setWaitFixed(waitFixed, null);}
    public void setWaitFixed(int waitFixed, OnValueChanged onValueChanged) {
        if (this.waitFixed == waitFixed)
            return;

        this.waitFixed = waitFixed;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getWaitRandom() {
        return waitRandom;
    }

    public void setWaitRandom(int waitRandom) {setWaitRandom(waitRandom, null);}
    public void setWaitRandom(int waitRandom, OnValueChanged onValueChanged) {
        if (this.waitRandom == waitRandom)
            return;

        this.waitRandom = waitRandom;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getMissTime() {
        return missTime;
    }

    public void setMissTime(int missTime) {setMissTime(missTime, null);}
    public void setMissTime(int missTime, OnValueChanged onValueChanged) {
        if (this.missTime == missTime)
            return;

        this.missTime = missTime;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    /**
     * Vrátí jas všech výstupů
     * @return Jas výstupů
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * Nastaví jas všem výstupům. Hodnoty jsou možné z intervalu <0 - 100>
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param brightness Jas výstupů
     */
    public void setBrightness(int brightness) {setBrightness(brightness, null);}
    /**
     * Nastaví jas všem výstupům. Hodnoty jsou možné z intervalu <0 - 100>
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param brightness Jas výstupů
     * @param onValueChanged Callback, který se zavolá po nastavení jasu výstupů
     */
    public void setBrightness(int brightness, OnValueChanged onValueChanged) {
        if (this.brightness == brightness)
            return;

        this.brightness = brightness;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    // endregion
}
