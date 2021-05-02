package main.services.uno;

import java.io.*;
import java.net.*;
import java.util.*;
import main.services.uno.*;

public class UnoGame
{
  private int noOfPlayers;
  private static final int cardsPerGame = 1;
  private ArrayList<Socket> players;
  private ArrayList<PrintWriter> outList;
  private ArrayList<BufferedReader> inList;
  private ArrayList<Integer> playerCardCount;
  private UnoDeck deck;

    public UnoGame(ArrayList<Socket> playersList, int noOfPlayers)  // constructor
    {
      this.noOfPlayers = noOfPlayers;
      players = new ArrayList<Socket>();
      players = playersList;
      outList = new ArrayList<PrintWriter>();
      inList = new ArrayList<BufferedReader>();
      playerCardCount = new ArrayList<Integer>();
      deck = new UnoDeck();

      startUnoGame();
    }

    public void initializeConnections()
    {
      try{
        PrintWriter out;
        BufferedReader in;
        for(Socket socket : players)
        {
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          out = new PrintWriter(socket.getOutputStream(), true);
          outList.add(out);
          inList.add(in);
        }
      }
      catch(IOException ie)
      {
        ie.printStackTrace();
      }

    }

    public void sendStartMessage()
    {
      for(PrintWriter out : outList)
        {out.println("start");
        out.flush();}
    }

    public void sendPlayerCards(int noOfCards)
    {
      deck.newDeck();
      deck.shuffle();
  //    System.out.println("Cards in deck : " + deck.getCardsInDeck());
      List<UnoCard> playerCards;
      for(PrintWriter out : outList)
      {
        playerCards = deck.drawMultipleCards(noOfCards);
        for(UnoCard card : playerCards)
        {
    //      System.out.println("Card : " + card.toString());
          out.println(card);
        }
        out.println("-EOF-");
        out.flush();
      }
    }

    public UnoCard sendTopCard()   // SENT TO ALL PLAYERS
    {
      UnoCard topCard = deck.drawCard();
      for(PrintWriter out : outList)
      {
          out.println(topCard);   // SENDING THE OPENING CARD...
          out.flush();
      }
      return topCard;
    }

    public void sendNewTopCard(UnoCard card) // TO SEND WHAT THE PLAYER PLAYED WHICH BECOMES TOP CARD
    {
    //    System.out.println("Sending the card : " + card.toString());
      for(PrintWriter out : outList)
      {
          out.println(card);   // SENDING THE TOP CARD...
          out.flush();
      }
    }

    public void whomToPlay(int playerNumber)
    {
      int i=0;
      for(PrintWriter out : outList)
      {
        if(i==playerNumber)
          out.println("play");
        else out.println("wait");

        ++i;
      }
    }

    public UnoCard getCardFromPlayer(int playerNumber,UnoCard topCard)
    {
      String message,tokens[];
      UnoCard card = null,drawCard = null;
      UnoCard.Colour colour; UnoCard.Number number;
//      System.out.println("player : " + playerNumber);
      try
      {
        while(true)
        {
          message = inList.get(playerNumber).readLine();
          System.out.println("message : " + message);
          if(message.equals("drawcard"))
          {
            drawCard = deck.drawCard();
            System.out.println("card drawn : " + drawCard);
            if(topCard.equals(drawCard))
            {
              outList.get(playerNumber).println("played");
              outList.get(playerNumber).flush();
              return drawCard;
            }
            else
            {
              outList.get(playerNumber).println("keep");
              outList.get(playerNumber).println(drawCard + "\n-EOF-");
              outList.get(playerNumber).flush();
              updateCardCount(1,playerNumber);
              return null;
            }
          }
          else
          {
  //          System.out.println("client card : " + message);
            tokens = message.split("-");
            colour = UnoCard.Colour.valueOf(tokens[1]);
            number = UnoCard.Number.valueOf(tokens[0]);
//            System.out.println("col : " + colour + "num : " + number);
//            System.out.println("top card now : " + topCard);
            if(topCard.equalsCol(colour) || topCard.equalsNum(number))
            {
              card = new UnoCard(colour,number);
//                System.out.println("card played ok");
              outList.get(playerNumber).println("ok");
              outList.get(playerNumber).flush();
              updateCardCount(-1,playerNumber);
              break;
            }
            else
            {
              outList.get(playerNumber).println("wrong card");
              outList.get(playerNumber).flush();
//                System.out.println("Card pplayed not ok");
            }
          }

        }
      }
      catch(IOException ie)
      {
        ie.printStackTrace();
      }

      return card;
    }

    public void initializeCardCount(int count)
    {
      int i;
      for(i=0;i<noOfPlayers;++i)
        playerCardCount.add(count);

    }

    public void updateCardCount(int count, int playerNumber)
    {
      int setNum = playerCardCount.get(playerNumber) + count;
      playerCardCount.set(playerNumber,setNum);

    }

    public int checkGameOver(int currentPlayer)
    {
      return playerCardCount.get(currentPlayer);
    }

    public void startUnoGame()
    {
        int currentPlayer,i;
        UnoCard topCard,newTopCard;
        boolean gameOver = false;

        initializeConnections();    // creating the out and in for each player socket.
        initializeCardCount(cardsPerGame);
        sendStartMessage();
        sendPlayerCards(cardsPerGame);
        topCard = sendTopCard();   // OPENING card.

// STARTING THE GAME NOW...
      while(true)
      {

        for(currentPlayer = 0;currentPlayer<noOfPlayers;++currentPlayer)
        {
          whomToPlay(currentPlayer);
          newTopCard = getCardFromPlayer(currentPlayer,topCard);

          if(checkGameOver(currentPlayer)==0)
          {
            gameOver = true;
            System.out.println(currentPlayer + " has finished");
            break;
          }

          if(newTopCard==null)
            sendNewTopCard(topCard);
          else
          {
            sendNewTopCard(newTopCard);
            topCard = newTopCard;
          }
          //      System.out.println("Going to send top card");


        }
        if(gameOver)
          break;
      }
System.out.println("sending won or lost");
      for(i=0;i<noOfPlayers;++i)
      {
        if(i==currentPlayer)
          outList.get(i).println("WON");
        else outList.get(i).println("LOST");
      }

    }// end of start uno game.
}
