package subprotocols;

import services.Peer;
import utilities.RandomDelay;
import services.Message;

import java.io.IOException;
import java.net.DatagramPacket;

import java.net.MulticastSocket;

public class RestoreSubprotocol extends Thread {

    public Peer peer;
    public String fileid;
    public int chunkNo;

    public RestoreSubprotocol(Peer peer, String fileid, int chunkNo){
        this.peer = peer;
        this.fileid = fileid;
        this.chunkNo = chunkNo;
    }

    public void run(){
    	
    	try{
			Thread.sleep(RandomDelay.getRandomDelay());
		}catch(Exception e){

		}

        Message m = new Message();
        byte[] msg = m.getchunkMsg( this.peer.peerNumber,
                                    this.fileid,
                                    this.chunkNo );

        DatagramPacket packet = new DatagramPacket( msg,
                                                    msg.length,
                                                    this.peer.mcastMC,
                                                    this.peer.portMC);

        System.out.println("Sending GetChunk: " + this.chunkNo);

        try {
        	this.peer.MC.writeToMulticast(packet);
		} catch (Exception e){
			System.err.println("Errror sending getchunk message");
			e.printStackTrace();
		}


        // Wait for CHUNK messages

    }

}
