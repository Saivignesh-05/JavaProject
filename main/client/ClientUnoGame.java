package main.client;
import java.io.*;
import main.services.uno.*;
import java.util.*;
import main.utility.*;

public class ClientUnoGame
{
  private String myName, serverMessage;
  private BufferedReader in;
  private PrintWriter out;
  private ArrayList<UnoCard> myCards;
  public ClientUnoGame(BufferedReader in, PrintWriter out, String myName)
  {
    this.myName = myName;
    this.out = out;
    this.in = in;
    myCards = new ArrayList<UnoCard>();
    startUnoGame();
  }

  public void receiveMyCards()
  {
    String cardDetail,tokens[];
    UnoCard card;
    try{
      while(true)
      {
        cardDetail = in.readLine();
        if(cardDetail==null)
          continue;

        else if(cardDetail.equals("-EOF-"))
          break;
        tokens = cardDetail.split("-");
        card = new UnoCard(UnoCard.Colour.valueOf(tokens[1]),UnoCard.Number.valueOf(tokens[0]));
        myCards.add(card);

      }
        System.out.println("Total cards with me : " + myCards.size());
    }
    catch(IOException ie)
    {
      ie.printStackTrace();
    }
  }//  END OF receiveMyCards function

  public String checkSplMessage(String message)
  {
    switch(message)
    {
      case "WON":
      case "LOST":
        System.out.println("the game has ended!");
        return message;

      case "draw":
        System.out.println("DRAWING CARDS due opponent power!");
        receiveMyCards();
      break;

      default:
  //      System.out.println(message);

    }
    return null;

  }

    public String receiveTopCard()
    {
      String topCardDetail,tokens[],finish;
      UnoCard topCard = null;

      try
      {
  //        System.out.println("Waiting for spl message:");
          topCardDetail = in.readLine();
          finish = checkSplMessage(topCardDetail);
          if(finish!=null)
            return finish;

  //      System.out.println("Waiting for top card:");
        topCardDetail = in.readLine();
        finish = checkSplMessage(topCardDetail);
        if(finish!=null)
          return finish;


          tokens = topCardDetail.split("-");
          topCard = new UnoCard(UnoCard.Colour.valueOf(tokens[1]),UnoCard.Number.valueOf(tokens[0]));
          System.out.println("Current Top Card : " + topCard.toString());

      }

      catch(IOException ie)
      {
        ie.printStackTrace();
      }
      return null;
    } // end of receiveTopCard function.

  public void printMyCards()
  {
    int i=0;
    for(UnoCard card : myCards)
      System.out.println((++i) + "." + card);

  }

  public void playOrWait()
  {
    UnoCard playMyCard = null;
    int option;
    try
    {
      String message = in.readLine();

      if(message.equals("play"))
      {
        printMyCards();
        while(true)
        {
          option = Validator1.getInt("Enter Card Number to Play(0 if no card) : ");
          option-=1;
  //        System.out.println("option : " + option);
          if(option == -1)
          {
            out.println("drawcard");
            out.flush();
            message = in.readLine();      // RECEIVE PLAYED OR KEEP MESSAGE

            System.out.println("played or keep? : " + message);
            if(message.equals("keep"))    // CANNOT PLAY THE DRAWN CARD
              receiveMyCards();

            break;
          }
          else
          {
            if(option>=myCards.size() || option<0)
            {
              System.out.println("Not valid : ");
              continue;
            }
            playMyCard = myCards.get(option);
  //          System.out.println("im playing : " + playMyCard.toString());
  //          printMyCards();
            out.println(playMyCard);
            out.flush();


            message = in.readLine();

            if(message.equals("ok"))
            {
              myCards.remove(option);
              break;
            }

          //  else myCards.add(option,playMyCard);
          }
        }
      }
    }
    catch(IOException i)
    {
      i.printStackTrace();
    }

  }// end of funciton

  public void startUnoGame()
  {
    String topCardString;
    UnoCard topCard, playMyCard;
    String gameStatus;
    try
    {
      // wait till Server tells start game..
      System.out.println("MATCHING...\n");
      serverMessage = in.readLine();// receive message from server as start.  If quit is mentioned then exit saying no other players, try again

      switch(serverMessage.toLowerCase())
      {
          case "quit":
            // throw NoPlayerException
            return;
          case "start":
            System.out.println("YOUR CARDS FOR THE GAME...\n");
            break;
     }

      receiveMyCards();
      while(true)
      {
        gameStatus = receiveTopCard();
        if(gameStatus!=null)
        {
           System.out.println("YOU " + gameStatus + " THE GAME!!");
           break;
        }

        playOrWait();
      }

    }// end of try
    catch(IOException ie)
    {
      ie.printStackTrace();
    }

  }// end of StartUnoGame


}// end of class
