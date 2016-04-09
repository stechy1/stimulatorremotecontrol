package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public interface IFactory<T extends AItem<T>> {

    /**
     * Sestavý objekt
     * @param name Název
     * @return Sestavený objekt
     */
    T build(String name);

    IReadWrite<T> getReadWriteAcces();
}
