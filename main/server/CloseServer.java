package main.server;
import java.util.*;
import main.utility.*;
import java.io.*;


public class CloseServer extends Thread
{
  private String line, tokens[],quit;
  private File sorted;FileWriter fw;
  private TreeMap<String,Integer> map;
  private BufferedReader temp;
  private Map.Entry mentry;

  public void run()
  {

    quit = Validator1.getString("Enter quit to close the server : ");
    if(quit.equals("quit"));
    {
      try
      {
      sorted = new File("main/services/quiz/questions/sortedRankings");
      if(!sorted.exists())
        sorted.createNewFile();
      fw = new FileWriter(sorted,true);
      map = new TreeMap<String,Integer>();
      temp = new BufferedReader(new FileReader("main/services/quiz/questions/rankings"));

        for(line = temp.readLine();line!=null;line = temp.readLine())
                    {
                      tokens = line.split("\t\t");
                      map.put(tokens[0],Integer.parseInt(tokens[1]));
          //            System.out.println(tokens[0] + "\t" + tokens[1]);
                    }
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext())
        {
           mentry = (Map.Entry)iterator.next();
           System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
           System.out.println(mentry.getValue());
           fw.write(mentry.getKey()+ "\t\t" + mentry.getValue() + "\n");

        }
        fw.close();
      }
      catch(IOException ie)
      {
        ie.printStackTrace();
      }
      return;
    }
  }
}
