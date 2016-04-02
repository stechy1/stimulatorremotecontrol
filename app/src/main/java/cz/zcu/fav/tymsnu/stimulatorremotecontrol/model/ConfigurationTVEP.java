package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationTVEP extends AItem<ConfigurationTVEP> {

    // region Variables
    private int outputCount;
    private int patternLength;
    private int pulsLength;
    private int pulsSkew;
    private int brightness;
    public final List<Pattern> patternList;
    // endregion

    // region Constructors
    public ConfigurationTVEP(String name) {
        this(name, 1, 1, 0, 0, 0, new ArrayList<Pattern>());
    }

    public ConfigurationTVEP(String name, int outputCount, int patternLength, int pulsLength, int pulsSkew, int brightness, List<Pattern> patternList) {
        super(name);
        this.outputCount = outputCount;
        this.patternLength = patternLength;
        this.pulsLength = pulsLength;
        this.pulsSkew = pulsSkew;
        this.brightness = brightness;
        this.patternList = patternList;
    }
    // endregion

    // region Public methods
    @Override
    public ConfigurationTVEP duplicate(String newName) {
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

    /**
     * Vrátí délku Patternu
     * @return Délka patternu
     */
    public int getPatternLength() {
        return patternLength;
    }

    /**
     * Nastaví délku Patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param patternLength Dálka Patternu
     */
    public void setPatternLength(int patternLength) {
        setPatternLength(patternLength, null);}
    /**
     * Nastaví délku Patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param patternLength Délka Patternu
     * @param onValueChanged Callback, který se zavolá po nastavení délky Patternu
     */
    public void setPatternLength(int patternLength, OnValueChanged onValueChanged) {
        if (this.patternLength == patternLength)
            return;

        this.patternLength = patternLength;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    /**
     * Vrátí délku pulsu
     * @return Délka pulsu
     */
    public int getPulsLength() {
        return pulsLength;
    }

    /**
     * Nastaví délku pulsu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param pulsLength Dálka pulsu
     */
    public void setPulsLength(int pulsLength) {setPulsLength(pulsLength, null);}
    /**
     * Nastaví délku pulsu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param pulsLength Délka pulsu
     * @param onValueChanged Callback, který se zavolá po nastavení délky pulsu
     */
    public void setPulsLength(int pulsLength, OnValueChanged onValueChanged) {
        if (this.pulsLength == pulsLength)
            return;

        this.pulsLength = pulsLength;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    /**
     * Vrátí číslo představující bitový posun, o který liší jednotlivé patterny
     * @return Bitový posun
     */
    public int getPulsSkew() {
        return pulsSkew;
    }

    /**
     * Nastaví bitový posun patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param pulsSkew Bitový posun
     */
    public void setPulsSkew(int pulsSkew) {setPulsSkew(pulsSkew, null);}
    /**
     * Nastaví bitový posun patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param pulsSkew Bitový posun
     * @param onValueChanged Callback, který se zavolá po nastavení bitového posunu
     */
    public void setPulsSkew(int pulsSkew, OnValueChanged onValueChanged) {
        if (this.pulsSkew == pulsSkew)
            return;

        this.pulsSkew = pulsSkew;

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

    public static final class Pattern {

        // region Variables
        private int size;
        private int value;
        // endregion

        // region Constructors
        public Pattern() {
            this(1, 0);
        }

        public Pattern(int size, int value) {
            this.size = size;
            this.value = value;
        }
        // endregion

        // region Public methods
        // endregion

        // region Getters & Setters
        /**
         * Vrátí velikost patternu
         * @return Velikost patternu
         */
        public int getSize() {
            return size;
        }

        /**
         * Nastaví velikost patternu. Velikost je v intervalu <1 - 16>
         * okud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
         * @param size Nová velikost patternu
         */
        public void setSize(int size) {setSize(size, null);}
        /**
         * Nastaví velikost patternu. Velikost je v intervalu <1 - 16>
         * okud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
         * @param size Nová velikost patternu
         * @param onValueChanged Callback, který se zavolá po nastavení nové velikosti patternu
         */
        public void setSize(int size, OnValueChanged onValueChanged) {
            if (this.size == size)
                return;

            this.size = size;

            if (onValueChanged != null)
                onValueChanged.changed();
        }

        /**
         * Vrátí aktuální hodnotu patternu
         * @return Hodnota patternu
         */
        public int getValue() {
            return value;
        }

        /**
         * Nastaví novou hodnotu patternu
         * @param value Nová hodnota patternu
         */
        public void setValue(int value) {setValue(value, null);}
        /**
         * Nastaví novou hodnotu patternu
         * @param value Nová hodnota patternu
         * @param onValueChanged Callback, který se zavolá po nastavení nové hodnoty patternu
         */
        public void setValue(int value, OnValueChanged onValueChanged) {
            if (this.value == value)
                return;

            this.value = value;

            if (onValueChanged != null)
                onValueChanged.changed();
        }
        // endregion
    }
}
