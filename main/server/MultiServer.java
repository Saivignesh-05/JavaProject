package main.server;

import java.net.*;
import java.io.*;
import java.util.*;
import main.utility.*;
public class MultiServer {

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;
        boolean listening = true,quit;
        String quizFileName;

        try{
          quizFileName = args[1];
        }
        catch(ArrayIndexOutOfBoundsException aoe)
        {
        //  quizFileName = Validator1.getString("Enter the File Name to conduct quiz on : ");
            quizFileName = "1"; // seting default
        }
  //      new CloseServer().start();
        try {
            serverSocket = new ServerSocket(4444);
            while (listening)
    	       new MultiServerThread(serverSocket.accept(),quizFileName).start();
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        serverSocket.close();



            // FOR SORTING THE RANKINGS...

    }
}
