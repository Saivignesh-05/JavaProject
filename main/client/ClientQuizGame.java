package main.client;

import main.utility.*;
import java.io.*;
public class ClientQuizGame
{
  private PrintWriter out;
  private BufferedReader in;
  private String question,answer;
  private static int noOfQuestions;
  private String clientName;


  public ClientQuizGame(PrintWriter p, BufferedReader b,String clientName)
  {
    out = p;
    in = b;
    noOfQuestions = 5;
    this.clientName = clientName;
    StartGame();
  }

  public void readServerResponse()
  {
    try
    {
      while(true)
      {
          question = in.readLine();
          if(question.equals("-EOF-"))
            break;
          else if(question == null)
          continue;
         System.out.println(question);
      }
    }
    catch(IOException ie)
    {
      ie.printStackTrace();
    }
  }


  public void sendMyResponse()
  {
    answer = Validator1.getString("Enter your option : ");

    out.println(answer);
    out.flush();

  }

  private void StartGame()
  {
    int iterator;
    String temp;
    readServerResponse();   // FOR READING THE RULES OF GAME.
    for(iterator = 0; iterator<noOfQuestions;++iterator)
    {
//System.out.println("read server response 1");
      readServerResponse();   // RECIEVE THE question
//System.out.println("sending my response");
      sendMyResponse();       // SENDING THE answer to server...
      temp = answer.toLowerCase();
      if(temp.equals("quit"))
        return;
//System.out.println("read server response 2");
      readServerResponse();   //RESPONSE IF ANSWER IS CORRECT OR WRONG

    }// end of for loop

    //SERVER SENDS THE TOTAL MARKS...
//System.out.println("read server response for final marks");
    readServerResponse();   // SERVER SENDS FINAL MARKS

    out.println(clientName);
    out.flush();

  }


}
