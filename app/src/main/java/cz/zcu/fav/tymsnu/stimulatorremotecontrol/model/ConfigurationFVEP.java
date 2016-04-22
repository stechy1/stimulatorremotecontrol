package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.ArrayList;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Code;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Codes;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.DataConvertor;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtils;

public class ConfigurationFVEP extends AConfiguration<ConfigurationFVEP> {

    // region Variables
    // Kolekce všech výstupů
    public final List<Output> outputList;
    // endregion

    // region Constructors
    public ConfigurationFVEP(String name) {
        this(name, DEF_OUTPUT_COUNT, new ArrayList<Output>());
    }

    public ConfigurationFVEP(String name, int outputCount, List<Output> outputList) {
        super(name, outputCount);

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
    public ArrayList<Packet> getPackets() {

        ArrayList<Packet> packets = new ArrayList<>();

        Code actualDuration = Codes.OUTPUT0_DURATION; //TODO Pulse-up?
        Code actualPause = Codes.OUTPUT0_PAUSE; //TODO Pulse-down?
        Code actualFrequency = Codes.OUTPUT0_FREQ;
        Code actualMiddlePeriod = Codes.OUTPUT0_MIDDLE_PERIOD; //TODO Duty cycle ?
        Code actualBrightness = Codes.OUTPUT0_BRIGHTNESS;

        for(Output a : outputList){
            packets.add(new Packet(actualDuration, DataConvertor.milisecondsTo2B(a.puls.up)));
            packets.add(new Packet(actualPause, DataConvertor.milisecondsTo2B(a.puls.down)));
            packets.add(new Packet(actualFrequency, DataConvertor.intTo1B(a.frequency)));
            packets.add(new Packet(actualMiddlePeriod, DataConvertor.intTo1B(a.duty_cycle)));
            packets.add(new Packet(actualBrightness, DataConvertor.intTo1B(a.brightness)));

            actualDuration = actualDuration.getNext();
            actualPause = actualPause.getNext();
            actualFrequency = actualFrequency.getNext();
            actualMiddlePeriod = actualMiddlePeriod.getNext();
            actualBrightness = actualBrightness.getNext();
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
     */
    public void setOutputCount(int outputCount, OnValueChanged onValueChanged) {
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
        public Output() {
            this(new Puls(), DEF_FREQUENCY, DEF_DUTY_CYCLE, DEF_BRIGHTNESS);
        }

        public Output(Output source) {
            this(new Puls(source.puls), source.frequency, source.duty_cycle, source.brightness);
        }

        /**
         * Vytvoří nový výstup z parametrů
         * @param puls
         * @param frequency
         * @param duty_cycle Délka pulzu při nastavení frekvence
         * @param brightness Jas výstupu
         */
        public Output(Puls puls, int frequency, int duty_cycle, int brightness) {
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
            return RangeUtils.isInByteRange(val);
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
            return RangeUtils.isInByteRange(val);
        }
        // endregion

        // region Getters & Setters
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
        // endregion
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
            this.up = up;
            this.down = down;
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
    }
}
