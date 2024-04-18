package main.server;
import java.util.*;
import java.net.*;
import main.services.uno.*;

public class PlayerQueue
{
  private static Queue<Socket> playerQueue = new LinkedList<Socket>();
  private static ArrayList<Socket> sendPlayerList = new ArrayList<Socket>();
  private static final int playersPerGame = 2;
  private static boolean gameOver = false;

  public static void AddPlayerToQueue(Socket player)
  {
    int i;
      playerQueue.add(player);
      System.out.println("player queue size : " + playerQueue.size());
      if(playerQueue.size()==playersPerGame)
      {
        for(i=0;i<playersPerGame;++i)
          sendPlayerList.add(playerQueue.remove());
        System.out.println("Starting new game");
        new UnoGame(sendPlayerList,playersPerGame);
        gameOver = true;
        System.out.println("COMPLETED GAME...");
      }
      sendPlayerList.clear();
      System.out.println("play queue cleared..");
  }

  public static int getPlayersInQueue()
  {
    return playerQueue.size();
  }

  public static boolean getGameStatus()
  {
    return gameOver;
  }
}
