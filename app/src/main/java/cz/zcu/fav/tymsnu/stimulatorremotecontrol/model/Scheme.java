package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.ArrayList;
import java.util.List;

public final class Scheme {

    // region Variables
    // Výchozí počet výstupů (stará verze 4)
    public static final int MIN_OUTPUT_COUNT = 1;
    public static final int MAX_OUTPUT_COUNT = 8;


     // Indikátor, zda-li je schéma načteno
    public boolean loaded = false;
    // TODO implementovat reakci na změnu hodnot ve schématu (indikátor na screenu1)
    public boolean changed = false;

    // Název schématu
    private String name;
    // Počet výstupů
    private int outputCount;
    // Nastavení hrany (náběžná/sestupná)
    private Edge edge;
    // Nastavení náhodnosti (žádná/krátká/dlouhá/krátká i dlouhá)
    private Random random;
    // Kolekce udržující výstupy
    private final List<Output> outputList;
    // endregion

    // region Constructors

    /**
     * Konstruktor schématu
     * Vytvoří nové schéma s výchozími hodnotami
     * @param name Název schématu
     */
    public Scheme(String name) {

        this(name, MIN_OUTPUT_COUNT, Edge.FALLING, Random.OFF, new ArrayList<Output>());

        for (int i = 0; i < MIN_OUTPUT_COUNT; i++) {
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
    public Scheme(String name, int outputCount, Edge edge, Random random, List<Output> outputList) {
        if (name.contains("."))
            name = name.substring(0, name.lastIndexOf("."));
        this.name = name;
        this.outputCount = outputCount;
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
    /**
     * Metoda pro získání packetů, které reprezentují nastavení celého schématu a lze je postupně
     * odeslat po sériové lince do stimulátoru
     * @return list packetů
     */

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

    public void setOutputCount(int outputCount) throws IllegalArgumentException {
        if (outputCount < MIN_OUTPUT_COUNT || outputCount > MAX_OUTPUT_COUNT)
            throw new IllegalArgumentException();

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
