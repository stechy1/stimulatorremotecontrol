package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.ArrayList;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtils;

public class ConfigurationFVEP extends AConfiguration<ConfigurationFVEP> {

    // region Variables
    // Kolekce všech výstupů
    public final List<Output> outputList;
    // endregion

    // region Constructors
    /**
     * Jednoduchý konstruktor třídy ConfigurationFVEP s výchozím počtem výstupů
     * @param name Název konfigurace
     * @throws IllegalArgumentException Pokud je parametr outputList null
     */
    public ConfigurationFVEP(String name) throws IllegalArgumentException {
        this(name, DEF_OUTPUT_COUNT, new ArrayList<Output>());
    }

    /**
     * Konstruktor třídy ConfigurationFVEP
     * @param name Název konfigurace
     * @param outputCount Počet výstupů
     * @param outputList Kolekce výstupů.
     *                   Pokud velikost kolekce nebude shodná s parametrem počet výstupů, tak se kolekce automaticky přeurčí
     * @throws IllegalArgumentException Pokud je parametr outputList null
     */
    public ConfigurationFVEP(String name, int outputCount, List<Output> outputList) throws IllegalArgumentException {
        super(name, outputCount);

        if (outputList == null)
            throw new IllegalArgumentException();

        this.outputList = outputList;

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
    public ConfigurationFVEP duplicate(String newName) {
        int outputCount = this.outputCount;
        List<Output> outputList = new ArrayList<>(outputCount);

        for (int i = 0; i < outputCount; i++) {
            outputList.add(new Output(this.outputList.get(i)));
        }

        return new ConfigurationFVEP(newName, outputCount, outputList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConfigurationFVEP that = (ConfigurationFVEP) o;

        return outputList.equals(that.outputList);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + outputList.hashCode();
        return result;
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
    // endregion

    public static final class Output {

        // region Variables
        public static final int DEF_FREQUENCY = 0;
        public static final int DEF_DUTY_CYCLE = 0;
        public static final int DEF_BRIGHTNESS = 0;
        // Reference pro nastavení pulsu
        public final Puls puls;
        private int frequency;
        // Hodnota v procentech [0 - 100], která určuje délku pulzu při nastavení frekvence
        private int duty_cycle;
        private int brightness;
        // endregion

        // region Constructors
        /**
         * Vytvoří nový výstup s výchozími hodnotami
         */
        public Output() {
            this(new Puls(), DEF_FREQUENCY, DEF_DUTY_CYCLE, DEF_BRIGHTNESS);
        }

        /**
         * Vytvoří nový výstup jako kopii ze zadané třídy
         * @param source Předloha
         */
        public Output(Output source) {
            this(new Puls(source.puls), source.frequency, source.duty_cycle, source.brightness);
        }

        /**
         * Vytvoří nový výstup z parametrů
         * @param puls
         * @param frequency
         * @param duty_cycle Délka pulzu při nastavení frekvence
         * @param brightness Jas výstupu
         * @throws IllegalArgumentException Pokud se vloží nějaký parametr null
         */
        public Output(Puls puls, int frequency, int duty_cycle, int brightness) {
            if (puls == null)
                throw new IllegalArgumentException();

            this.puls = puls;

            setFrequency(frequency);
            setDutyCycle(duty_cycle);
            setBrightness(brightness);
        }
        // endregion

        // region Public methods
        /**
         * Zjistí, zda-li hodnota odpovídá frekvenčnímu rozsahu
         * @param val Kontrolovaná hodnota
         * @return True, pokud hodnota odpovídá frekvenčnímu rozsahu, jinak false
         */
        public boolean isFrequencyInRange(int val) {
            return RangeUtils.isInByteRange(val, 1);
        }

        /**
         * Zjistí, zda-li hodnota odpovídá duty cycle rozsahu
         * @param val Kontrolovaná hodnota
         * @return True, pokud hodnota odpovídá duty cycle rozsahu, jinak false
         */
        public boolean isDutyCycleInRange(int val) {
            return RangeUtils.isInPercentRange(val);
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

            if (frequency != output.frequency) return false;
            if (duty_cycle != output.duty_cycle) return false;
            if (brightness != output.brightness) return false;
            return puls.equals(output.puls);

        }

        @Override
        public int hashCode() {
            int result = puls.hashCode();
            result = 31 * result + frequency;
            result = 31 * result + duty_cycle;
            result = 31 * result + brightness;
            return result;
        }

        // endregion

        // region Getters & Setters
        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) throws IllegalArgumentException {setFrequency(frequency, null);}
        public void setFrequency(int frequency, OnValueChanged onValueChanged) throws IllegalArgumentException {
            if (!isFrequencyInRange(frequency))
                throw new IllegalArgumentException();

            if (this.frequency == frequency)
                return;

            this.frequency = frequency;

            if (onValueChanged != null)
                onValueChanged.changed();
        }

        public int getDutyCycle() {
            return duty_cycle;
        }

        public void setDutyCycle(int duty_cycle) throws IllegalArgumentException {setDutyCycle(duty_cycle, null);}
        public void setDutyCycle(int duty_cycle, OnValueChanged onValueChanged) throws IllegalArgumentException {
            if (!isDutyCycleInRange(duty_cycle))
                throw new IllegalArgumentException();

            if (this.duty_cycle == duty_cycle)
                return;

            this.duty_cycle = duty_cycle;

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
        // endregion

        public static final class Builder {
            private Puls puls = new Puls();
            private int frequency = DEF_FREQUENCY;
            private int duty_cycle = DEF_DUTY_CYCLE;
            private int brightness = DEF_BRIGHTNESS;

            public Builder puls(Puls puls) {
                this.puls = puls;
                return this;
            }

            public Builder frequency(int frequency) {
                this.frequency = frequency;
                return this;
            }

            public Builder duty_cycle(int duty_cycle) {
                this.duty_cycle = duty_cycle;
                return this;
            }

            public Builder brightness(int brightness) {
                this.brightness = brightness;
                return this;
            }

            public Output build() {
                return new Output(puls, frequency, duty_cycle, brightness);
            }
        }
    }

    public static final class Puls {
        // region Variables
        public static final int DEF_UP = 0;
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
         * Konstruktor pulsu
         * Vytvoří kopii podle předlohy
         * @param source Zdrojový puls
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
        // endregion

        public static final class Builder {
            private int up;
            private int down;

            public Builder up(int up) {
                this.up = up;
                return this;
            }
            public Builder down(int down) {
                this.down = down;
                return this;
            }

            public Puls build() {
                return new Puls(up, down);
            }
        }
    }

    public static final class Builder {
        private List<Output> outputList = new ArrayList<>();
        private String name;
        private int outputCount = DEF_OUTPUT_COUNT;

        public Builder(String name){
            this.name = name;
        }

        public Builder outpuCount(int outputCount){
            this.outputCount = outputCount;
            return this;
        }

        public Builder outputList(List<Output> outputList){
            if(outputList == null)
                return this;

            this.outputList = outputList;
            return this;
        }

        public ConfigurationFVEP build(){
            return new ConfigurationFVEP(this.name, this.outputCount, this.outputList);
        }


    }
}
