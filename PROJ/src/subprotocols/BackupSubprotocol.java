package subprotocols;

import services.Peer;
import services.Message;
import fileManagement.ChunksSending;
import fileManagement.FileChunk;
import utilities.Constants;
import utilities.RandomDelay;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class BackupSubprotocol extends Thread{

    public Peer peer;
    public FileChunk chunk;
    public int repDeg;

    public BackupSubprotocol (Peer peer, FileChunk chunk, int repDeg){
    	super(chunk.fileId+":"+chunk.chunkNo);

        this.peer = peer;

        this.chunk = chunk;
        this.repDeg = repDeg;

    }

    public void run(){
    	ChunksSending.add(chunk);
        int tries = Constants.MAX_TRIES;
       
        while(tries > Constants.ZERO_TRIES){
        	
        	

            Message m = new Message();

            byte[] msg = m.putchunkMsg( this.peer.peerNumber,
                                        this.chunk.fileId,
                                        this.chunk.chunkNo,
                                        this.repDeg,
                                        this.chunk.data);

            // write putchunk
            DatagramPacket packet = new DatagramPacket( msg,
            msg.length,
            this.peer.mcastMDB,
            this.peer.portMDB);
            try{
            	Thread.sleep(RandomDelay.getRandomDelay()/2);
            }catch(Exception e){
            	System.err.println("Exception: " + e.toString());
                e.printStackTrace();
            }
            
            try {
            	System.out.println("Sent chunk #" + this.chunk.chunkNo);
                this.peer.MDB.writeToMulticast(packet);
            } catch (Exception e){
                System.err.println("BackupSubprotocol Exception. Couldn't send packet. " + e.toString());
                e.printStackTrace();
            }

            
            try{
            	Thread.sleep(Constants.ONE_SECOND);
            }catch(Exception e){
            	System.err.println("Exception: " + e.toString());
                e.printStackTrace();
            }

            if(ChunksSending.hasEnoughResponses(chunk)){
            	System.out.println("Successfully stored chunk #" + this.chunk.chunkNo);
            	break;
            }else{
            	
            	tries--;
            	continue;
            }
        }
        ChunksSending.remove(chunk);
        
    }
}
