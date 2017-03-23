import java.net.*;

public class Channel extends Thread {
	protected Peer peer;
	protected MulticastSocket msocket;
	protected byte[] buffer;
	protected DatagramPacket packet;
	
	public Channel(Peer p){
		super("Peer" + p.peerNumber);
		
		this.peer = p;
		
		this.msocket = null;
		
		this.buffer = new byte[Constants.MAX_BUFFER_SIZE];
		
		this.packet = new DatagramPacket(this.buffer,Constants.MAX_BUFFER_SIZE);
	}
	
}
