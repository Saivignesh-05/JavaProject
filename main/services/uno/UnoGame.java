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

    public void sendMessage(String msg)
    {
      for(PrintWriter out : outList)
        {out.println(msg);
        out.flush();}
    }

    public void sendSplMessage(String message, int player)
    {
      int i;
      for(i=0;i<noOfPlayers;++i)
      {
        if(i==player)
          outList.get(i).println(message);
        else outList.get(i).println("nothing");
      }
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

    public UnoCard cardFromPlayerCase1(int playerNumber, UnoCard topCard)
    {
      UnoCard drawCard = deck.drawCard();
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

    public UnoCard cardFromPlayerCase2(int playerNumber,UnoCard topCard,String message)
    {
      UnoCard.Colour colour; UnoCard.Number number;
      String tokens[] = message.split("-");
      colour = UnoCard.Colour.valueOf(tokens[1]);
      number = UnoCard.Number.valueOf(tokens[0]);
      if(topCard.equalsCol(colour) || topCard.equalsNum(number))
      {

        outList.get(playerNumber).println("ok");
        outList.get(playerNumber).flush();
        updateCardCount(-1,playerNumber);
        return new UnoCard(colour,number);

      }
      else
      {
        outList.get(playerNumber).println("wrong card");
        outList.get(playerNumber).flush();
        return null;
      }
    }

    public UnoCard getCardFromPlayer(int playerNumber,UnoCard topCard)
    {
      String message;
      int nxtPlayer = (playerNumber+1)%noOfPlayers,i;
      UnoCard card = null,drawCard = null,sendCard = null;
//      System.out.println("player : " + playerNumber);
      try
      {
        while(true)
        {
          message = inList.get(playerNumber).readLine();
          System.out.println("message : " + message);
          if(message.equals("drawcard"))
          {
            card = cardFromPlayerCase1(playerNumber,topCard);
            break;
          }
          else
          {
            card = cardFromPlayerCase2(playerNumber,topCard,message);
            if(card!=null)
              break;
          }
        }
        if(card!=null)
        {
          switch(card.getNumber())
          {
            case DrawTwo:
              sendCard = deck.drawCard();
              sendSplMessage("draw", nxtPlayer);
              outList.get(nxtPlayer).println(sendCard);
              sendCard = deck.drawCard();
              outList.get(nxtPlayer).println(sendCard + "\n-EOF-");
            break;

            case WildDrawFour:
              sendSplMessage("draw", nxtPlayer);
              for(i=0;i<4;++i)
              {
                sendCard = deck.drawCard();
                outList.get(nxtPlayer).println(sendCard);
              }
              outList.get(nxtPlayer).println("-EOF-");
            break;

            default:
              sendSplMessage("nothing", nxtPlayer);
          }
        }
        else sendSplMessage("nothing", nxtPlayer);

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
        sendMessage("start");
        sendPlayerCards(cardsPerGame);
        sendMessage("continue");    // special message
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
