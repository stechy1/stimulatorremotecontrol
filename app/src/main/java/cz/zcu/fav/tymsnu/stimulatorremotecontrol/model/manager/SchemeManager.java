package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager;


import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Output;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;

public final class SchemeManager extends Observable {

    // region Variables
    private static final String TAG = "SchemeManager";
    private static SchemeManager INSTANCE;

    // Reference na pracovní adresár
    private File workingDirectory;
    // Přiznak označující, zda-li byl pracoví adresář proskenován
    private boolean loaded = false;

    // Kolekce všech schémat
    private final List<Scheme> schemeList = new ArrayList<>();
    // Reference na aktuálně zvolené schéma
    private Scheme selectedScheme;
    // endregion

    public static SchemeManager getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new SchemeManager();

        return INSTANCE;
    }

    // region Constructors
    /**
     * Konstruktor scheme manageru
     */
    private SchemeManager() {

    }
    // endregion

    // region Private methods

    /**
     * Načte všechny schémata do paměti
     * Načte pouze jejich názvy
     */
    private void loadSchemes() {
        if (loaded)
            return;

        List<Output> list1 = new ArrayList<>();
        list1.add(new Output("Out1", new Output.Puls(1, 2), new Output.Distribution(6, 8), 9));
        list1.add(new Output("Out2", new Output.Puls(3, 4), new Output.Distribution(3, 5), 0));
        list1.add(new Output("Out3", new Output.Puls(5, 6), new Output.Distribution(1, 7), 3));

        schemeList.add(new Scheme("Scheme1", 3, Scheme.Edge.FALLING, Scheme.Random.OFF, list1));
        schemeList.add(new Scheme("Scheme2", 3, Scheme.Edge.LEADING, Scheme.Random.SHORT,
                Arrays.asList(
                        new Output("Out1", new Output.Puls(9, 8), new Output.Distribution(4, 3), 5),
                        new Output("Out2", new Output.Puls(2, 7), new Output.Distribution(8, 2), 2),
                        new Output("Out3", new Output.Puls(1, 5), new Output.Distribution(9, 4), 7)
                )));
        schemeList.add(new Scheme("Scheme3", 3, Scheme.Edge.FALLING, Scheme.Random.SHORT_LONG,
                Arrays.asList(
                        new Output("Out1", new Output.Puls(5, 4), new Output.Distribution(7, 5), 2),
                        new Output("Out2", new Output.Puls(9, 3), new Output.Distribution(9, 3), 6),
                        new Output("Out3", new Output.Puls(2, 6), new Output.Distribution(5, 6), 4)
                )));

        loaded = true;
        /*if (workingDirectory == null)
            return;

        schemeList.clear();

        File[] schemes = workingDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains(".xml");
            }
        });

        for (File file : schemes) {
            String name = file.getName();
            schemeList.add(new Scheme(name));
        }*/
    }

    /**
     * Načte celé schéma ze souboru
     * @param scheme Reference na schema, které má být načteno
     */
    private void loadScheme(Scheme scheme) {
        if (scheme.loaded)
            return;

        scheme.loaded = true;
        Log.i(TAG, "Scheme: " + scheme + " is loaded");
    }
    // endregion

    // region Public methods

    /**
     * Vytvoří nové schéma
     * @param name Název schématu
     */
    public void create(String name) {create(name, null);}

    /**
     * Vytvoří nové schéma
     * @param name Název schématu
     * @param callback Callback který se zavolá po úspěšném vytvoření schématu
     */
    public void create(String name, Callback callback) {
        Scheme scheme = new Scheme(name);
        scheme.loaded = true;
        schemeList.add(scheme);

        if (callback != null)
            callback.callack();
    }

    public void add(Scheme scheme) {add(scheme, null);}
    public void add(Scheme scheme, Callback callback) {


        if (callback != null)
            callback.callack();
    }

    /**
     * Uloží schéma do souboru
     * @param scheme Referene na schéma
     */
    public void save(Scheme scheme) {save(scheme, null);}
    /**
     * Uloží schéma do souboru
     * @param scheme Referene na schéma
     * @param callback Callback který se zavolá po úspěšném uložení schématu
     */
    public void save(Scheme scheme, Callback callback) {


        if (callback != null)
            callback.callack();
    }

    /**
     * Smaže schéma
     * @param scheme Reference na schéma
     */
    public void delete(Scheme scheme) {delete(scheme, null);}
    /**
     * Smaže schéma
     * @param scheme Reference na schéma
     * @param callback Callback který se zavolá po úspěšném smazání schématu
     */
    public void delete(Scheme scheme, Callback callback) {
        Log.i(TAG, "Mazu schema: " + scheme);


        if (callback != null)
            callback.callack();
    }

    /**
     * Označí schéma jako vybrané
     * @param actScheme Reference na schéma
     */
    public void select(Scheme actScheme) {select(actScheme, null);}
    /**
     * Označí schéma jako vybrané
     * @param actScheme Reference na schéma
     * @param callback Callback který se zavolá po úspěšné změně schématu
     */
    public void select(Scheme actScheme, Callback callback) {
        if (selectedScheme != null && selectedScheme.equals(actScheme))
            return;

        this.selectedScheme = actScheme;
        if (!actScheme.loaded)
            loadScheme(actScheme);

        if (callback != null)
            callback.callack();

        setChanged();
        notifyObservers(actScheme);
    }

    /**
     * Oznámí všechny přihlášené pozorovatele, že se změníly hodnoty ve vybraném schématu
     */
    public void notifyValueChanged() {
        setChanged();
        notifyObservers(selectedScheme);
    }
    // endregion

    // region Getters & Setters
    public Scheme getSelectedScheme() {
        return selectedScheme;
    }

    public List<Scheme> getSchemeList() {
        return schemeList;
    }

    /**
     * Nastaví nový pracovní adresář
     * Nesmí se jednat o soubor
     * @param workingDirectory Reference na složku s pracovním adresářem
     */
    public void setWorkingDirectory(File workingDirectory) {
        if (workingDirectory.isFile() || !workingDirectory.isDirectory())
            return;

        this.workingDirectory = workingDirectory;
        loaded = false;
        loadSchemes();
    }

    // endregion

    /**
     * Rozhraní pro zpětné volání
     */
    public interface Callback {
        void callack();
    }

    /**
     * Značkovací rozhraní pro klasický observer
     */
    public interface OnSchemeChangeListener extends Observer {}
}
