package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.packet;


import java.util.ArrayList;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Code;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Codes;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.DataConvertor;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.IPacketable;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP.Output;

public class FVEPPacketHandler implements IPacketable{

    private final ConfigurationFVEP configuration;

    public FVEPPacketHandler(ConfigurationFVEP configuration) {
        this.configuration = configuration;
    }

    @Override
    public ArrayList<Packet> getPackets() {

        ArrayList<Packet> packets = new ArrayList<>();

        Code actualDuration = Codes.OUTPUT0_DURATION; //TODO Pulse-up?
        Code actualPause = Codes.OUTPUT0_PAUSE; //TODO Pulse-down?
        Code actualFrequency = Codes.OUTPUT0_FREQ;
        Code actualMiddlePeriod = Codes.OUTPUT0_MIDDLE_PERIOD; //TODO Duty cycle ?
        Code actualBrightness = Codes.OUTPUT0_BRIGHTNESS;

        for(Output a : configuration.outputList){
            packets.add(new Packet(actualDuration, DataConvertor.milisecondsTo2B(a.puls.getUp())));
            packets.add(new Packet(actualPause, DataConvertor.milisecondsTo2B(a.puls.getDown())));
            packets.add(new Packet(actualFrequency, DataConvertor.intTo1B(a.getFrequency())));
            packets.add(new Packet(actualMiddlePeriod, DataConvertor.intTo1B(a.getDutyCycle())));
            packets.add(new Packet(actualBrightness, DataConvertor.intTo1B(a.getBrightness())));

            actualDuration = actualDuration.getNext();
            actualPause = actualPause.getNext();
            actualFrequency = actualFrequency.getNext();
            actualMiddlePeriod = actualMiddlePeriod.getNext();
            actualBrightness = actualBrightness.getNext();
        }

        return packets;
    }
}
