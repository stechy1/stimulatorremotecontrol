package cz.zcu.fav.tymsnu.stimulatorremotecontrol;


/**
 * Definuje rozhraní pro ošetření stisku zpětného tlačítka
 */
public interface IBackPress {
    void onBackButtonPressed(IViewSwitcher IViewSwitcher);
}
