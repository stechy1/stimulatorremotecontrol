package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Code;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Codes;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.DataConvertor;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtils;

public final class ConfigurationERP extends AConfiguration<ConfigurationERP> {

    // region Variables
    // Výchozí hodnota parametru out
    public static final int DEF_OUT = 0;
    // Výchozí hodnota parametru wait
    public static final int DEF_WAIT = 0;

    private int out;
    private int wait;
    // Nastavení hrany (náběžná/sestupná)
    private Edge edge;
    // Nastavení náhodnosti (žádná/krátká/dlouhá/krátká i dlouhá)
    private Random random;
    // Kolekce všech výstupů
    public final List<Output> outputList;
    // endregion

    // region Constructors

    /**
     * Konstruktor schématu
     * Vytvoří nové schéma s výchozími hodnotami
     * @param name Název schématu
     * @throws IllegalArgumentException Pokud je nějaký parametr null
     */
    public ConfigurationERP(String name) {
        this(name, DEF_OUTPUT_COUNT, DEF_OUT, DEF_WAIT, Edge.FALLING, Random.OFF, new ArrayList<Output>());
    }

    /**
     * Konstruktor schématu
     * Vytvoří nové schéma na základě parametrů
     * @param name Název schématu
     * @param outputCount Počet výstupů
     * @param edge Typ hrany
     * @param random Náhodnost
     * @param outputList Reference na kolekci výstupů
     * @throws IllegalArgumentException Pokud je nějaký parametr null
     */
    public ConfigurationERP(String name, int outputCount, int out, int wait, Edge edge, Random random, List<Output> outputList) throws IllegalArgumentException {
        super(name, outputCount);

        if (edge == null || random == null || outputList == null)
            throw new IllegalArgumentException();

        this.outputList = outputList;

        setOut(out);
        setWait(wait);
        setEdge(edge);
        setRandom(random);

        if (this.outputCount != this.outputList.size())
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
        int listCount = outputList.size();
        if (outputCount > listCount) {
            int delta = outputCount - listCount;
            for (int i = 0; i < delta; i++) {
                outputList.add(new Output());
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
        Edge edge = Edge.valueOf(this.edge.ordinal());
        Random random = Random.valueOf(this.random.ordinal());
        List<Output> outputs = new ArrayList<>(outputCount);

        for (int i = 0; i < outputCount; i++) {
            outputs.add(Output.clone(this.outputList.get(i)));
        }

        return new ConfigurationERP(newName, outputCount, out, wait, edge, random, outputs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConfigurationERP that = (ConfigurationERP) o;

        if (out != that.out) return false;
        if (wait != that.wait) return false;
        if (edge != that.edge) return false;
        if (random != that.random) return false;
        return outputList.equals(that.outputList);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + out;
        result = 31 * result + wait;
        result = 31 * result + edge.hashCode();
        result = 31 * result + random.hashCode();
        result = 31 * result + outputList.hashCode();
        return result;
    }

    public ArrayList<Packet> getPackets() {

        ArrayList<Packet> packets = new ArrayList<>();

        packets.add(new Packet(Codes.EDGE, DataConvertor.intTo1B(edge.ordinal())));
        packets.add(new Packet(Codes.RANDOMNESS_ON, DataConvertor.intTo1B(random.ordinal()))); //TODO jak je to s tím kódem náhodnosti?

        Code actualDURATION = Codes.OUTPUT0_DURATION;
        Code actualPAUSE = Codes.OUTPUT0_PAUSE;
        Code actualDISTRIBUTION = Codes.OUTPUT0_DISTRIBUTION;
        Code actualBRIGHTNESS = Codes.OUTPUT0_BRIGHTNESS;

        int vystup = 0; //index výstupu, slouží pro odfiltrování jasu kvůli sdružení u LED 5 a 7

        for(Output a : outputList){
            packets.add(new Packet(actualDURATION, DataConvertor.milisecondsTo2B(a.puls.up)));
            packets.add(new Packet(actualPAUSE, DataConvertor.milisecondsTo2B(a.puls.down)));
            packets.add(new Packet(actualDISTRIBUTION, DataConvertor.intTo1B(a.distribution.value))); //TODO u distribution parametru ještě neposíláme delay

            if(vystup != 5 && vystup != 7) {  //neukládáme hodnoty pro výstupy 5 a 7 protože jsou sdružené (bereme ty nižší)
                packets.add(new Packet(actualBRIGHTNESS, DataConvertor.intTo1B(a.brightness)));
                actualBRIGHTNESS = actualBRIGHTNESS.getNext();
            }

            actualDURATION = actualDURATION.getNext();
            actualPAUSE = actualPAUSE.getNext();
            actualDISTRIBUTION = actualDISTRIBUTION.getNext();


            vystup++;
        }

        return packets;
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

    public void setEdge(Edge edge) throws IllegalArgumentException {setEdge(edge, null);}
    public void setEdge(Edge edge, OnValueChanged onValueChanged) throws IllegalArgumentException {
        if (edge == null)
            throw new IllegalArgumentException();

        if (this.edge != null && this.edge.equals(edge))
            return;

        this.edge = edge;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) throws IllegalArgumentException {setRandom(random, null);}
    public void setRandom(Random random, OnValueChanged onValueChanged) throws IllegalArgumentException {
        if (random == null)
            throw new IllegalArgumentException();

        if (this.random != null && this.random.equals(random))
            return;

        this.random = random;

        if (onValueChanged != null)
            onValueChanged.changed();
    }

    // endregion

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

    public static final class Output {

        // region Variables
        // Výchozí hodnota jasu výstupu
        public static final int DEF_BRIGHTNESS = 0;
        // Reference pro nastavení pulsu
        public final Puls puls;
        // Reference pro nastavení rozdělení
        public final Distribution distribution;
        // Intenzita jasu [%](0-100)
        private int brightness;
        // endregion

        // region Private static methods
        public static Output clone(Output source) throws IllegalArgumentException {
            if (source == null)
                throw new IllegalArgumentException();

            return new Output(new Puls(source.puls), new Distribution(source.distribution), source.brightness);
        }
        // endregion

        // region Constructors

        /**
         * Vytvoří nový výstup s výchozími hodnotami
         */
        public Output() {
            this(new Puls(), new Distribution(), DEF_BRIGHTNESS);
        }

        /**
         * Konstruktor výstupu
         * Vytvoří nový výstup na zákadě parametrů
         * @param puls Reference na nastavení pulsu
         * @param distribution Reference na nastavení rozdělení
         * @param brightness Intenzita jasu [%](0-100)
         */
        public Output(Puls puls, Distribution distribution, int brightness) throws IllegalArgumentException {
            if (puls == null || distribution == null)
                throw new IllegalArgumentException();

            this.puls = puls;
            this.distribution = distribution;
            setBrightness(brightness);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Output output = (Output) o;

            if (brightness != output.brightness) return false;
            if (!puls.equals(output.puls)) return false;
            return distribution.equals(output.distribution);

        }

        @Override
        public int hashCode() {
            int result = puls.hashCode();
            result = 31 * result + distribution.hashCode();
            result = 31 * result + brightness;
            return result;
        }

        // endregion

        // region Getters & Setters
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
         * @throws IllegalArgumentException Pokud parametr nevyhovuje intervalue
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

        // endregion

        public static final class Puls {
            // region Variables
            // Výchozí hodnota parametru up
            public static final int DEF_UP = 0;
            // Výchozí hodnota parametru down
            public static final int DEF_DOWN = 0;

            // Doba, po kterou jsou výstupy aktivní
            private int up;
            // Doba, po kterou jsou výstupy neaktivní
            private int down;
            // endregion

            // region Constructors
            /**
             * Konstruktor pulsu
             * Vytvoří nový puls s výchozími hodnotami
             * Up - 0
             * Down - 0
             */
            public Puls() {
                this(DEF_UP, DEF_DOWN);
            }

            /**
             * Vytvoří kopii objektu
             * @param source Zdroj kopie
             */
            public Puls(Puls source) {
                this(source.up, source.down);
            }

            /**
             * Konstruktor pulsu
             * Vytvoří nový puls na základě parametrů
             * @param up Doba, po kterou jsou výstupy aktivní
             * @param down Doba, po kterou jsou výstupy neaktivní
             */
            public Puls(int up, int down) {
                setUp(up);
                setDown(down);
            }
            // endregion

            // region Public methods

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Puls puls = (Puls) o;

                if (up != puls.up) return false;
                return down == puls.down;

            }

            @Override
            public int hashCode() {
                int result = up;
                result = 31 * result + down;
                return result;
            }

            // endregion

            // region Getters & Setters
            /**
             * Vrátí dobu [ms] po kterou je výstup aktivní
             * @return Doba [ms] po kterou je výstup aktivní
             */
            public int getUp() {
                return up;
            }

            /**
             * Nastaví dobu [ms], po kterou je výstup aktivní
             * @param up Doba [ms] po kterou je výstup aktivní
             */
            public void setUp(int up) {setUp(up, null);}

            /**
             * Nastaví dobu [ms], po kterou je výstup aktivní
             * @param up Doba [ms] po kterou je výstup aktivní
             * @param onValueChanged Callback, který se zavolá po nastavení parametru
             */
            public void setUp(int up, OnValueChanged onValueChanged) {
                if (this.up == up)
                    return;

                this.up = up;

                if (onValueChanged != null)
                    onValueChanged.changed();
            }

            /**
             * Vrátí dobu [ms] po kterou je výstup neaktivní
             * @return Doba [ms] po kterou je výstup neaktivní
             */
            public int getDown() {
                return down;
            }

            /**
             * Nastaví dobu [ms] po kterou je výstup neaktivní
             * @param down Doba [ms] po kterou je výstup neaktivní
             */
            public void setDown(int down) {setDown(down, null);}
            /**
             * Nastaví dobu [ms] po kterou je výstup neaktivní
             * @param down Doba [ms] po kterou je výstup neaktivní
             * @param onValueChanged Callback, který se zavolá po nastavení parametru
             */
            public void setDown(int down, OnValueChanged onValueChanged) {
                if (this.down == down)
                    return;

                this.down = down;

                if (onValueChanged != null)
                    onValueChanged.changed();
            }
            // endregion
        }

        public static final class Distribution {
            // region Variables
            // Výchozí hodnota parametru value
            public static final int DEF_VALUE = 0;
            // Výchozí hodnota parametru delay
            public static final int DEF_DELAY = 0;
            // Rozdělení pravděpodobnosti, počet bliknutí výstupu je odvozen z této hodnoty.
            // Mezi n výstupů je rozděleno 100%
            private int value;

            private int delay;
            // endregion

            // region Constructors
            /**
             * Konstruktor rozdělení
             * Vytvoří nové rozdělení s výchozími hodnotami
             * Value - 0
             * Delay - 0
             */
            public Distribution() {
                this(DEF_VALUE, DEF_DELAY);
            }

            /**
             * Vytvoří kopii objektu
             * @param source Zdroj kopie
             */
            public Distribution(Distribution source) {
                this(source.value, source.delay);
            }

            /**
             * Konstruktor rozdělení
             * Vytvoří nové rozdělení na základě parametrů
             * @param value Patametr value
             * @param delay Patametr delay
             */
            public Distribution(int value, int delay) {
                setValue(value);
                setDelay(delay);
            }
            // endregion

            // region Public methods

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Distribution that = (Distribution) o;

                if (value != that.value) return false;
                return delay == that.delay;

            }

            @Override
            public int hashCode() {
                int result = value;
                result = 31 * result + delay;
                return result;
            }

            // endregion

            // region Getters & Setters
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

            public void setValue(int value) throws IllegalArgumentException {setValue(value, null);}
            public void setValue(int value, OnValueChanged onValueChanged) throws IllegalArgumentException {
                if (!isValueInRange(value))
                    throw new IllegalArgumentException();

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
            // endregion
        }

        public static final class Builder {
            private Puls puls = new Puls();
            private Distribution distribution = new Distribution();
            private int brightness = DEF_BRIGHTNESS;

            public Builder puls(Puls puls) {
                if (puls == null)
                    return this;

                this.puls = puls;
                return this;
            }

            public Builder distribution(Distribution distribution) {
                if (distribution == null)
                    return this;

                this.distribution = distribution;
                return this;
            }

            public Builder brightness(int brightness) {
                this.brightness = brightness;
                return this;
            }

            public Output build() {
                return new Output(puls, distribution, brightness);
            }
        }

    }

    public static final class Builder {
        private String name;
        private int outputCount = AConfiguration.DEF_OUTPUT_COUNT;
        private int out = DEF_OUT;
        private int wait = DEF_WAIT;
        private Edge edge = Edge.FALLING;
        private Random random = Random.OFF;
        private List<Output> outputList = new ArrayList<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder outputCount(int outputCount) {
            this.outputCount = outputCount;
            return this;
        }

        public Builder out(int out) {
            this.out = out;
            return this;
        }

        public Builder wait (int wait) {
            this.wait = wait;
            return this;
        }

        public Builder edge (Edge edge) {
            if (edge == null)
                return this;

            this.edge = edge;
            return this;
        }

        public Builder random (Random random) {
            if (random == null)
                return this;

            this.random = random;
            return this;
        }

        public Builder outputList (List<Output> outputList) {
            if (outputList == null)
                return this;

            this.outputList = outputList;
            return this;
        }

        public ConfigurationERP build() {
            return new ConfigurationERP(name, outputCount, out, wait, edge, random, outputList);
        }
    }
}
