package ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Formatter;
import java.util.Scanner;

public class ftpclient {
	public static void main(String[] args) {
		try {
			InetAddress ip = InetAddress.getByName("localhost");

			Socket clientsocket = new Socket (ip,3000);
			DataInputStream input=new DataInputStream(clientsocket.getInputStream());
			DataOutputStream output=new DataOutputStream(clientsocket.getOutputStream());
			String isconnected =input.readUTF();
			
			System.out.println("Server :"+isconnected);
		    //while(true) {
			String ask=input.readUTF();
			System.out.println("Server: "+ask);
			Scanner scanner=new Scanner(System.in);
			String request = scanner.nextLine();
         	output.writeUTF(request);
         	String reply=input.readUTF();
			System.out.println("Server: "+reply);
			//for pass
			ask=input.readUTF();
			System.out.println("Server: "+ask);
			request = scanner.nextLine();
         	output.writeUTF(request);
         	 reply=input.readUTF();
			System.out.println("Server: "+reply);
			while(true) {
			// for the directories
			output.writeUTF("show me my directories");
			reply=input.readUTF();
			System.out.println("Server: "+reply);
			
			// for entering the file client wants to open 
			reply=input.readUTF();
			System.out.println("Server: "+reply);
		    request = scanner.nextLine();
         	output.writeUTF(request);
         	
         	// showing the subfiles 
         	reply=input.readUTF();
         	System.out.println("Server: "+reply);
         	
         	reply=input.readUTF();
         	System.out.println("Server: "+reply);
         	
         	//the file name
         	request = scanner.nextLine();
         	output.writeUTF(request);
	         	
	  Socket clientsocket2= new Socket (ip,4000);
	  //BufferedInputStream input2 = new BufferedInputStream(clientsocket2.getInputStream());
		BufferedOutputStream outputFile = new BufferedOutputStream(new FileOutputStream("C:\\Users\\sika\\Desktop\\" + request));
		byte[] buffer = new byte [50005000];
		int bytesRead = input.read(buffer,0,buffer.length);
		
		
		outputFile.write(buffer , 0 , bytesRead);
		//outputFile.flush();
		System.out.println("Received");
		clientsocket2.close();
		//outputFile.close();
	//	input2.close();
	  //final Formatter makefile;
	  //makefile=new Formatter(request);
         	 //request = scanner.nextLine();
          	//output.writeUTF(request);
	   //reply=input.readUTF();
	   //System.out.println(reply);
	   
	   
	 
	         	
	         System.out.println("do you want to choose a new file or close");
	            request = scanner.nextLine();
	          	
	            output.writeUTF(request);
				if(request.equalsIgnoreCase("close"))
	         	{	;
					clientsocket.close();
					System.out.println("the connection with the server is closed");
					break;
					
	
	         	}
		    }
		    
			
		}
		
			catch(IOException  e)
			{
				
			}
		
		
	}
};