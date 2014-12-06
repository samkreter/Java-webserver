package server;

import java.io.*;
import java.net.*;

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
	
	//overridden from the Thread class
	public void run(){
		//inside thread separate formt he gui
		
		ServerSocket serverSocket = null;
		
		s("The simple httpserver v. 0000000000\nCoded by Sam Kreter");
		
		try{
			//send message to gui
			s("Trying to bind to port "+Integer.toString(port)+"....");
			//make and bind socket to the port
			serverSocket = new ServerSocket(port);
		}catch(Exception e){
			s("\nFatel Error: "+e.getMessage());
			return;
		}
		s("OK!\n");
		
		//infinite loop to wait for connections, process requests and send responses
		while(true){
			s("\nReady, Waiting for requests....");
			
			try{
				//waits until someone connects to the port being listened on  
				Socket connectionSocket = serverSocket.accept();
				//get clients ipAdress
				InetAddress client = connectionSocket.getInetAddress(); 
				s(client.getHostName()+" connect to server\n");
				
				//Read the http request form the client through the sockets
				BufferedReader input = new BufferedReader(new InputStreamReader(
						connectionSocket.getInputStream()));
				//prepare outstream to the client, sending header and request file
				DataOutputStream output = new DataOutputStream(connectionSocket.getOutputStream());
				
				//actully handling the request
				http_handler(input,output);
				
			}catch(Exception e){
				s("\nError:" + e.getMessage());
			}
		}//infite loop to catch
		
		
	}
	
	//implementation of simple http
	private void http_handler(BufferedReader input, DataOutputStream output){
		int method = 0; //1 get, 2 head, 0 not upported
		String http = new String();
		String path = new String(); //what path 
		String file = new String(); //what file
		String user_agent = new String();//what user agent 
		
		try{
			String temp = input.readLine(); //read from the input 
			String temp2 = new String(temp);
			temp.toUpperCase(); //convert to upper case
			if(temp.startsWith("GET")){
				method = 1;
			}
			if(temp.startsWith("HEAD")){
				method = 2;
			}
			if(method == 0){
				try{
					output.writeBytes(construct_http_header(501,0));
					output.close();
					return;
				}catch(Exception e3){
					s("ERROR: "+e3.getMessage());
				}
			}
			
			int start = 0;
			int end = 0;
			for(int a=0;a<temp2.length();a++){
				if(temp2.charAt(a) == ' ' && start != 0){
					end = a;
					break;
				}
				if(temp2.charAt(a) == ' ' && start == 0){
					start = a;
				}
			}
			
			path = temp2.substring(start+2,end); //fill the path 
			
		}catch(Exception e){
			s("ERROR: "+e.getMessage());
		}
		
		//show the request file 
		s("\nClient requested:" + new File(path).getAbsolutePath() + "\n");
		FileInputStream requestedFile = null;
		
		try{
			//this is unsecured --- I really need to fix this later---- but this will work for now
			requestedFile = new FileInputStream(path);
			
		}catch(Exception e){
			try{
				//if file can't be opened send a 404
				output.writeBytes(construct_http_header(404,0));
				//close stream
				output.close();
			}catch(Exception e2){}
			;
			s("ERROR "+e.getMessage());
		}
		
		//good senerio ===========================
		try{
			int type_is = 0;
			//get file ending to construct the correct type
			if(path.endsWith(".zip")){
				type_is = 3;
			}
			if(path.endsWith(".jpg") || path.endsWith(".jpeg")){
				type_is = 1;
			}
			if(path.endsWith(".gif")){
				type_is = 2;
			}
			
			//create header with everything is just lovely on this side man
			output.writeByte(construct_http_header(202,0));
			
		}catch(Exception e){
			
		}
		
	}
	
	
}
