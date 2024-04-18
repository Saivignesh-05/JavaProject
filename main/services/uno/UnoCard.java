package main.services.uno;

public class UnoCard
{
  public enum Colour
  {
    Red, Blue, Green, Yellow, Wild;

//    public final String colourLabel;
    private static final Colour colours[] = Colour.values();
/*
    private Colour(String colourLabel)
    {
      this.colourLabel = colourLabel;
    }
*/
    public static Colour getColour(int i)
    {
      return Colour.colours[i];
    } // 0 is red, 1 is blue...
  }

  public enum Number
  {
    Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, Skip,
    Reverse, Wild, WildDrawFour;

  //  public final String numberLabel;
    private static final Number numbers[] = Number.values();
/*    private Number(String numberLabel)
    {
      this.numberLabel = numberLabel;
    }
*/    public static Number getNumber(int i)
    {
      return Number.numbers[i];
    }
  }

  private final Colour colour;
  private final Number number;


  public UnoCard(Colour colour, Number number)
  {
    this.colour = colour;
    this.number = number;
  }

  public Colour getColour()
  {
    return this.colour;
  }

  public Number getNumber()
  {
    return this.number;
  }

  public String toString()
  {
    return this.number + "-" + this.colour;
  }

  public boolean equalsCol(Colour colour)
  {
    System.out.println("in check func 1");
    if(this.colour == colour)
      {
        System.out.println("colour match 1\n");
        return true;
      }
    else return false;
  }

  public boolean equalsNum(Number number)
  {
    System.out.println("in check func 2");
    if(this.number == number)
    {
      System.out.println("Number match 1\n");
      return true;
    }
    else return false;
  }


  public boolean equals(UnoCard card)
  {
    System.out.println("in check func 3");
    if(this.colour == card.colour)
    {
      System.out.println("colour match 2\n");
      return true;
    }
    else if(this.number == card.number)
    {
      System.out.println("number match 2\n");
      return true;
    }

    else return false;
  }
}

/*
  public static Colour valueOfColourLabel(String label)
  {
    for(Colour colour : Colour.values())
    {
      if(colour.colourLabel.equals(label))
        break;
    }
    return colour;
  }

  public static Number valueOfNumberLabel(String label)
  {
    for(Number number : Number.values())
    {
      if(number.numberLabel.equals(label))
        return number;
    }
  }
*/
