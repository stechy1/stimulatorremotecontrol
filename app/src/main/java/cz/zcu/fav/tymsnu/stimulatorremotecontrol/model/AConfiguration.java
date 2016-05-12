package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.IPacketable;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtils;

// ItemViewModel
public abstract class AConfiguration<T extends AConfiguration<T>>
        extends BaseObservable
        implements IDuplicatable<T>, IPacketable {

    // region Variables

    // Pattern pro název itemu:
    // - musí se jednat o neprázdné slovo
    // - délka slova je z intervalu <1, 32>
    // - slovo může obsahovat pouze [a-zA-Z0-9] a "_"
    //   přičemž číslo nesmí být na první pozici
    private static final Pattern pattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]{0,31}");

    // Minimání počet výstupů
    public static final int MIN_OUTPUT_COUNT = 1;
    // Výchozí počet výstupů
    public static final int DEF_OUTPUT_COUNT = 4;
    // Maximální počet výstupů
    public static final int MAX_OUTPUT_COUNT = 8;

    // Název itemu
    @Bindable
    protected String name;
    // Počet výstupů
    protected int outputCount;
    // Příznak, který určuje, zda-li byl item načtený, či nikoliv
    @Bindable
    public boolean loaded;
    // Příznak, který určuje, zda-li byl item vybraný v manažeru
    @Bindable
    public boolean selected;
    // Příznak, který určuje, zda-li došlo ke změnš interních hodnot itemu
    @Bindable
    public boolean changed;
    // Přiznak, který určuje, zda-li je konfigurace poškozená - nelze s ní pracovat
    @Bindable
    public boolean corrupted;
    // endregion

    // region Constructors
    /**
     * Konstruktor základní konfigurace s výchozím počtem výstupů
     * @param name Název konfigurace
     */
    public AConfiguration(String name) {this(name, DEF_OUTPUT_COUNT);}
    /**
     * Konstruktor základní konfigurace s nastavitelným počtem výstupů
     * @param name Název konfigurace
     * @param outputCount Počet výstupů
     */
    public AConfiguration(String name, int outputCount) throws IllegalArgumentException {
        setName(name);

        if (!isOutputCountInRange(outputCount))
            throw new IllegalArgumentException();

        this.outputCount = outputCount;
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

    /**
     * Zjistí, zda-li je hodnota v povoleném intervalu
     * @param value Testovaná hodnota
     * @return True, pokud je hodnota v povoleném intervalu, jinak false
     */
    public boolean isOutputCountInRange(int value) {
        return RangeUtils.isInRange(value, MIN_OUTPUT_COUNT, MAX_OUTPUT_COUNT);
    }

    @Bindable
    public int getStatusIcon() {
        if (corrupted)
            return R.drawable.corrupted_file;

        if (selected)
            return R.drawable.checkbox_marked_outline;

        return R.drawable.checkbox_blank_outline;
    }

    @BindingAdapter("bind:imageResource")
    public static void loadImage(ImageView view, int resourceID) {

        final Drawable drawable = ContextCompat.getDrawable(view.getContext(), resourceID);
        drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        view.setImageDrawable(drawable);
    }

    @Override
    public T duplicate(String newName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArrayList<Packet> getPackets() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AConfiguration<?> aConfiguration = (AConfiguration<?>) o;

        return !(name != null ? !name.equals(aConfiguration.name) : aConfiguration.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
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

    /**
     * Vrátí počet výstupů
     * @return Počet výstupů
     */
    public int getOutputCount() {
        return outputCount;
    }

    public String getStringOutputCount() {
        return String.valueOf(outputCount);
    }

    /**
     * Nastaví počet výstupů
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param outputCount Počet výstupů
     */
    public void setOutputCount(int outputCount) throws IllegalArgumentException {setOutputCount(outputCount, null);}
    /**
     * Nastaví počet výstupů
     * Pokud se do parametru vloží hodnota, která je stejná jako aktuální, nic se nestane
     * @param outputCount Počet výstupů
     * @param onValueChanged Callback, který se zavolá po nastavení počtu výstupů
     */
    public void setOutputCount(int outputCount, OnValueChanged onValueChanged) throws IllegalArgumentException {
        if (!isOutputCountInRange(outputCount))
            throw new IllegalArgumentException();

        if (this.outputCount == outputCount)
            return;

        this.outputCount = outputCount;

        if (onValueChanged != null)
            onValueChanged.changed();
    }
    // endregion

    public interface OnValueChanged {
        void changed();
    }
}
