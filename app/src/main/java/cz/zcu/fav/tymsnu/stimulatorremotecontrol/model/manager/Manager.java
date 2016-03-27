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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class Manager<T extends AItem> extends Observable {

    // region Variables
    private static final String TAG = "Manager";
    private static final String EXTENTION = ".json";

    // Reference na pracovní adresár
    private File workingDirectory;
    // Přiznak označující, zda-li byl pracoví adresář proskenován
    private boolean scanned = false;

    private IFactory<T> factory;

    // Reference na aktuálně zvolený item
    private T selectedItem;

    // Kolekce všech itemů
    public final List<T> itemList = new ArrayList<>();
    // endregion


    public Manager(IFactory<T> factory) {
        this.factory = factory;
    }

    // region Private methods
    /**
     * Načte všechny itemy do paměti
     * Načte pouze jejich názvy
     */
    private void loadItems()  {
        if (scanned || workingDirectory == null)
            return;

        itemList.clear();

        String[] items = workingDirectory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.contains(EXTENTION);
            }
        });

        for (String name : items) {
            name = name.substring(0, name.indexOf(EXTENTION));
            itemList.add(factory.build(name));
        }

        scanned = true;
    }

    /**
     * Načte item ze souboru
     * @param item Reference na načítaný item
     */
    private void loadItem(AItem item)  {
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
        itemList.add(item);
        save(item);

        if (callback != null)
            callback.callack(item);
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
            outFile.createNewFile();
            FileOutputStream out = new FileOutputStream(outFile);
            IReadWrite readWrite = factory.getReadWriteAcces();
            readWrite.write(out, item);
            item.changed = false;

            if (callback != null)
                callback.callack(item);
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
            callbackAfterAll.callack(counter);

        notifyValueChanged();
    }

    /**
     * Přejmenuje item
     * @param item Item pro přejmenování
     * @param newName Nové jméno itemu
     */
    public void rename(T item, String newName) {
        rename(item, newName, null);
    }
    /**
     * Přejmenuje item
     * @param item Item pro přejmenování
     * @param newName Nové jméno itemu
     * @param callback Callback, který se zavolá po přejmenování itemu
     */
    public void rename(T item, String newName, Callback callback) {
        String itemName = item.getName();
        if (!itemName.contains(EXTENTION))
            itemName += EXTENTION;
        String newFileName = newName;
        if (!newFileName.contains(EXTENTION))
            newFileName += EXTENTION;

        File oldFile = new File(workingDirectory, itemName);
        File newFile = new File(workingDirectory, newFileName);
        boolean success = oldFile.renameTo(newFile);
        if (success) {
            item.setName(newName);
            if (callback != null)
                callback.callack(item);
            notifyValueChanged();
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
                callback.callack(item);
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
            callbackAfterAll.callack(size);
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
            callback.callack(item);

        notifyValueChanged();
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
    public T getSelectedItem() {
        return selectedItem;
    }
    /**
     * Nastaví nový pracovní adresář
     * Nesmí se jednat o soubor
     * @param workingDirectory Reference na složku s pracovním adresářem
     */
    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;

        loadItems();
    }
    // endregion


    /**
     * Rozhraní pro zpětné volání
     */
    public interface Callback {
        void callack(Object object);
    }
}
