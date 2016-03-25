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

    public int getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(int outputCount) {
        this.outputCount = outputCount;
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
