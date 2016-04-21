package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationCVEP extends AConfiguration<ConfigurationCVEP> {

    // region Variables
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
    public ConfigurationCVEP(String name) {
        this(name, new Pattern(), DEF_OUTPUT_COUNT, DEF_BRIGHTNESS, DEF_BIT_SHIFT, DEF_PULS_LENGTH);
    }

    public ConfigurationCVEP(String name, Pattern mainPattern, int outputCount, int brightness, int bitShift, int pulsLength) {
        super(name, outputCount);

        setMainPattern(mainPattern.value);
        setBrightness(brightness);
        setBitShift(bitShift);
        setPulsLength(pulsLength);
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
    @Override
    public ConfigurationCVEP duplicate(String newName) {

        int outputCount = this.outputCount;
        int pulsLength = this.pulsLength;
        int bitShift = this.bitShift;
        int brightness = this.brightness;

        Pattern mainPattern = new Pattern(this.mainPattern);

        return new ConfigurationCVEP(newName, mainPattern, brightness, bitShift, pulsLength, outputCount);
    }
    // endregion
    
    // region Getters & Setters
    /**
     * Nastaví počet výstupů
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param outputCount Počet výstupů
     * @param onValueChanged Callback, který se zavolá po nastavení počtu výstupů
     */
    public void setOutputCount(int outputCount, OnValueChanged onValueChanged) {
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

        // region Constructors
        /**
         * Konstruktor třídy Pattern s výchozí hodnotou
         */
        public Pattern() {
            this(DEF_VALUE);
        }

        /**
         * Konstruktor třídy Pattern
         * Vytvoří kopii podle předlohy
         * @param source Předloha
         */
        public Pattern(Pattern source){
            this(source.getValue());
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
        public void shiftLeft(int index) {
            shiftLeft(index, null);
        }
        /**
         * Provede cyklický bitový posun doleva
         * @param index O kolik se má číslo posunout. Hodnota musí být větší než 0, jinak se nic nestane
         * @param onValueChanged Callback, který se zavolá po bitovém posuvu
         */
        public void shiftLeft(int index, OnValueChanged onValueChanged) {
            if (index <= 0)
                return;
            
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
        public void shiftRight(int index) {
            shiftRight(index, null);
        }
        /**
         * Provede cyklický bitový posun doprava
         * @param index O kolik se má číslo posunout. Hodnota musí být větší než 0, jinak se nic nestane
         * @param onValueChanged Callback, který se zavolá po bitovém posuvu
         */
        public void shiftRight(int index, OnValueChanged onValueChanged) {
            if (index <= 0)
                return;
            
            value = Integer.rotateRight(value, index);
            
            if (onValueChanged != null)
                onValueChanged.changed();
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
        private Pattern mainPattern = new Pattern();

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

        public Builder mainPattern(Pattern mainPattern){
            if(mainPattern == null) return this;

            this.mainPattern = mainPattern;
            return this;
        }

        public ConfigurationCVEP build(){
            return new ConfigurationCVEP(this.name, this.mainPattern, this.outputCount, this.brightness,
                    this.bitShift, this.pulsLength);
        }
    }
}
