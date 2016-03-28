package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.ArrayList;
import java.util.List;

public class ConfigurationFvep extends AItem<ConfigurationFvep> {

    public static final int MIN_OUTPUT_COUNT = 1;
    public static final int DEF_OUTPUT_COUNT = 4;
    public static final int MAX_OUTPUT_COUNT = 8;

    private int outputCount;
    public final List<Output> outputList;

    public ConfigurationFvep(String name) {
        this(name, DEF_OUTPUT_COUNT, new ArrayList<Output>());

        for (int i = 0; i < outputCount; i++) {
            outputList.add(new Output(i + ". stimul"));
        }
    }

    public ConfigurationFvep(String name, int outputCount, List<Output> outputList) {
        super(name);

        this.outputCount = outputCount;
        this.outputList = outputList;
    }

    /**
     * Upraví počet výstupů
     * Pokud je jich víc, než je požadováno, tak odstraní poslední
     * Pokud je jich méně, tak vytvoří nové
     */
    private void rearangeOutputs() {
        int listCount = outputList.size();
        if (outputCount > listCount) {
            int delta = outputCount - listCount;
            for (int i = 0; i < delta; i++) {
                outputList.add(new Output("Output" + i + outputCount));
            }
        } else {
            for (int i = --listCount; i >= outputCount; i--) {
                outputList.remove(i);
            }
        }
    }

    public int getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(int outputCount) {setOutputCount(outputCount, null);}
    public void setOutputCount(int outputCount, OnValueChanged onValueChanged) {
        if (outputCount < MIN_OUTPUT_COUNT || outputCount > MAX_OUTPUT_COUNT)
            throw new IllegalArgumentException();

        if (this.outputCount == outputCount)
            return;

        this.outputCount = outputCount;

        if (outputList.size() != outputCount)
            rearangeOutputs();

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    @Override
    public ConfigurationFvep duplicate(String newName) {
        int outputCount = this.outputCount;
        List<Output> outputList = new ArrayList<>(outputCount);

        for (int i = 0; i < outputCount; i++) {
            outputList.add(new Output(this.outputList.get(i)));
        }

        return new ConfigurationFvep(newName, outputCount, outputList);
    }

    public static final class Output {

        // Název výstupu
        private final String name;
        // Reference pro nastavení pulsu
        public final Puls puls;
        private int frequency;
        private int duty_cycle;
        private int brightness;

        public Output(String name) {
            this(name, new Puls(), 0, 0, 0);
        }

        public Output(String name, Puls puls, int frequency, int duty_cycle, int brightness) {
            this.name = name;
            this.puls = puls;
            this.frequency = frequency;
            this.duty_cycle = duty_cycle;
            this.brightness = brightness;
        }

        public Output(Output source) {
            this.name = source.name;
            this.puls = new Puls(source.puls);
            this.frequency = source.frequency;
            this.duty_cycle = source.duty_cycle;
            this.brightness = source.brightness;
        }

        /**
         * Zjistí, zda-li je číslo v rozsahu
         * @param val Kontrolovaná hodnota
         * @param min Minimální hodnota (včetně)
         * @param max Maximální hodnota (vyjma)
         * @return True, pokud je číslo v rozsahu, jinak false
         */
        public boolean isInRange(int val, int min, int max) {
            return val >= min && val < max;
        }

        /**
         * Zjistí, zda-li hodnota odpovídá frekvenčnímu rozsahu
         * @param val Kontrolovaná hodnota
         * @return True, pokud hodnota odpovídá frekvenčnímu rozsahu, jinak false
         */
        public boolean isFrequencyInRange(int val) {
            return isInRange(val, 0, 256);
        }

        /**
         * Zjistí, zda-li hodnota odpovídá duty cycle rozsahu
         * @param val Kontrolovaná hodnota
         * @return True, pokud hodnota odpovídá duty cycle rozsahu, jinak false
         */
        public boolean isDutyCycleInRange(int val) {
            return isInRange(val, 0, 256);
        }

        /**
         * Zjistí, zda-li hodnota odpovídá rozsahu jasu
         * @param val Kontrolovaná hodnota
         * @return True, pokud hodnota odpovídá rozsahu jasu, jinak false
         */
        public boolean isBrightnessInRange(int val) {
            return isInRange(val, 0, 256);
        }

        public String getName() {
            return name;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {setFrequency(frequency, null);}
        public void setFrequency(int frequency, OnValueChanged onValueChanged) {
            if (this.frequency == frequency)
                return;

            this.frequency = frequency;

            if (onValueChanged != null)
                onValueChanged.changed();
        }

        public int getDutyCycle() {
            return duty_cycle;
        }

        public void setDutyCycle(int duty_cycle) {setDutyCycle(duty_cycle, null);}
        public void setDutyCycle(int duty_cycle, OnValueChanged onValueChanged) {
            if (this.duty_cycle == duty_cycle)
                return;

            this.duty_cycle = duty_cycle;

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
    }

    public static final class Puls {
        // Doba, po kterou jsou výstupy aktivní
        private int up;
        // Doba, po kterou jsou výstupy neaktivní
        private int down;

        /**
         * Konstruktor pulsu
         * Vytvoří nový puls s výchozími hodnotami
         * Up - 0
         * Down - 0
         */
        public Puls() {
            this(0, 0);
        }

        /**
         * Konstruktor pulsu
         * Vytvoří nový puls na základě parametrů
         * @param up Doba, po kterou jsou výstupy aktivní
         * @param down Doba, po kterou jsou výstupy neaktivní
         */
        public Puls(int up, int down) {
            this.up = up;
            this.down = down;
        }

        public Puls(Puls source) {
            this.up = source.up;
            this.down = source.down;
        }

        public int getUp() {
            return up;
        }

        public void setUp(int up) {setUp(up, null);}
        public void setUp(int up, OnValueChanged onValueChanged) {
            if (this.up == up)
                return;

            this.up = up;

            if (onValueChanged != null)
                onValueChanged.changed();
        }

        public int getDown() {
            return down;
        }

        public void setDown(int down) {setDown(down, null);}
        public void setDown(int down, OnValueChanged onValueChanged) {
            if (this.down == down)
                return;

            this.down = down;

            if (onValueChanged != null)
                onValueChanged.changed();
        }
    }
}
