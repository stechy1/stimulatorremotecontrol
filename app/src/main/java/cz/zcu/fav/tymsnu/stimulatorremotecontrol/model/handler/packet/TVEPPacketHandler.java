package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.packet;

import java.util.ArrayList;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.IPacketable;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;


public class TVEPPacketHandler implements IPacketable {

    private final ConfigurationTVEP configuration;

    public TVEPPacketHandler(ConfigurationTVEP configuration) {
        this.configuration = configuration;
    }

    @Override
    public ArrayList<Packet> getPackets() {
        return null;
    }
}
