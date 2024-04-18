package main.services.quiz;

import java.io.*;
import java.util.*;
import main.utility.*;
public class StartQuizGame
{
  private PrintWriter out;
  private BufferedReader in,temp;
  private int clientMarks,totalMarks,positiveMarking,negativeMarking;
  private int noOfQuestions;
  private String clientName;
  private String clientAnswer,question,answer,quizFileName,fileInput;
  private String path = "main/services/quiz/questions/",quizPath,rankingPath ;
  private File rankings;


  public StartQuizGame(PrintWriter pw, BufferedReader br,String quizFileName)
  {
    out = pw; in = br;
    noOfQuestions = 5;
    positiveMarking = 1;
    negativeMarking = 0;      // set a negative number only
    totalMarks = noOfQuestions*positiveMarking;
    clientMarks = 0;
    quizPath=path + quizFileName;

    try{
        temp = new BufferedReader(new FileReader(quizPath));
    }
    catch(FileNotFoundException fe)
    {
      fe.printStackTrace();
    }
    conductQuiz();
  }

  public void printRules()
  {
    out.println("\nEnter only one option from the choices given.Enter quit to quit game\n-EOF-");
    out.flush();
  }

  public int getTotalMarks()
  {
    return totalMarks;
  }

  public int getClientMarks()
  {
    return clientMarks;
  }

  public void setClientMarks(int marking)
  {
    clientMarks+=marking;
  }

  public void getClientName()
  {
    try{
    //  System.out.println("client name : " + clientAnswer);
    clientName = in.readLine(); // for client name
    }
    catch(IOException ie)
    {
      ie.printStackTrace();
    }
  }

  public void getClientResponse()
  {
    try
    {
      clientAnswer = in.readLine();
      clientAnswer = clientAnswer.toUpperCase();
      //System.out.println("client answer : " + clientAnswer);
    }
    catch(IOException ie)
    {
      ie.printStackTrace();
    }

    if(clientAnswer.equals(answer))
      {//System.out.println("CORRECT ANSWER!!!\n-EOF-");
      setClientMarks(positiveMarking);}

    else if(clientAnswer == "quit")
        return;

    else
        {//System.out.println("WRONG ANSWER!!!\n-EOF-");
        setClientMarks(negativeMarking);}

  }

  public void readQuestionFromFile()
  {
    try{
      while(true)
      {
        question = temp.readLine();
        out.println(question);
        if(question.equals("-EOF-") || question == null)
          break;
      }
      out.println("-EOF-");
      answer = temp.readLine();   // reading the answer which is after EOF
      out.flush();
    }
    catch(IOException ie)
    {
      ie.printStackTrace();
    }
//    System.out.println("answer : " + answer);
  }

  public void sendClientScore()
  {
    out.println("\nYou have scored " + getClientMarks() + " out of " + getTotalMarks() + "\n-EOF-");
    out.flush();
  }

  synchronized public void setRankings()
  {
    try{
      fileInput = clientName + "\t\t" + getClientMarks() + "\n";
      rankingPath = path + "rankings";
      rankings = new File(rankingPath);
      if(!rankings.exists())
        rankings.createNewFile();
      FileWriter fw = new FileWriter(rankings,true);
      fw.write(fileInput);

      fw.close();
    }
    catch(IOException ie)
    {
      ie.printStackTrace();
    }


  }

  private void conductQuiz()
  {
    int iterator;

    printRules();

    for(iterator=0;iterator<noOfQuestions;++iterator)
    {
      readQuestionFromFile();

      getClientResponse();    //RECEIVE THE ANSWER FROM CLIENT...

    }
      sendClientScore();      //SEND CLIENT HIS FINAL SCORE...

      getClientName();

      setRankings();
  }


}
