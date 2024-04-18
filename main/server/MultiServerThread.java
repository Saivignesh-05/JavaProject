package main.server;

import java.util.*;
import java.net.*;
import java.io.*;
import main.services.quiz.*;

public class MultiServerThread extends Thread
{
    private Socket socket = null;
	  private Random rd;
    private int option;
    private StartQuizGame sqg;
    private String quizFileName;
      //CONSTRUCTOR
  public MultiServerThread(Socket socket,String quizFileName)
  {
  	super("MultiServerThread");
  	rd = new Random();
  	this.socket = socket;
    this.quizFileName = quizFileName;
  }

  public void run()
  {
  	System.out.println("New client with address : "+socket.getInetAddress());
  	try
    {
  	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
  	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


  	    String inputLine;
        char temp;
  	    StringBuffer output = new StringBuffer();
        out.println("Hello, You are now connected to Vignesh's Server...\n");


      while(true)
      {
        out.println("What can I for you today:\n");
  		  out.println("\t\t- 1.PLAY UNO GAME\n\t\t- 2.PLAY A QUIZ GAME\n\t\t- 3.\n\t\t- 4.BYE\n");
        out.println("\t\tEnter your option : \n-EOF-");
        out.flush();

        inputLine = in.readLine();
        option = Integer.parseInt(inputLine);
        switch(option)
        {
          case 1:
      //      out.println("OKAY!!! HERE ARE THE RULES...\n-EOF-");
            PlayerQueue.AddPlayerToQueue(socket);
            while(PlayerQueue.getGameStatus()==false)
            {
        //      System.out.println("game status : " + PlayerQueue.getGameStatus());
              Thread.sleep(1000);
            }
              Thread.sleep(5000);
            break;
          case 2:
        //  out.println("OKAY!!! HERE ARE THE RULES...\n-EOF-");
           new StartQuizGame(out,in,quizFileName);
          break;
          case 3:

          break;
          case 4:
            out.println("Bye, have a nice day!\n-EOF-");
            out.close();
            in.close();
            socket.close();
            break;
          default:
            out.println("Please enter within the specified options!\n-EOF-");
        }//end of switch
        if(option == 4)
          break;
          out.flush();
      }//end of while

	   }// end of try
     catch (IOException e)
     {
	      e.printStackTrace();
	   }
     catch(InterruptedException ie)
     {
       ie.printStackTrace();
     }

    finally
    {
  		try
      {
  		    socket.close();
  		}
      catch(Exception e){System.out.println("Problem while closing socket..."+e);}
  	}


  }// end of run function

}// end of class definition
