package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtils;

public class ConfigurationTVEP extends AConfiguration<ConfigurationTVEP> {

    // region Variables
    public static final int DEF_PATTERN_LENGTH = 1;
    public static final int MIN_PATTERN_LENGTH = 1;
    public static final int MAX_PATTERN_LENGTH = 16;
    public static final int DEF_PULS_LENGTH = 0;
    public static final int DEF_PULS_SKEW = 0;
    public static final int DEF_BRIGHTNESS = 0;

    private int patternLength;
    private int pulsLength;
    private int pulsSkew;
    private int brightness;
    public final List<Pattern> patternList;
    // endregion

    // region Constructors
    /**
     * Konstruktor třídy s výchozími parametry
     * @param name Název konfigurace
     */
    public ConfigurationTVEP(String name) {
        this(name, DEF_OUTPUT_COUNT, DEF_PATTERN_LENGTH, DEF_PULS_LENGTH, DEF_PULS_SKEW, DEF_BRIGHTNESS, new ArrayList<Pattern>());
    }

    /**
     * Konstruktor třídy s parametry
     * @param name Název konfigurace
     * @param outputCount Počet výstupů
     * @param patternLength Délka paternu
     * @param pulsLength Délka pulzu
     * @param pulsSkew Délka mezery mezi dvěma pulzy
     * @param brightness
     * @param patternList Kolekce výstupů
     * @throws IllegalArgumentException Pokud je parametr outputList null
     */
    public ConfigurationTVEP(String name, int outputCount, int patternLength, int pulsLength, int pulsSkew, int brightness, List<Pattern> patternList) throws IllegalArgumentException {
        super(name, outputCount);

        if (patternList == null)
            throw new IllegalArgumentException();

        this.patternList = patternList;

        setPatternLength(patternLength);
        setPulsLength(pulsLength);
        setPulsSkew(pulsSkew);
        setBrightness(brightness);

        if (this.outputCount != this.patternList.size())
            rearangeOutputs();
    }
    // endregion

    // region Private methods
    /**
     * Upraví počet výstupů
     * Pokud je jich víc, než je požadováno, tak odstraní poslední
     * Pokud je jich méně, tak vytvoří nové
     */
    private void rearangeOutputs() {
        int listCount = patternList.size();
        if (outputCount > listCount) {
            int delta = outputCount - listCount;
            for (int i = 0; i < delta; i++) {
                patternList.add(new Pattern());
            }
        } else {
            for (int i = --listCount; i >= outputCount; i--) {
                patternList.remove(i);
            }
        }
    }
    // endregion

    // region Public methods

    /**
     * Zjistí, zda-li je hodnota v povoleném intervalu
     * @param value Testovaná hodnota
     * @return True, pokud je hodnota v povoleném intervalu, jinak false
     */
    public boolean isPatternLengthInRange(int value) {
        return RangeUtils.isInRange(value, MIN_PATTERN_LENGTH, MAX_PATTERN_LENGTH);
    }

    /**
     * Zjistí, zda-li hodnota odpovídá rozsahu jasu
     * @param val Kontrolovaná hodnota
     * @return True, pokud hodnota odpovídá rozsahu jasu, jinak false
     */
    public boolean isBrightnessInRange(int val) {
        return RangeUtils.isInPercentRange(val);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConfigurationTVEP that = (ConfigurationTVEP) o;

        if (patternLength != that.patternLength) return false;
        if (pulsLength != that.pulsLength) return false;
        if (pulsSkew != that.pulsSkew) return false;
        if (brightness != that.brightness) return false;
        return patternList.equals(that.patternList);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + patternLength;
        result = 31 * result + pulsLength;
        result = 31 * result + pulsSkew;
        result = 31 * result + brightness;
        result = 31 * result + patternList.hashCode();
        return result;
    }

    @Override
    public ConfigurationTVEP duplicate(String newName) {
        int outputCount = this.outputCount;
        int patternLength = this.patternLength;
        int pulsLength = this.pulsLength;
        int pulsSkew = this.pulsSkew;
        int brightness = this.brightness;

        List<Pattern> patternList = new ArrayList<>();

        for(Pattern a : this.patternList){
            patternList.add(Pattern.clone(a));
        }
        return new ConfigurationTVEP(newName, outputCount, patternLength, pulsLength, pulsSkew, brightness, patternList);
    }

    @Override
    public ArrayList<Packet> getPackets() {
        return new ArrayList<>();
    }

    // endregion

    // region Getters & Setters
    /**
     * Nastaví počet výstupů
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param outputCount Počet výstupů
     * @param onValueChanged Callback, který se zavolá po nastavení počtu výstupů
     * @throws IllegalArgumentException Pokud počet výstupů není v povoleném rozsahu
     */
    public void setOutputCount(int outputCount, OnValueChanged onValueChanged) throws IllegalArgumentException {
        super.setOutputCount(outputCount, null);

        rearangeOutputs();

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
     * @throws IllegalArgumentException Pokud délka paternu není v povoleném intervalu
     */
    public void setPatternLength(int patternLength) throws IllegalArgumentException {
        setPatternLength(patternLength, null);}
    /**
     * Nastaví délku Patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param patternLength Délka Patternu
     * @param onValueChanged Callback, který se zavolá po nastavení délky Patternu
     * @throws IllegalArgumentException Pokud délka paternu není v povoleném intervalu
     */
    public void setPatternLength(int patternLength, OnValueChanged onValueChanged) throws IllegalArgumentException {
        if (!isPatternLengthInRange(patternLength))
            throw new IllegalArgumentException();

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
     * @throws IllegalArgumentException Pokud jas není v povoleném intervalu
     */
    public void setBrightness(int brightness) throws IllegalArgumentException {setBrightness(brightness, null);}
    /**
     * Nastaví jas všem výstupům. Hodnoty jsou možné z intervalu <0 - 100>
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param brightness Jas výstupů
     * @param onValueChanged Callback, který se zavolá po nastavení jasu výstupů
     * @throws IllegalArgumentException Pokud jas není v povoleném intervalu
     */
    public void setBrightness(int brightness, OnValueChanged onValueChanged) throws IllegalArgumentException {
        if (!isBrightnessInRange(brightness))
            throw new IllegalArgumentException();

        if (this.brightness == brightness)
            return;

        this.brightness = brightness;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    // endregion

    public static final class Pattern {

        // region Variables
        public static final int DEF_VALUE = 0;
        // Hodnota patternu
        private int value;
        // endregion

        // region Private static methods
        public static Pattern clone(Pattern source) throws IllegalArgumentException {
            if (source == null)
                throw new IllegalArgumentException();

            return new Pattern(source.value);
        }
        // endregion

        // region Constructors
        /**
         * Konstruktor třídy Pattern s výchozí hodnotou
         */
        public Pattern() {
            this(DEF_VALUE);
        }

        /**
         * Konstruktor třídy pattern
         * @param value Hodnota patternu
         */
        public Pattern(int value) {
            setValue(value);
        }
        // endregion

        // region Public methods

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pattern pattern = (Pattern) o;

            return value == pattern.value;

        }

        @Override
        public int hashCode() {
            return value;
        }

        // endregion

        // region Getters & Setters
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

    public static final class Builder{
        private String name;
        private int outputCount = DEF_OUTPUT_COUNT;
        private int patternLength = DEF_PATTERN_LENGTH;
        private int pulsLength = DEF_PULS_LENGTH;
        private int pulsSkew = DEF_PULS_SKEW;
        private int brightness = DEF_BRIGHTNESS;
        private List<Pattern> patternList = new ArrayList<>();

        public Builder(String name){
            this.name = name;
        }

        public Builder outputCount(int outputCount){
            this.outputCount = outputCount;
            return this;
        }

        public Builder patternLength(int patternLength){
            this.patternLength = patternLength;
            return this;
        }

        public Builder pulsLength(int pulsLength){
            this.pulsLength = pulsLength;
            return this;
        }

        public Builder pulsSkew(int pulsSkew){
            this.pulsSkew = pulsSkew;
            return this;
        }

        public Builder brightness(int brightness){
            this.brightness = brightness;
            return this;
        }

        public Builder patternList(List<Pattern> patternList){
            if(patternList == null)
                return this;

            this.patternList = patternList;
            return this;
        }

        public ConfigurationTVEP build(){
            return new ConfigurationTVEP(this.name, this.outputCount, this.patternLength,
                    this.pulsLength, this.pulsSkew, this.brightness, this.patternList);
        }
    }
}
