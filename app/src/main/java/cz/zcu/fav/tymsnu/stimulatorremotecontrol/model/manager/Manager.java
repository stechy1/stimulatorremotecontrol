package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager;


import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory.IFactory;

public class Manager<T extends AItem<T>> extends Observable {

    // region Variables
    private static final String TAG = "Manager";
    private static final String EXTENTION = ".json";

    // Reference na pracovní adresár
    private File workingDirectory;
    // Přiznak označující, zda-li byl pracoví adresář proskenován
    private boolean scanned = false;
    // Továrna pro itemy a handler pro čtení a zápis do souboru
    private IFactory<T> factory;

    // Reference na aktuálně zvolený item
    private T selectedItem;

    // Kolekce všech itemů
    public final List<T> itemList = new ArrayList<>();
    // endregion

    // region Constructors
    public Manager(IFactory<T> factory) {
        this.factory = factory;
    }
    // endregion

    // region Private methods

    /**
     * Načte všechny itemy do paměti
     * Načte pouze jejich názvy
     * @return Pole o dvou prvcích. První prvek obsahuje počet úspěšně načtených itemů,
     *         druhý prvek počet neúspěšně načtených itemů
     */
    private int[] loadItems()  {
        if (scanned || workingDirectory == null)
            return null;

        int[] result = new int[2];
        itemList.clear();

        String[] items = workingDirectory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.contains(EXTENTION);
            }
        });

        for (String name : items) {
            name = name.substring(0, name.indexOf(EXTENTION));

            try {
                itemList.add(factory.build(name));
                result[0]++;
            } catch (IllegalArgumentException ex) {
                result[1]++;
            }
        }

        scanned = true;

        return result;
    }

    /**
     * Načte item ze souboru
     * @param item Reference na načítaný item
     */
    private void loadItem(T item)  {
        if (item.loaded) return;

        try {
            File file = new File(workingDirectory, item.getName() + EXTENTION);
            InputStream in = new FileInputStream(file);
            factory.getReadWriteAcces().read(in, item);
            item.loaded = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // endregion

    // region Public methods
    /**
     * Vytvoří nový item
     * @param name Název item
     */
    public void create(String name) throws IllegalArgumentException {create(name, null);}
    /**
     * Vytvoří nový item
     * @param name Název itemu
     * @param callback Callback který se zavolá po úspěšném vytvoření itemu
     */
    public void create(String name, Callback callback) throws IllegalArgumentException {
        T item = factory.build(name);
        item.loaded = true;
        if (itemList.contains(item))
            throw new IllegalArgumentException();

        itemList.add(item);
        save(item);

        if (callback != null)
            callback.callback(item);
    }

    /**
     * Přidá item do seznamu načtených
     * @param item Item pro přidání
     * @throws IllegalArgumentException Pokud item již v kolekci existuje
     */
    public void add(T item) throws IllegalArgumentException {add(item, null);}
    /**
     * Přidá item do seznamu načtených
     * @param item Item pro přidání
     * @param callback Callback, který se zavolá po úspěšném přidání do kolekce
     * @throws IllegalArgumentException Pokud item již v kolekci existuje
     */
    public void add(T item, Callback callback) throws IllegalArgumentException {
        if (itemList.contains(item))
            throw new IllegalArgumentException();

        itemList.add(item);
        save(item);

        if (callback != null)
            callback.callback(item);
    }

    /**
     * Uloží item do souboru
     * @param item Referene na ukládaný item
     */
    public void save(T item) {save(item, null);}
    /**
     * Uloží item do souboru
     * @param item Referene na ukládaný item
     * @param callback Callback který se zavolá po úspěšném uložení itemu
     */
    public void save(T item, Callback callback) {
        try {
            String name = item.getName();
            if (!name.contains(EXTENTION))
                name += EXTENTION;

            File outFile = new File(workingDirectory, name);
            FileOutputStream out = new FileOutputStream(outFile);
            factory.getReadWriteAcces().write(out, item);
            item.changed = false;

            if (callback != null)
                callback.callback(item);
        } catch (IOException e) {
            Log.e(TAG, "Nepodarilo se zapsat do souboru");
        }
    }

    /**
     * Uloží všechny načtený itemy
     */
    public void saveAll() {saveAll(null);}
    /**
     * Uloží všechny načtený itemy
     * @param callbackAfterAll Callback, který se zavolá po uložení všech itemů
     */
    public void saveAll(Callback callbackAfterAll) {
        saveAll(callbackAfterAll, null);
    }
    /**
     * Uloží všechny načtený itemy
     * @param callbackAfterAll Callback, který se zavolá po uložení všech itemů
     * @param callbackAfterOne Callback, který se zavolá po uložení jednoho itemu
     */
    public void saveAll(Callback callbackAfterAll, Callback callbackAfterOne) {
        int counter = 0;
        for (T item : itemList) {
            if (item.loaded && item.changed) {
                save(item, callbackAfterOne);
                counter++;
            }
        }

        if (callbackAfterAll != null)
            callbackAfterAll.callback(counter);

        notifyValueChanged();
    }

    /**
     * Přejmenuje item
     * @param item Item pro přejmenování
     * @param newName Nové jméno itemu
     */
    public void rename(T item, String newName) throws IllegalArgumentException {
        rename(item, newName, null);
    }
    /**
     * Přejmenuje item
     * @param item Item pro přejmenování
     * @param newName Nové jméno itemu
     * @param callback Callback, který se zavolá po přejmenování itemu
     */
    public void rename(T item, String newName, Callback callback) throws IllegalArgumentException {
        String oldName = item.getName();
        String itemName = oldName + EXTENTION;
        String newFileName = newName + EXTENTION;

        File oldFile = new File(workingDirectory, itemName);
        File newFile = new File(workingDirectory, newFileName);

        item.setName(newName);

        if (oldFile.renameTo(newFile)) {
            if (callback != null)
                callback.callback(item);
            notifyValueChanged();
        } else {
            item.setName(oldName);
        }
    }

    /**
     * Smaže schéma
     * @param item Reference na schéma
     */
    public void delete(T item) {delete(item, null);}
    /**
     * Smaže schéma
     * @param item Reference na schéma
     * @param callback Callback který se zavolá po úspěšném smazání schématu
     */
    public void delete(T item, Callback callback) {
        Log.i(TAG, "Mazu schema: " + item);

        String name = item.getName();
        if (!name.contains(EXTENTION))
            name += EXTENTION;

        File file = new File(workingDirectory, name);
        if (!file.exists() || file.isDirectory() || !file.isFile()) {
            itemList.remove(item);
            if (item.equals(selectedItem)) {
                selectedItem = null;
                notifyValueChanged();
            }
            return;
        }

        if (file.delete()) {
            itemList.remove(item);
            if (item.equals(selectedItem)) {
                selectedItem = null;
                notifyValueChanged();
            }
            if (callback != null)
                callback.callback(item);
        }
    }

    /**
     * Smaže všechny itemy
     */
    public void deleteAll() {deleteAll(null);}
    /**
     * Smaže všechny itemy
     * @param callbackAfterAll Callback, který se zavolá po uložení všech itemů
     */
    public void deleteAll(Callback callbackAfterAll) {deleteAll(callbackAfterAll, null);}
    /**
     * Smaže všechny itemy
     * @param callbackAfterAll Callback, který se zavolá po smazání všech itemů
     * @param callbackAfterOne Callback, který se zavolá po smazání jednoho itemu
     */
    public void deleteAll(Callback callbackAfterAll, Callback callbackAfterOne) {
        int size = itemList.size();
        Iterator<T> it = itemList.iterator();
        for (T item = it.next(); it.hasNext();) {
            delete(item, callbackAfterOne);
            it.remove();
        }

        if (callbackAfterAll != null)
            callbackAfterAll.callback(size);
    }

    /**
     * Označí item jako vybraný
     * @param item Reference na vybraný item
     */
    public void select(T item) {select(item, null);}
    /**
     * Označí item jako vybraný
     * @param item Reference na vybraný item
     * @param callback Callback který se zavolá po úspěšné změně itemu
     */
    public void select(T item, Callback callback) {
        if (selectedItem != null && item.equals(selectedItem))
            return;

        if (this.selectedItem != null) {
            this.selectedItem.selected = false;
        }

        if (!item.loaded)
            loadItem(item);

        item.selected = true;
        this.selectedItem = item;

        if (callback != null)
            callback.callback(item);

        notifyValueChanged();
    }

    /**
     * Vytvoří hlubokou kopii objektu
     * @param source Zdrojový objekt
     * @param newName Nový název objektu
     * @return Hlubokou kopii
     */
    public T duplicate(T source, String newName) {
        return source.duplicate(newName);
    }

    /**
     * Obeznámí všechny přihlášené pozorovatele, že se změníly hodnoty ve vybraném schématu
     */
    public void notifyValueChanged() {
        setChanged();
        notifyObservers(selectedItem);
    }

    /**
     * Obeznámí všechny pozorovatele, že vybraný item změníl svůj vnitřní stav
     * Zavolá se pouze, pokud item neměl příznak "changed"
     */
    public void notifySelectedItemInternalChange() {
        if (selectedItem != null) {
            if (selectedItem.changed)
                return;

            selectedItem.changed = true;
            notifyValueChanged();
        }
    }

    // endregion

    // region Getters & Setters
    /**
     * Vrátí vybraný item
     * @return Vybraný item
     */
    public T getSelectedItem() {
        return selectedItem;
    }
    /**
     * Nastaví nový pracovní adresář
     * Nesmí se jednat o soubor
     * @param workingDirectory Reference na složku s pracovním adresářem
     * @param callback Callback, který se zavolá po úspěšném načtení itemů.
     *                 Do callbacku se vloží pole s počtem úspěšně a neúspěšně načtených itemů.
     *                 [0] - počet úspěšně načtených itemů
     *                 [1] - počet neúspěšně načtených itemů
     */
    public void setWorkingDirectory(File workingDirectory, Callback callback) {
        if (workingDirectory == null) return;
        if (workingDirectory.equals(this.workingDirectory)) return;

        this.workingDirectory = workingDirectory;

        int[] res = loadItems();
        if (res == null) return;

        Log.i(TAG, "Items loaded - Succesfully: " + res[0] + "; Unsuccesfully: " + res[1]);

        if (callback != null)
            callback.callback(res);
    }
    // endregion

    /**
     * Rozhraní pro zpětné volání
     */
    public interface Callback {
        void callback(Object object);
    }
}
