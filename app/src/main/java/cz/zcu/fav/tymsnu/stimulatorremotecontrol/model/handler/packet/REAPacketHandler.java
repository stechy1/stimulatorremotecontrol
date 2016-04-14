package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.packet;

import java.util.ArrayList;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.IPacketable;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;

public class REAPacketHandler implements IPacketable {

    private final ConfigurationREA configuration;

    public REAPacketHandler(ConfigurationREA configuration) {
        this.configuration = configuration;
    }

    @Override
    public ArrayList<Packet> getPackets() {
        return null;
    }
}