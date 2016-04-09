package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import java.util.regex.Pattern;

public abstract class AItem<T extends AItem<T>> implements IDuplicatable<T> {

    // region Variables

    // Pattern pro název itemu:
    // - musí se jednat o neprázdné slovo
    // - délka slova je z intervalu <1, 32>
    // - slovo můžu obsahovat pouze [a-zA-Z] a "_"
    private static final Pattern pattern = Pattern.compile("[a-zA-Z_]{1,32}");

    // Název itemu
    protected String name;
    // Příznak, který určuje, zda-li byl item načtený, či nikoliv
    public boolean loaded;
    // Příznak, který určuje, zda-li byl item vybraný v manažeru
    public boolean selected;
    // Příznak, který určuje, zda-li došlo ke změnš interních hodnot itemu
    public boolean changed;
    // endregion

    // region Constructors
    public AItem(String name) {
        setName(name);
    }
    // endregion

    // region Private methods

    /**
     * Zkontroluje validitu zadaného názvu
     * @param name Název pro zvalidování
     * @return True, pokud je název validní, jinak false
     */
    private boolean isNameValid(String name) {
        // Kontrola na null
        if (name == null) return false;

        // Kontrola na rovnost názvu s již aktuálním názvem
        if (name.equals(this.name)) return false;

        // Kontrola podle patternu
        return pattern.matcher(name).matches();
    }
    // endregion

    // region Public methods
    // endregion

    // region Getters & Setters
    /**
     * Vrátí název itemu
     * @return Název itemu
     */
    public String getName() {
        return name;
    }

    /**
     * Nastaví nový název itemu
     * @param name Nový název itemu. Název nesmí být null a musí obsahovat pouze [a-zA-Z] a "_"
     */
    public void setName(String name) throws IllegalArgumentException {setName(name, null);}
    /**
     * Nastaví nový název itemu
     * @param name Nový název itemu. Název nesmí být prázdný ani null a musí obsahovat pouze [a-zA-Z] a "_"
     * @param onValueChanged Callback, který se zavolá po úspěšném nastavení nového názvu
     */
    public void setName(String name, OnValueChanged onValueChanged) throws IllegalArgumentException {
        if (!isNameValid(name))
            throw new IllegalArgumentException();

        this.name = name;

        if (onValueChanged != null)
            onValueChanged.changed();
    }
    // endregion

    public interface OnValueChanged {
        void changed();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AItem<?> aItem = (AItem<?>) o;

        return !(name != null ? !name.equals(aItem.name) : aItem.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
