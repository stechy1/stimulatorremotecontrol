package cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils;

public final class RangeUtils {

    /**
     * Privátní konstruktor
     * Nepotřeujeme žádnou instanci třídy
     */
    private RangeUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Zjistí, zda-li je číslo v intervalu <min, max>
     * @param val Kontrolovaná hodnota
     * @param min Minimální hodnota
     * @param max Maximální hodnota
     * @return True, pokud je číslo v rozsahu, jinak false
     */
    public static boolean isInRange(int val, int min, int max) {
        return val >= min && val <= max;
    }

    /**
     * Zjistí, zda-li je hodnota v intervalu 0 - 255
     * @param val Kontrolovaná hodnota
     * @return True, pokud hodnota je v intervalu, jinak false
     */
    public static boolean isInByteRange(int val) {
        return isInRange(val, 0, 255);
    }

    /**
     * Zjistí, zda-li je hodnota v intervalu 0 - 100
     * @param val Kontrolovaná hodnota
     * @return True, pokud hodnota je v intervalu, jinak false
     */
    public static boolean isInPercentRange(int val) {
        return isInRange(val, 0, 100);
    }
}
