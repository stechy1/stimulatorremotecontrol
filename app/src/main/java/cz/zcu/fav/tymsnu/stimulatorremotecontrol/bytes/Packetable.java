package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;


import java.util.ArrayList;

/**
 * Interface, který dovoluje třídám jenž jej implementují umět sami sebe reprezentovat jako list packetů,
 * které můžeme následně poslat do stimulátoru.
 */
public interface Packetable {

    public ArrayList<Packet> getPackets();

}
