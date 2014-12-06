package server;

public class Server extends Thread{

	private Server_Starter message_to; //the starter class, needed for gui
	 private int port; //port we are going to listen to
	
	public Server(int listen_port, Server_Starter to_send_message_to){
		message_to = to_send_message_to;
		port = listen_port ;
		
		this.start();
	}
	
	//alias for typing 
	private void s(String s2){
		message_to.send_message_to_window(s2);
	}
	
	
}
