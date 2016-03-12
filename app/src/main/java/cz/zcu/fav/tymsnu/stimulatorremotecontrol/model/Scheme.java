package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.ArrayList;
import java.util.List;

public final class Scheme {

    // region Variables
    public static final int DEF_OUTPUT_COUNT = 0;

    public boolean loaded = false;
    // TODO implementovat reakci na změnu hodnot ve schématu (indikátor na screenu1)
    public boolean changed = false;

    private String name;
    private int outputCount;
    private Edge edge;
    private Random random;

    private final List<Output> outputList;
    // endregion

    // region Constructors

    public Scheme(String name) {
        this(name, DEF_OUTPUT_COUNT, Edge.FALLING, Random.OFF, new ArrayList<Output>());
    }

    public Scheme(String name, int outputCount, Edge edge, Random random, List<Output> outputList) {
        this.name = name;
        this.outputCount = outputCount;
        this.edge = edge;
        this.random = random;
        this.outputList = outputList;
    }
    // endregion

    // region Private methods
    private void rearangeOutputs() {
        final int listCount = outputList.size();
        if (outputCount > listCount) {
            int delta = outputCount - listCount;
            for (int i = 0; i < delta; i++) {
                outputList.add(new Output("Output" + i));
            }
        } else {
            for (int i = listCount; i > outputCount; i--) {
                outputList.remove(i);
            }
        }
    }
    // endregion

    // region Public methods

    // endregion

    // region Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(int outputCount) {
        this.outputCount = outputCount;

        if (outputList.size() != outputCount)
            rearangeOutputs();
    }

    public List<Output> getOutputList() {
        return outputList;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    // endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scheme scheme = (Scheme) o;

        if (outputCount != scheme.outputCount) return false;
        if (name != null ? !name.equals(scheme.name) : scheme.name != null) return false;
        if (edge != scheme.edge) return false;
        return random == scheme.random;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + outputCount;
        result = 31 * result + (edge != null ? edge.hashCode() : 0);
        result = 31 * result + (random != null ? random.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

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
}
