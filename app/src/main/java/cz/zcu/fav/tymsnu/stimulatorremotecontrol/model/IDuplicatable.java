package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

public interface IDuplicatable<T> {

    /**
     * Vytvoří hlubokou kopii s novým názvem objektu
     * @param newName Nový název objektu
     * @return Hlubokou kopii objektu
     */
    T duplicate(String newName);

}
