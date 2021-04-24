package main.server;

import java.net.*;
import java.io.*;

public class MultiServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        String quizFileName;


        try {
            quizFileName = args[1];
        }
        catch(ArrayIndexOutOfBoundsException aoe)
        {
          quizFileName = "1";
        }

        try{
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }


        while (listening)
	    new MultiServerThread(serverSocket.accept(),quizFileName).start();

        serverSocket.close();
    }

}

/*
	private static int noOfClients=0;

	public static void addMe(){
	noOfClients++;
	System.out.println("Current no. of clients connected: "+noOfClients);
}
	public static void removeMe(){
	noOfClients--;
	System.out.println("Current no. of clients connected: "+noOfClients);
}

*/
