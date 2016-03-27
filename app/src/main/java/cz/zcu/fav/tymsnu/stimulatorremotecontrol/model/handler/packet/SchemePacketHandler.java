package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.packet;

import java.util.ArrayList;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Code;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Codes;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.DataConvertor;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.IPacketable;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme.Output;


public class SchemePacketHandler implements IPacketable {

    private final Scheme scheme;

    public SchemePacketHandler(Scheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public ArrayList<Packet> getPackets() {

        ArrayList<Packet> packets = new ArrayList<>();

        packets.add(new Packet(Codes.EDGE, DataConvertor.intTo1B(scheme.getEdge().ordinal())));
        packets.add(new Packet(Codes.RANDOMNESS_ON, DataConvertor.intTo1B(scheme.getRandom().ordinal()))); //TODO jak je to s tím kódem náhodnosti?

        Code actualDURATION = Codes.OUTPUT0_DURATION;
        Code actualPAUSE = Codes.OUTPUT0_PAUSE;
        Code actualDISTRIBUTION = Codes.OUTPUT0_DISTRIBUTION;
        Code actualBRIGHTNESS = Codes.OUTPUT0_BRIGHTNESS;

        for(Output a : scheme.getOutputList()){
            packets.add(new Packet(actualDURATION, DataConvertor.milisecondsTo2B(a.puls.getUp())));
            packets.add(new Packet(actualPAUSE, DataConvertor.milisecondsTo2B(a.puls.getDown())));
            packets.add(new Packet(actualDISTRIBUTION, DataConvertor.intTo1B(a.distribution.getValue()))); //TODO u distribution parametru ještě neposíláme delay
            packets.add(new Packet(actualBRIGHTNESS, DataConvertor.intTo1B(a.getBrightness())));

            actualDURATION = actualDURATION.getNext();
            actualPAUSE = actualPAUSE.getNext();
            actualDISTRIBUTION = actualDISTRIBUTION.getNext();
            actualBRIGHTNESS = actualBRIGHTNESS.getNext();
        }

        return packets;
    }

}
