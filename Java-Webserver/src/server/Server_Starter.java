package server;

import javax.swing.*;


public class Server_Starter extends JFrame{
	
	//setting up varibles 
	JPanel jpanel1 = new JPanel();
	JScrollPane jscrollPane1 = new JScrollPane();
	JTextArea jTextArea2 = new JTextArea();
	static Integer listen_port = null;
	
	
	//constructor
	public Server_Starter(){
		try{
			jBInit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//start the server on the passed in port with defalut 80
		try{
			listen_port = new Integer(args[0]);
		}catch(Exception e){
			listen_port = new Integer(80);
		}
		
		
		//create instance of the server
		Server_Starter server_start = new Server_Starter();
	}
	
	//user interface for the server

}
