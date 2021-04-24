package main.client;

import java.net.*;
import java.io.*;
import main.utility.*;
//import java.util.*;
//import java.lang.*;

public class Client
{

  public static void main(String args[ ]) throws Exception
  {
    String host = "localhost",keyboardInput,response;
    InetAddress server = InetAddress.getByName(host);
    Socket s = new Socket(server, 4444);
    BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
    PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
    int myChoice;
    ClientQuizGame cqg;

  while(true)
  {

    while(true)
    {
        response = r.readLine();
        if(response.equals("-EOF-"))
          break;
       System.out.println(response);
    }

    keyboardInput = Validator1.getString("");

    out.println(keyboardInput);
    out.flush();


    while(true)
    {
        //response = r.readLine();
        if((response = r.readLine()).equals("-EOF-"))
          break;
       System.out.println("server : " + response);
    } // server reply to the option

    myChoice = Integer.parseInt(keyboardInput);
    switch(myChoice)
    {
        case 1:   // UNO GAME
          break;

        case 2:
          cqg = new ClientQuizGame(out,r);
          break;

        case 3:
          break;

        default:
          break;
    }
      if(myChoice == 4)
        break;

  }// end of while1





    s.close();
  }
}
