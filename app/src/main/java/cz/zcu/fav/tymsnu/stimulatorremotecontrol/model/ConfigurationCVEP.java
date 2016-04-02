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
    public int getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(int outputCount) {setOutputCount(outputCount, null);}
    public void setOutputCount(int outputCount, OnValueChanged onValueChanged) {
        if (this.outputCount == outputCount)
            return;

        this.outputCount = outputCount;
        rearangeOutputs();

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getPulsLength() {
        return pulsLength;
    }

    public void setPulsLength(int pulsLength) {setPulsLength(pulsLength, null);}
    public void setPulsLength(int pulsLength, OnValueChanged onValueChanged) {
        if (this.pulsLength == pulsLength)
            return;

        this.pulsLength = pulsLength;
        rearangeOutputs();

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getPulsSkew() {
        return pulsSkew;
    }

    public void setPulsSkew(int pulsSkew) {setPulsSkew(pulsSkew, null);}
    public void setPulsSkew(int pulsSkew, OnValueChanged onValueChanged) {
        if (this.pulsSkew == pulsSkew)
            return;

        this.pulsSkew = pulsSkew;
        recalculateOutputs();

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {setBrightness(brightness, null);}
    public void setBrightness(int brightness, OnValueChanged onValueChanged) {
        if (this.brightness == brightness)
            return;

        this.brightness = brightness;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public Pattern getMainPattern() {
        return mainPattern;
    }

    public void setMainPattern(int value) {setMainPattern(value, null);}
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
        public void shiftLeft() {shiftLeft(DEF_SHIFT);}
        public void shiftLeft(int index) {
            shiftLeft(index, null);
        }
        public void shiftLeft(int index, OnValueChanged onValueChanged) {
            if (index <= 0)
                return;
            
            value = Integer.rotateLeft(value, index);
            
            if (onValueChanged != null)
                onValueChanged.changed();
        }
        
        public void shiftRight() {shiftRight(DEF_SHIFT);}
        public void shiftRight(int index) {
            shiftRight(index, null);
        }
        public void shiftRight(int index, OnValueChanged onValueChanged) {
            if (index <= 0)
                return;
            
            value = Integer.rotateRight(value, index);
            
            if (onValueChanged != null)
                onValueChanged.changed();
        }
        // endregion
        
        // region Getters & Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {setName(name, null);}
        public void setName(String name, OnValueChanged onValueChanged) {
            if (this.name.equals(name) || name.isEmpty())
                return;

            this.name = name;

            if (onValueChanged != null)
                onValueChanged.changed();
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {setValue(value, null);}
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
