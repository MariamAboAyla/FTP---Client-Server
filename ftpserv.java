package ftp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

//import ftp.ftpserv.clientconnection;

//import task.server.clientconnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ftpserv extends Thread{
	public static void main(String[] args) { 
	
	
		
	try {
		
		ServerSocket serversocket=new ServerSocket(3000);
		ServerSocket serversocket2=new ServerSocket(4000);
		System.out.println("Server is booted up & is waiting for clients to connect.");
		while (true){
			
			Socket clientsocket=serversocket.accept();
			System.out.println("client is connected");
			Thread client = new clientconnection(clientsocket,serversocket2);
			client.start();
			
		}
	}
	catch (IOException e) {
		System.out.println("problem with serversocket");
		
	}
	}
static class clientconnection extends Thread {
		private Socket clientsocket;
		private ServerSocket serverSocket2;

		public clientconnection(Socket clientsocket,ServerSocket serverSocket2) 
		{
			this.clientsocket = clientsocket;
			this.serverSocket2 = serverSocket2;
		}
		@Override
		public void run() {
			String [] q=new String [8];
			int l=0;
			try {
				File myfile=new File ("names.txt");
				Scanner myreader=new Scanner (myfile);
				while(myreader.hasNextLine())
				{
					String data=myreader.nextLine();
					q[l]=data;
					l++;
				}
				myreader.close();
				/*for (int j=0;j<8;j++)
				{
					System.out.println(q[j]);
				}*/
			}
		catch(FileNotFoundException e)
			{
			System.out.println("An error has occured");
			}
			try {
				DataInputStream input=new DataInputStream(clientsocket.getInputStream());
				DataOutputStream output=new DataOutputStream(clientsocket.getOutputStream());
				output.writeUTF("connected");
				//while(true) {
				output.writeUTF("Send the username");
				String name=input.readUTF();
				System.out.println("Clinet: "+name);
				
				String pass = " ";
				String ans=" ";
				int found=0;
				//System.out.println(q[1]);
				
				for(int i=0;i<8;i++) 
				{
					
					if(q[i].equalsIgnoreCase(name))
					{
						pass=q[i+1];
						ans="confirm username";
						found=1;
						break;
					}
				}
			
			
			if(found==0)
			{ 
				ans="login failed and the connection will terminate ";
				output.writeUTF(ans);
                System.out.println("client is terminated");
				clientsocket.close();

			}else
			output.writeUTF(ans);
			
			

			output.writeUTF("Send the password");
			String request =input.readUTF();
			
			System.out.println("Clinet: "+request);
			
			if(request.equalsIgnoreCase(pass))
			{
				//login 
				output.writeUTF("login successfully");
				
				 
				 //directories
				while (true) {
					String askk=input.readUTF();
					System.out.println("client: "+askk);
				 File file = new File("E:\\ftp\\"+name);
				 String[] sub;
				 sub=file.list();
				 String joinedString = null;
				 for (String x: sub) {
					 joinedString = String.join(" , ", sub);    // storing the array into string
			        }
				 output.writeUTF("those are the available directories\n "+joinedString);
				 System.out.println(joinedString);
				 
				 //sub directory
				 output.writeUTF(" enter the command ");
				 String filename = input.readUTF();
				 System.out.println("client: "+filename);
				 int count=0;
				 
				 for (int i=0 ; i<sub.length ; i++) //counter to check if command available or not
				 {
					 if (filename.equals(sub[i]))
						 count++;
				 }
				 if (count>0)
				 {
				 File file2 = new File("E:\\ftp\\"+name+"\\"+filename);
				 String[] sub2;
				 sub2=file2.list();
				 String joinedString2 = null;
				 for (String x: sub2)
				 {
					 joinedString2 = String.join(" , ", sub2);
			     }
				 //showing the file 
				 output.writeUTF("those are the available files in this directory \n "+joinedString2);
				 
				 
				 output.writeUTF("choose the file:  ");
				 String filename2 =input.readUTF();
				 System.out.println("client: "+filename2);
				 
				 for (int i=0 ; i<sub2.length ; i++) //counter to check if command available or not
				 {
					 if (filename2.equals(sub2[i]))
						 count++;
				 }
				 if (count>0) {
					 try {
							System.out.println("ftp is ready");
							//while (true){
								
								Socket clientsocket2=serverSocket2.accept();
								System.out.println("client is ready to take the file");
								//BufferedOutputStream output2 = new BufferedOutputStream(clientsocket2.getOutputStream());
								File file56 = new File("E:\\ftp\\"+name+"\\"+filename+"\\"+filename2);
								BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream(file56));
								
								byte[] buffer = new byte[50005000];
								fileReader.read(buffer,0,buffer.length);
								output.write(buffer,0,buffer.length);
								int bytesRead = 0;
								bytesRead = fileReader.read(buffer);
								//output2.write(buffer, 0, bytesRead);
								output.flush();
								
								clientsocket2.close();
								
								//output.close();
								fileReader.close();
								//break;
								
							
						}
						catch (IOException  e) {
							//System.out.println("problem with serversocket2");
							
						}
				 }
				 
				 
				 
					 
				 
				 
				 }
				 
				 else //when the client enters an unexpected command
				 {
					 output.writeUTF("this command is not available");
				 }
				 String reply=input.readUTF();
				
				 if (reply.equals("close")) {
						System.out.println("Closing connection with a client.");
						clientsocket.close();
						break;
					}

					
				}
				//input.close();
				//output.close();
			}
			
			 if(!request.equals(pass))
			 {
				    output.writeUTF("login failed and the connection will terminate ");
	                System.out.println("client is terminated");
					clientsocket.close();

		 
			 }
			 output.writeUTF(request);
			 
			 
			 
			
			
			
				}
			catch (IOException e) {
				System.out.println("Connection with a client is terminated.");
			}
	}
	
}
	}
	
