package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationCVEP extends AItem<ConfigurationCVEP> {

    // region Variables
    private int outputCount;
    private int pulsLength;
    private int pulsSkew;
    private int brightness;
    private Pattern mainPattern;
    public final List<Pattern> patternList = new ArrayList<>();
    // endregion

    // region Constructors
    public ConfigurationCVEP(String name) {
        this(name, new Pattern("Main pattern"), 0, 0, 0, 1);
    }

    public ConfigurationCVEP(String name, Pattern mainPattern, int brightness, int pulsSkew, int pulsLength, int outputCount) {
        super(name);
        this.mainPattern = mainPattern;
        this.brightness = brightness;
        this.pulsSkew = pulsSkew;
        this.pulsLength = pulsLength;
        this.outputCount = outputCount;
    }
    // endregion
    
    // region Private methods
    private void rearangeOutputs() {
        int listCount = patternList.size();
        if (outputCount > listCount) {
            int delta = outputCount - listCount;
            for (int i = 0; i < delta; i++) {
                patternList.add(new Pattern("Pattern" + i + outputCount));
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
            int shiftValue = Integer.rotateRight(value, pulsSkew);
            pattern.setValue(shiftValue);
            value = shiftValue;
        }
    }
    // endregion

    // region Public methods
    @Override
    public ConfigurationCVEP duplicate(String newName) {
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
        public static final int DEF_SHIFT = 1;
        private String name;
        private int value;
        // endregion

        // region Constructors
        public Pattern(String name) {
            this(name, 0);
        }
        public Pattern(String name, int value) {
            this.name = name;
            this.value = value;
        }
        // endregion

        // region Public methods
        /**
         * Provede cyklický bitový posun doleva o jeden bit
         */
        public void shiftLeft() {shiftLeft(DEF_SHIFT);}
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
        public void shiftRight() {shiftRight(DEF_SHIFT);}
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
         * Vrátí název patternu
         * @return Název patternu
         */
        public String getName() {
            return name;
        }

        /**
         * Nastaví název patternu
         * @param name Nový název patternu
         */
        public void setName(String name) {setName(name, null);}
        /**
         * Nastaví název patternu
         * @param name Nový název patternu
         * @param onValueChanged Callback, který se zavolá po nastavení nového názvu patternu
         */
        public void setName(String name, OnValueChanged onValueChanged) {
            if (this.name.equals(name) || name.isEmpty())
                return;

            this.name = name;

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
