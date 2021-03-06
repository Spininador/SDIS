package fileManagement;

import java.util.ArrayList;

public class ChunksSending {
	public static class ChunkSent{
		FileChunk c;
		int nConfirmations;
		public ChunkSent(FileChunk c){
			this.c = c;
			this.nConfirmations = 0;
		}
	}
	public static ArrayList<ChunkSent> chunks = new ArrayList<ChunkSent>();

	public synchronized static void add(FileChunk c){
		ChunkSent sent = new ChunkSent(c);
		chunks.add(sent);
	}

	public static void remove(FileChunk c){
		for(int i = 0;i < chunks.size();i++){
			if(chunks.get(i).c == c){
				chunks.remove(i);
			}
		}
	}

	public synchronized static Boolean incrementResponses(String fileid, int chunkNo){
		for(int i = 0;i < chunks.size();i++){
			if(chunks.get(i).c.fileId.equals(fileid) && chunks.get(i).c.chunkNo == chunkNo){
				chunks.get(i).nConfirmations++;
				return true;
			}
		}
		return false;
	}

	public synchronized static Boolean hasEnoughResponses(FileChunk c){
		for(int i = 0;i < chunks.size();i++){
			if(chunks.get(i).c.equals(c)){
				boolean ret = false;
				if(chunks.get(i).nConfirmations >= c.repDeg)
					ret = true;
				
				return ret;
			}
		}
		System.out.println("Didnt find");
		return false;
	}
}
