package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


public final class Output {

    // region Variables
    private final String name;
    public final Puls puls;
    public final Distribution distribution;
    private int brightness;
    // endregion

    // region Constructors
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

    // endregion

    // region Getters & Setters

    public String getName() {
        return name;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    // endregion

    // region Classes
    public static final class Puls {
        private int up;
        private int down;

        public Puls() {
            this(0, 0);
        }

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

        public Distribution() {
            this(0, 0);
        }

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
