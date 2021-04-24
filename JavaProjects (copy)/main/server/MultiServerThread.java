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
  //  private StartQuizGame sqg;
    private String quizFileName;
      //CONSTRUCTOR
  public MultiServerThread(Socket socket, String quizFileName)
  {
  	super("MultiServerThread");
  	rd = new Random();
  	this.socket = socket;
    this.quizFileName = quizFileName;
  }

  public void run()
  {
  	System.out.println("Now connected to: "+socket.getInetAddress());
  	try
    {
  	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
  	    BufferedReader in = new BufferedReader(
  				    new InputStreamReader(socket.getInputStream()));

  	    String inputLine, outputLine="--";
  	    StringBuffer output = new StringBuffer();
        out.println("You are now connected to Vignesh's Server...\n");


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
            //StartUnoGame();
            out.println("case 1\n-EOF-");
          break;
          case 2:
          out.println("OKAY!!! HERE ARE THE RULES...\n-EOF-");
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



/*
  private String processInput(String inputLine)
  {
    int choice = Integer.parseInt(inputLine);
    switch(choice)
    {
      case 1:
        //StartUnoGame();
      break;
      case 2:
        StartQuizGame();
    }
*/



/*

    String input = inputLine.toLowerCase().trim();
	  StringBuffer output = new StringBuffer();
  		if(inputLine.startsWith("hello")){
			output.append("Hello my dear Client...\nHere are the following services I can provide today:\n");
			output.append("\t\t- URL\n\t\t- Time\n\t\t- Name\n\t\t- Thought of the Day(TOTD)");
			output.append("\n\tFor URL example:\n\t URL=http:\\\\www.google.com");
			output.append("\n\tFor Name example: \n\t name=Sairam");
			return output.toString();
		}else if(inputLine.startsWith("url")){
			//String[] tokens = inputLine.split("=");
			return provideURLService(inputLine.substring(inputLine.indexOf('=')+1));
		}else if(inputLine.startsWith("time")){
			return new Date().toString();
		}else if(inputLine.startsWith("totd")){
			return provideTOTDService();
		}else if(inputLine.startsWith("name")){
			String 	name = inputLine.substring(inputLine.indexOf('=')+1);
			return "Hello and sairam "+name;
		}else if(inputLine.startsWith("bye")){
			return "Bye and Have a good day ";
		}else {
			return "Sorry my dear client, I did not understand your request...Try again...";
		}
    */


/*
while ((inputLine = in.readLine()) != null)
{
  System.out.println(inputLine);
  outputLine = processInput(inputLine);
  out.println(outputLine);
  out.println("-EOF-");
  if (outputLine.contains("Bye"))
  {
      out.close();
      in.close();
      socket.close();
      break;
  }
}
*/
