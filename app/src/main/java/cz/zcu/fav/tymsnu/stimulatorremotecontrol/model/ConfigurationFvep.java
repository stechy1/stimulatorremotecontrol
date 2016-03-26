package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.ArrayList;
import java.util.List;

public class ConfigurationFvep extends AItem {

    public static final int MIN_OUTPUT_COUNT = 1;
    public static final int MAX_OUTPUT_COUNT = 8;

    private int outputCount;
    public final List<Output> outputList;

    public ConfigurationFvep(String name) {
        this(name, MIN_OUTPUT_COUNT, new ArrayList<Output>());

        for (int i = 0; i < outputCount; i++) {
            outputList.add(new Output(i + ". stimul"));
        }
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

    public void setOutputCount(int outputCount) {
        if (outputCount < MIN_OUTPUT_COUNT || outputCount > MAX_OUTPUT_COUNT)
            throw new IllegalArgumentException();

        this.outputCount = outputCount;

        if (outputList.size() != outputCount)
            rearangeOutputs();
    }

    public ConfigurationFvep(String name, int outputCount, List<Output> outputList) {
        super(name);

        this.outputCount = outputCount;
        this.outputList = outputList;
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

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getDutyCycle() {
            return duty_cycle;
        }

        public void setDutyCycle(int duty_cycle) {
            this.duty_cycle = duty_cycle;
        }

        public int getBrightness() {
            return brightness;
        }

        public void setBrightness(int brightness) {
            this.brightness = brightness;
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

        public int getUp() {
            return up;
        }

        public void setUp(int up) {
            this.up = up;
        }

        public int getDown() {
            return down;
        }

        public void setDown(int down) {
            this.down = down;
        }
    }
}
