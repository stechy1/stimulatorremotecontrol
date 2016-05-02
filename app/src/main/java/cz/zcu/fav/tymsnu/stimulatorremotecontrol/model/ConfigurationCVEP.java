package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import java.util.ArrayList;
import java.util.List;


import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtils;

public class ConfigurationCVEP extends AConfiguration<ConfigurationCVEP> {

    // region Variables
    public static final int PATTERN_LENGTH = 32;
    // Výchozí hodnota parametru brightness
    public static final int DEF_BRIGHTNESS = 0;
    // Výchozí hodnota parametru bit shift
    public static final int DEF_BIT_SHIFT = 1;
    // Výchozí hodnota parametru puls length
    public static final int DEF_PULS_LENGTH = 0;

    private int pulsLength;
    private int bitShift;
    private int brightness;
    private final Pattern mainPattern = new Pattern();
    public final List<Pattern> patternList = new ArrayList<>();
    // endregion

    // region Constructors
    /**
     * Konstruktor třídy s výchozími parametry
     * @param name Název konfigurace
     */
    public ConfigurationCVEP(String name) {
        this(name, Pattern.DEF_VALUE, DEF_OUTPUT_COUNT, DEF_BRIGHTNESS, DEF_BIT_SHIFT, DEF_PULS_LENGTH);
    }

    /**
     * Konstruktor třídy s parametry
     * @param name Název konfigurace
     * @param patternValue Hodnota hlavního patternu
     * @param outputCount Počet výstupů
     * @param brightness Jas výstupů
     * @param bitShift Bitový posun ostatních výstupů
     * @param pulsLength Délka pulsu
     */
    public ConfigurationCVEP(String name, int patternValue, int outputCount, int brightness, int bitShift, int pulsLength) {
        super(name, outputCount);

        setMainPattern(patternValue);
        setBrightness(brightness);
        setBitShift(bitShift);
        setPulsLength(pulsLength);

        rearangeOutputs();
    }
    // endregion

    // region Private methods
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

    private void recalculateOutputs() {
        Pattern main = getMainPattern();
        int value = main.getValue();

        for (Pattern pattern : patternList) {
            int shiftValue = Integer.rotateRight(value, bitShift);
            pattern.setValue(shiftValue);
            value = shiftValue;
        }
    }
    // endregion

    // region Public methods
    /**
     * Zjistí, zda-li hodnota odpovídá rozsahu jasu
     * @param val Kontrolovaná hodnota
     * @return True, pokud hodnota odpovídá rozsahu jasu, jinak false
     */
    public boolean isBrightnessInRange(int val) {
        return RangeUtils.isInPercentRange(val);
    }

    @Override
    public ConfigurationCVEP duplicate(String newName) {

        int outputCount = this.outputCount;
        int pulsLength = this.pulsLength;
        int bitShift = this.bitShift;
        int brightness = this.brightness;
        int patternValue = this.mainPattern.value;

        return new ConfigurationCVEP(newName, patternValue, outputCount, brightness, bitShift, pulsLength);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConfigurationCVEP that = (ConfigurationCVEP) o;

        if (pulsLength != that.pulsLength) return false;
        if (bitShift != that.bitShift) return false;
        if (brightness != that.brightness) return false;
        if (!mainPattern.equals(that.mainPattern)) return false;
        return patternList.equals(that.patternList);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + pulsLength;
        result = 31 * result + bitShift;
        result = 31 * result + brightness;
        result = 31 * result + mainPattern.hashCode();
        result = 31 * result + patternList.hashCode();
        return result;
    }

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
    public void setPulsLength(int pulsLength) throws IllegalArgumentException {setPulsLength(pulsLength, null);}
    /**
     * Nastaví délku pulsu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param pulsLength Délka pulsu
     * @param onValueChanged Callback, který se zavolá po nastavení délky pulsu
     */
    public void setPulsLength(int pulsLength, OnValueChanged onValueChanged) throws IllegalArgumentException {
        if (pulsLength < 0)
            throw new IllegalArgumentException();
        if (this.pulsLength == pulsLength)
            return;

        this.pulsLength = pulsLength;
        rearangeOutputs();

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    /**
     * Vrátí číslo představující bitový posun, o který liší jednotlivé patterny
     * @return Bitový posun
     */
    public int getBitShift() {
        return bitShift;
    }

    /**
     * Nastaví bitový posun patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param bitShift Bitový posun
     */
    public void setBitShift(int bitShift) {setBitShift(bitShift, null);}
    /**
     * Nastaví bitový posun patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param pulsSkew Bitový posun
     * @param onValueChanged Callback, který se zavolá po nastavení bitového posunu
     */
    public void setBitShift(int pulsSkew, OnValueChanged onValueChanged) {
        if (this.bitShift == pulsSkew)
            return;

        this.bitShift = pulsSkew;
        recalculateOutputs();

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
    public void setBrightness(int brightness) throws IllegalArgumentException {setBrightness(brightness, null);}
    /**
     * Nastaví jas všem výstupům. Hodnoty jsou možné z intervalu <0 - 100>
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param brightness Jas výstupů
     * @param onValueChanged Callback, který se zavolá po nastavení jasu výstupů
     * @throws IllegalArgumentException Pokud parametr nevyhovuje intervalu
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

    /**
     * Vrátí referenci na hlavní pattern podle kterého se počítají zbylé patterny
     * @return Hlavní pattern
     */
    public Pattern getMainPattern() {
        return mainPattern;
    }
    /**
     * Nastaví hodnotu hlavního patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param value Nová hodnota patternu
     */
    public void setMainPattern(int value) {setMainPattern(value, null);}

    /**
     * Nastaví hodnotu hlavního patternu
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param value Nová hodnota patternu
     * @param onValueChanged Callback, který se zavolá po nastavení nové hodnoty patternu
     */
    public void setMainPattern(int value, final OnValueChanged onValueChanged) {
        mainPattern.setValue(value, new OnValueChanged() {
            @Override
            public void changed() {
                recalculateOutputs();

                if (onValueChanged != null)
                    onValueChanged.changed();
            }
        });
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
         * Konstruktor třídy Pattern
         * @param value Hodnota patternu
         */
        public Pattern(int value) {
            this.value = value;
        }
        // endregion

        // region Public methods
        /**
         * Provede cyklický bitový posun doleva o jeden bit
         */
        public void shiftLeft() {shiftLeft(DEF_BIT_SHIFT);}
        /**
         * Provede cyklický bitový posun doleva
         * @param index O kolik se má číslo posunout. Hodnota musí být větší než 0, jinak se nic nestane
         */
        public void shiftLeft(int index) throws IllegalArgumentException {
            shiftLeft(index, null);
        }
        /**
         * Provede cyklický bitový posun doleva
         * @param index O kolik se má číslo posunout. Hodnota musí být větší než 0, jinak se nic nestane
         * @param onValueChanged Callback, který se zavolá po bitovém posuvu
         */
        public void shiftLeft(int index, OnValueChanged onValueChanged) throws IllegalArgumentException {
            if (!RangeUtils.isInRange(index, 0, PATTERN_LENGTH - 1))
                throw new IllegalArgumentException();
            
            value = Integer.rotateLeft(value, index);
            
            if (onValueChanged != null)
                onValueChanged.changed();
        }

        /**
         * Provede cyklický bitový posun doprava
         */
        public void shiftRight() {shiftRight(DEF_BIT_SHIFT);}
        /**
         * Provede cyklický bitový posun doprava
         * @param index O kolik se má číslo posunout. Hodnota musí být větší než 0, jinak se nic nestane
         */
        public void shiftRight(int index) throws IllegalArgumentException {
            shiftRight(index, null);
        }
        /**
         * Provede cyklický bitový posun doprava
         * @param index O kolik se má číslo posunout. Hodnota musí být větší než 0, jinak se nic nestane
         * @param onValueChanged Callback, který se zavolá po bitovém posuvu
         */
        public void shiftRight(int index, OnValueChanged onValueChanged) throws IllegalArgumentException {
            if (!RangeUtils.isInRange(index, 0, PATTERN_LENGTH - 1))
                throw new IllegalArgumentException();
            
            value = Integer.rotateRight(value, index);
            
            if (onValueChanged != null)
                onValueChanged.changed();
        }

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

    public static final class Builder {
        private String name;
        private int outputCount = DEF_OUTPUT_COUNT;
        private int pulsLength = DEF_PULS_LENGTH;
        private int bitShift = DEF_BIT_SHIFT;
        private int brightness = DEF_BRIGHTNESS;
        private int patternValue = Pattern.DEF_VALUE;

        public Builder(String name){
            this.name = name;
        }

        public Builder outputCount(int outputCount){
            this.outputCount = outputCount;
            return this;
        }

        public Builder pulsLength(int pulsLength){
            this.pulsLength = pulsLength;
            return this;
        }

        public Builder bitShift(int bitShift){
            this.bitShift = bitShift;
            return this;
        }

        public Builder brightness(int brightness){
            this.brightness = brightness;
            return this;
        }

        public Builder mainPattern(int patternValue){
            this.patternValue = patternValue;
            return this;
        }

        public ConfigurationCVEP build(){
            return new ConfigurationCVEP(this.name, this.patternValue, this.outputCount, this.brightness,
                    this.bitShift, this.pulsLength);
        }
    }
}
