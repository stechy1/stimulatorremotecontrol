package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import java.util.ArrayList;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;

public class ConfigurationREA extends AConfiguration<ConfigurationREA> {

    // region Variables
    public static final int DEF_CYCLE_COUNT = 0;
    public static final int DEF_WAIT_FIXED = 0;
    public static final int DEF_WAIT_RANDOM = 0;
    public static final int DEF_MISS_TIME = 0;
    public static final int DEF_BRIGHTNESS = 0;
    public static final int DEF_ON_FAIL = 0;
    public static final Sex DEF_SEX = Sex.MALE;
    public static final int DEF_A = 0;
    public static final int DEF_H = 0;
    public static final int DEF_W = 0;

    private int cycleCount;
    private int waitFixed;
    private int waitRandom;
    private int missTime;
    private int brightness;
    private int onFail;
    private Sex sex;
    private int a, h, w;
    // endregion

    // region Constructors

    /**
     * Konstruktor třídy s výchozími parametry
     * @param name Název konfigurace
     */
    public ConfigurationREA(String name) {
        this(name, DEF_OUTPUT_COUNT, 0, 0, 0, 0, 0, 0, Sex.MALE, 0, 0, 0);
    }

    /**
     * Konstruktor třídy s parametry
     * @param name Název konfigurace
     * @param outputCount Počet výstupů
     * @param cycleCount Počet cyklů
     * @param waitFixed
     * @param waitRandom
     * @param missTime
     * @param brightness Jas výstupů
     * @param onFail Co se má stán pří failu
     * @param sex Pohlaví subjektu
     * @param a Věk subjektu
     * @param h Výška subjektu
     * @param w Váha subjektu
     * @throws IllegalArgumentException
     */
    public ConfigurationREA(String name, int outputCount, int cycleCount, int waitFixed, int waitRandom, int missTime, int brightness, int onFail,
                            Sex sex, int a, int h, int w) throws IllegalArgumentException {
        super(name, outputCount);

        if (sex == null)
            throw new IllegalArgumentException();

        setCycleCount(cycleCount);
        setWaitFixed(waitFixed);
        setWaitRandom(waitRandom);
        setMissTime(missTime);
        setBrightness(brightness);
        setOnFail(onFail);
        setSex(sex);
        setA(a);
        setH(h);
        setW(w);
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
        Sex sex = this.sex;
        int a = this.a;
        int h = this.h;
        int w = this.w;

        return new ConfigurationREA(newName, outputCount, cycleCount, waitFixed, waitRandom, missTime, brightness, onFail, sex, a, h, w);
    }

    @Override
    public ArrayList<Packet> getPackets() {
        return new ArrayList<>();
    }

    // endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConfigurationREA that = (ConfigurationREA) o;

        if (outputCount != that.outputCount) return false;
        if (cycleCount != that.cycleCount) return false;
        if (waitFixed != that.waitFixed) return false;
        if (waitRandom != that.waitRandom) return false;
        if (missTime != that.missTime) return false;
        if (brightness != that.brightness) return false;
        if (onFail != that.onFail) return false;
        if (a != that.a) return false;
        if (h != that.h) return false;
        if (w != that.w) return false;
        return sex == that.sex;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + outputCount;
        result = 31 * result + cycleCount;
        result = 31 * result + waitFixed;
        result = 31 * result + waitRandom;
        result = 31 * result + missTime;
        result = 31 * result + brightness;
        result = 31 * result + onFail;
        result = 31 * result + sex.hashCode();
        result = 31 * result + a;
        result = 31 * result + h;
        result = 31 * result + w;
        return result;
    }

    // endregion

    // region Getters & Setters
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) throws IllegalArgumentException {setSex(sex, null);}
    public void setSex(Sex sex, OnValueChanged onValueChanged) throws IllegalArgumentException {
        if (sex == null)
            throw new IllegalArgumentException();

        if (this.sex == sex)
            return;

        this.sex = sex;

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

    public enum Sex {
        MALE, FEMALE;

        public static Sex valueOf(int index) {
            switch (index) {
                case 0:
                    return MALE;
                case 1:
                    return FEMALE;
                default:
                    return MALE;
            }
        }
    }

    public static final class Builder{
        private String name;
        private int outputCount = DEF_OUTPUT_COUNT;
        private int cycleCount = DEF_CYCLE_COUNT;
        private int waitFixed = DEF_WAIT_FIXED;
        private int waitRandom = DEF_WAIT_RANDOM;
        private int missTime = DEF_MISS_TIME;
        private int brightness = DEF_BRIGHTNESS;
        private int onFail = DEF_ON_FAIL;
        private Sex sex = DEF_SEX;
        private int a = DEF_A;
        private int h = DEF_H;
        private int w = DEF_W;

        public Builder(String name){
            this.name = name;
        }

        public Builder outputCount(int outputCount){
            this.outputCount = outputCount;
            return this;
        }

        public Builder cycleCount(int cycleCount){
            this.cycleCount = cycleCount;
            return this;
        }

        public Builder waitFixed(int waitFixed){
            this.waitFixed = waitFixed;
            return this;
        }

        public Builder waitRandom(int waitRandom){
            this.waitRandom = waitRandom;
            return this;
        }

        public Builder missTime(int missTime){
            this.missTime = missTime;
            return this;
        }

        public Builder brightness(int brightness){
            this.brightness = brightness;
            return this;
        }

        public Builder onFail(int onFail){
            this.onFail = onFail;
            return this;
        }

        public Builder sex(Sex sex){
            if (sex == null)
                return this;

            this.sex = sex;
            return this;
        }

        public Builder a(int a){
            this.a = a;
            return this;
        }

        public Builder h(int h){
            this.h = h;
            return this;
        }

        public Builder w(int w){
            this.w = w;
            return this;
        }

        public ConfigurationREA build(){
            return new ConfigurationREA(this.name, this.outputCount, this.cycleCount, this.waitFixed, this.waitRandom, this.missTime,
                    this.brightness, this.onFail, this.sex, this.a, this.h, this.w);
        }
    }
}
