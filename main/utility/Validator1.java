package main.utility;

import java.util.Scanner;

public class Validator1
{
    public static String getString(Scanner sc, String prompt)
    {
        System.out.print(prompt);
        String s = sc.next();  // read user entry
        sc.nextLine();  // discard any other data entered on the line
        return s;
    }

	public static String getString(String prompt)
    {
		Scanner sc = new Scanner(System.in);
        System.out.print(prompt);
        String s = sc.next();  // read user entry
        sc.nextLine();  // discard any other data entered on the line
        return s;
    }
    public static String getLine(String prompt)
      {
  		Scanner sc = new Scanner(System.in);
          System.out.print(prompt);
          return sc.nextLine();  // discard any other data entered on the line

      }

    public static int getInt(Scanner sc, String prompt)
    {
        int i = 0;
        boolean isValid = false;
        while (isValid == false)
        {
            System.out.print(prompt);
            if (sc.hasNextInt())
            {
                i = sc.nextInt();
                isValid = true;
            }
            else
            {
                System.out.println("Error! Invalid integer value. Try again.");
            }
            sc.nextLine();  // discard any other data entered on the line
        }
        return i;
    }

    public static int getInt(String prompt)
    {
		Scanner sc = new Scanner(System.in);
        int i = 0;
        boolean isValid = false;
        while (isValid == false)
        {
            System.out.print(prompt);
            if (sc.hasNextInt())
            {
                i = sc.nextInt();
                isValid = true;
            }
            else
            {
                System.out.println("Error! Invalid integer value. Try again.");
            }
            sc.nextLine();  // discard any other data entered on the line
        }
        return i;
    }
    public static float getFloat(String prompt)
    {
		Scanner sc = new Scanner(System.in);
        float i = 0;
        boolean isValid = false;
        while (isValid == false)
        {
            System.out.print(prompt);
            if (sc.hasNextFloat())
            {
                i = sc.nextFloat();
                isValid = true;
            }
            else
            {
                System.out.println("Error! Invalid float value. Try again.");
            }
            sc.nextLine();  // discard any other data entered on the line
        }
        return i;
    }
    public static double getDouble(String prompt)
    {
		Scanner sc = new Scanner(System.in);
        double i = 0;
        boolean isValid = false;
        while (isValid == false)
        {
            System.out.print(prompt);
            if (sc.hasNextDouble())
            {
                i = sc.nextDouble();
                isValid = true;
            }
            else
            {
                System.out.println("Error! Invalid Double value. Try again.");
            }
            sc.nextLine();  // discard any other data entered on the line
        }
        return i;
    }
    public static int getInt(Scanner sc, String prompt,
    int min, int max)
    {
        int i = 0;
        boolean isValid = false;
        while (isValid == false)
        {
            i = getInt(sc, prompt);
            if (i <= min)
                System.out.println(
                    "Error! Number must be greater than " + min + ".");
            else if (i >= max)
                System.out.println(
                    "Error! Number must be less than " + max + ".");
            else
                isValid = true;
        }
        return i;
    }

    public static int getInt(String prompt,
    int min, int max)
    {
		Scanner sc = new Scanner(System.in);
        int i = 0;
        boolean isValid = false;
        while (isValid == false)
        {
            i = getInt(sc, prompt);
            if (i <= min)
                System.out.println(
                    "Error! Number must be greater than " + min + ".");
            else if (i >= max)
                System.out.println(
                    "Error! Number must be less than " + max + ".");
            else
                isValid = true;
        }
        return i;
    }
}
