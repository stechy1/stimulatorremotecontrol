package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

public class ConfigurationREA extends AItem<ConfigurationREA> {

    // region Variables
    public static final int DEF_OUTPUT_COUNT = 4;

    private int outputCount;
    private int cycleCount;
    private int waitFixed;
    private int waitRandom;
    private int missTime;
    private int brightness;

    private int onFail;
    private boolean m, f;
    private int a, h, w;
    // endregion

    // region Constructors
    public ConfigurationREA(String name) {
        this(name, DEF_OUTPUT_COUNT, 0, 0, 0, 0, 0, 0, false, false, 0, 0, 0);
    }

    public ConfigurationREA(String name, int outputCount, int cycleCount, int waitFixed, int waitRandom, int missTime, int brightness, int onFail,
                            boolean m, boolean f, int a, int h, int w) {
        super(name);
        this.outputCount = outputCount;
        this.cycleCount = cycleCount;
        this.waitFixed = waitFixed;
        this.waitRandom = waitRandom;
        this.missTime = missTime;
        this.brightness = brightness;
        this.onFail = onFail;
        this.m = m;
        this.f = f;
        this.a = a;
        this.h = h;
        this.w = w;
    }

    // endregion

    // region Private methods
    // endregion

    // region Public methods
    @Override
    public ConfigurationREA duplicate(String newName) {

        int outputCount = this.outputCount;
        int cycleCount = this.cycleCount;
        int waitFixed = this.waitFixed;
        int waitRandom = this.waitRandom;
        int missTime = this.missTime;
        int brightness = this.brightness;
        int onFail = this.onFail;
        boolean m = this.m;
        boolean f = this.f;
        int a = this.a;
        int h = this.h;
        int w = this.w;

        return new ConfigurationREA(newName, outputCount, cycleCount, waitFixed, waitRandom, missTime, brightness, onFail, m, f, a, h, w);
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

    public int getOnFail() {
        return onFail;
    }

    public void setOnFail(int onFail) {setOnFail(onFail, null);}
    public void setOnFail(int onFail, OnValueChanged onValueChanged) {
        if (this.onFail == onFail)
            return;

        this.onFail = onFail;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public boolean getM() {
        return m;
    }

    public void setM(boolean m) {setM(m, null);}
    public void setM(boolean m, OnValueChanged onValueChanged) {
        if (this.m == m)
            return;

        this.m = m;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public boolean getF() {
        return f;
    }

    public void setF(boolean f) {setF(f, null);}
    public void setF(boolean f, OnValueChanged onValueChanged) {
        if (this.f == f)
            return;

        this.f = f;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {setA(a, null);}
    public void setA(int a, OnValueChanged onValueChanged) {
        if (this.a == a)
            return;

        this.a = a;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {setH(h, null);}
    public void setH(int h, OnValueChanged onValueChanged) {
        if (this.h == h)
            return;

        this.h = h;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {setW(w, null);}
    public void setW(int w, OnValueChanged onValueChanged) {
        if (this.w == w)
            return;

        this.w = w;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    // endregion
}
