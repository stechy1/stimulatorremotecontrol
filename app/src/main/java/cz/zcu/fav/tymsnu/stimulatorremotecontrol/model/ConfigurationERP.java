package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.ArrayList;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtils;

public final class ConfigurationERP extends AItem<ConfigurationERP> {

    // region Variables
    // Výchozí počet výstupů (stará verze 4)
    public static final int MIN_OUTPUT_COUNT = 1;
    public static final int DEF_OUTPUT_COUNT = 4;
    public static final int MAX_OUTPUT_COUNT = 8;

    // Počet výstupů
    private int outputCount;
    private int out;
    private int wait;
    // Nastavení hrany (náběžná/sestupná)
    private Edge edge;
    // Nastavení náhodnosti (žádná/krátká/dlouhá/krátká i dlouhá)
    private Random random;
    // Kolekce udržující výstupy
    public final List<Output> outputList;
    // endregion

    // region Constructors

    /**
     * Konstruktor schématu
     * Vytvoří nové schéma s výchozími hodnotami
     * @param name Název schématu
     */
    public ConfigurationERP(String name) {
        this(name, DEF_OUTPUT_COUNT, 0, 0, Edge.FALLING, Random.OFF, new ArrayList<Output>());

        for (int i = 0; i < outputCount; i++) {
            outputList.add(new Output("Output" + i));
        }
    }

    /**
     * Konstruktor schématu
     * Vytvoří nové schéma na základě parametrů
     * @param name Název schématu
     * @param outputCount Počet výstupů
     * @param edge Typ hrany
     * @param random Náhodnost
     * @param outputList Reference na kolekci výstupů
     */
    public ConfigurationERP(String name, int outputCount, int out, int wait, Edge edge, Random random, List<Output> outputList) {
        super(name);

        this.outputCount = outputCount;
        this.out = out;
        this.wait = wait;
        this.edge = edge;
        this.random = random;
        this.outputList = outputList;
    }
    // endregion

    // region Private methods

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
    // endregion

    // region Public methods
    @Override
    public ConfigurationERP duplicate(String newName) {
        int outputCount = this.outputCount;
        int out = this.out;
        int wait = this.wait;
        Edge edge = Edge.valueOf(this.random.ordinal());
        Random random = Random.valueOf(this.random.ordinal());
        List<Output> outputs = new ArrayList<>(outputCount);

        for (int i = 0; i < outputCount; i++) {
            outputs.add(new Output(this.outputList.get(i)));
        }

        return new ConfigurationERP(newName, outputCount, out, wait, edge, random, outputs);
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
     * Vrátí hodnotu parametru out
     * @return Hodnota parametru out
     */
    public int getOut() {
        return out;
    }

    /**
     * Nastaví parametr out
     * @param out Číslo
     */
    public void setOut(int out) {setOut(out, null);}
    /**
     * Nastaví parametr out
     * @param out Číslo
     * @param onValueChanged Callback, který se zavolá po změně hodnoty
     */
    public void setOut(int out, OnValueChanged onValueChanged) {
        if (this.out == out)
            return;


        this.out = out;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    /**
     * Vrátí hodnotu parametru wait
     * @return Hodnota parametru wait
     */
    public int getWait() {
        return wait;
    }

    /**
     * Nastaví parametr wait
     * @param wait Číslo
     */
    public void setWait(int wait) {setWait(wait, null);}
    /**
     * Nastaví parametr wait
     * @param wait Číslo
     * @param onValueChanged Callback, který se zavolá po změně hodnoty
     */
    public void setWait(int wait, OnValueChanged onValueChanged) {
        if (this.wait == wait)
            return;

        this.wait = wait;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {setEdge(edge, null);}
    public void setEdge(Edge edge, OnValueChanged onValueChanged) {
        if (this.edge.equals(edge))
            return;

        this.edge = edge;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {setRandom(random, null);}
    public void setRandom(Random random, OnValueChanged onValueChanged) {
        if (this.random.equals(random))
            return;

        this.random = random;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    // endregion

    // region Enums
    public enum Edge {
        LEADING, FALLING;

        public static Edge valueOf(int index) {
            switch (index) {
                case 0:
                    return LEADING;
                case 1:
                    return FALLING;
                default:
                    return LEADING;
            }
        }
    }

    public enum Random {
        OFF, SHORT, LONG, SHORT_LONG;

        public static Random valueOf(int index) {
            switch (index) {
                case 0:
                    return OFF;
                case 1:
                    return SHORT;
                case 2:
                    return LONG;
                case 3:
                    return SHORT_LONG;
                default:
                    return OFF;
            }
        }
    }
    // endregion

    public static final class Output {

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

        public Output(Output source) {
            this.name = source.name;
            this.puls = new Puls(source.puls);
            this.distribution = new Distribution(source.distribution);
            this.brightness = source.brightness;
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

        /**
         * Zjistí, zda-li hodnota odpovídá rozsahu jasu
         * @param val Kontrolovaná hodnota
         * @return True, pokud hodnota odpovídá rozsahu jasu, jinak false
         */
        public boolean isBrightnessInRange(int val) {
            return RangeUtils.isInPercentRange(val);
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

            /**
             * Vytvoří kopii objektu
             * @param source Zdroj kopie
             */
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
             * @param value Patametr value
             * @param delay Patametr delay
             */
            public Distribution(int value, int delay) {
                this.value = value;
                this.delay = delay;
            }

            /**
             * Vytvoří kopii objektu
             * @param source Zdroj kopie
             */
            public Distribution(Distribution source) {
                this.value = source.value;
                this.delay = source.delay;
            }

            /**
             * Zdjistí, zda-li zadaná hodnota odpovídá distribution rozsahu
             * @param value Kontrolovaná hodnota
             * @return True, pokud hodnota odpovídá distribution rozsahu, jinak false
             */
            public boolean isValueInRange(int value){
                return RangeUtils.isInPercentRange(value);
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

            public int getDelay() {
                return delay;
            }

            public void setDelay(int delay) {setDelay(delay, null);}
            public void setDelay(int delay, OnValueChanged onValueChanged) {
                if (this.delay == delay)
                    return;

                this.delay = delay;

                if (onValueChanged != null)
                    onValueChanged.changed();
            }
        }
        // endregion
    }
}
