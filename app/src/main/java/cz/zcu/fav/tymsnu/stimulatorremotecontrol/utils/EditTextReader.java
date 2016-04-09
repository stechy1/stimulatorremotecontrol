package cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils;


import android.widget.EditText;

public final class EditTextReader {
    private EditTextReader() {}

    /**
     * Přečte hodnotu z inputu
     * Pokud se nepodaří hodnotu naparsovat, tak vrátí 0
     * @param input Vstup
     * @return číslo
     */
    public static int readValue(EditText input) {return readValue(input, 0);}

    /**
     * Přečte hodnotu z inputu
     * Pokud se nepodaří hodnotu naparsovat, tak vrátí výchozí hodnotu
     * @param input Vstup
     * @param def Výchozí hodnota
     * @return číslo
     */
    public static int readValue(EditText input, int def) {
        String text = input.getText().toString();
        int toReturn = def;
        try {
            toReturn = Integer.parseInt(text);
        } catch (Exception ex) {
            input.setText(String.valueOf(def));
        }

        return toReturn;
    }
}
