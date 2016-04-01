package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.packet;

import java.util.ArrayList;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.IPacketable;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;

public class CVEPPacketHandler implements IPacketable {

    private final ConfigurationCVEP configuration;

    public CVEPPacketHandler(ConfigurationCVEP configuration) {
        this.configuration = configuration;
    }

    @Override
    public ArrayList<Packet> getPackets() {
        return null;
    }
}
