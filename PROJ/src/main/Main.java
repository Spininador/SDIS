package main;

import services.Peer;

public class Main {
	public static void main(String[] args){

		if(args.length != 9){
			System.err.println("Usage: Protocol-Version Server-ID Remote-name MC-IP MC-Port MDB-IP MDB-Port MDR-IP MDR-Port");
			System.exit(1);
		}

		Peer peer = new Peer(args);

		peer.start();
	}
}
