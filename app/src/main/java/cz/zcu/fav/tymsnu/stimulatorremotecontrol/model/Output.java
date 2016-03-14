package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.List;

public final class Output {

    // region Variables
    // Název výstupu
    private final String name;
    // Reference pro nastavení pulsu
    public final Puls puls;
    // Reference pro nastavení rozdělení
    public final Distribution distribution;
    // Intenzita jasu [%](0-100)
    private int brightness;
    // endregion

    // region Constructors

    /**
     * Konstruktor výstupu
     * Vytvoří nový výstup s výchozími hodnotami
     * @param name Název výstupu
     */
    public Output(String name) {
        this(name, new Puls(), new Distribution(), 0);
    }

    /**
     * Konstruktor výstupu
     * Vytvoří nový výstup na zákadě parametrů
     * @param name Název výstupu
     * @param puls Reference na nastavení pulsu
     * @param distribution Reference na nastavení rozdělení
     * @param brightness Intenzita jasu [%](0-100)
     */
    public Output(String name, Puls puls, Distribution distribution, int brightness) {
        this.name = name;
        this.puls = puls;
        this.distribution = distribution;
        this.brightness = brightness;
    }
    // endregion

    // region Private methods

    // endregion

    // region Public methods
    public boolean canUpdateDistribution(List<Output> outputs, int val) {
        int sum = 0;
        for (Output output : outputs) {
            sum += output.distribution.getValue();
        }
        sum -= distribution.getValue();

        return sum + val <= 100;

    }
    // endregion

    // region Getters & Setters

    /**
     * Vrátí jméno
     * @return jméno
     */
    public String getName() {
        return name;
    }

    /**
     * Vrátí intenzitu jasu
     * @return intenzita jasu
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * Nastaví intenzitu jasu
     * Hodnota musí být v rozmězí <0-100>
     * Pokud bude hodnota jiná, nic se nenastaví
     * @param brightness Intenzita jasu
     */
    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 100)
            return;

        this.brightness = brightness;
    }

    // endregion

    // region Classes
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

    public static final class Distribution {
        private int value;
        private int delay;

        /**
         * Konstruktor rozdělení
         * Vytvoří nové rozdělení s výchozími hodnotami
         * Value - 0
         * Delay - 0
         */
        public Distribution() {
            this(0, 0);
        }

        /**
         * Konstruktor rozdělení
         * Vytvoří nové rozdělení na základě parametrů
         * @param value
         * @param delay
         */
        public Distribution(int value, int delay) {
            this.value = value;
            this.delay = delay;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }
    }
    // endregion
}
