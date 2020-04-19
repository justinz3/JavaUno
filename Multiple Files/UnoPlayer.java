
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class UnoPlayer
{
  static Scanner reader = new Scanner(System.in);
  static Random generator = new Random();
  static boolean[] isHuman = new boolean[100];
  static int numPlayers;
  static int currentPlayer;
  static String[] playerNames = new String[100];
  static int direction = 1;
  
  public static boolean hasWon(int player)
  {
    for(int i = 0; i < UnoDeck.NUM_CARDS; i++)
    {
      if(UnoDeck.cardLocation[i] == player)
      {
        return false;
      }
    }
    return true;
  }
  
  public static void playerTurn(int player)
  {
    System.out.println("Player " + player + "'s turn.");
    System.out.println("The card on the top of the discard pile is: " + UnoDeck.deck[UnoDeck.justDiscarded] + " and is color " + UnoDeck.colors[UnoDeck.justDiscardedColor] + " (Press Enter)");
    if(isHuman[player]) // it's a Human
    {
      UnoDeck.printHand(player);
      if(UnoDeck.checkValidHand(player) == true)
      {
        int chosen = getPlayerChoice(player);
        UnoDeck.activateCard(chosen);
      }
      else
      {
        System.out.println("You didn't have any cards that work, Draw One");
        UnoDeck.drawCard(player);
        reader.nextLine();
        System.out.println("Your current hand: ");
        UnoDeck.printHand(player);
      }
    }
    
    else // it's a computer
    {
      System.out.println("Player " + player + " has " + UnoDeck.getNumPlayerCards(player) + " cards");
      if(UnoDeck.checkValidHand(player) == true)
      {
        int chosen = getCompChoice(player);
        System.out.println("Player " + player + " has played " + UnoDeck.deck[chosen]);
        UnoDeck.activateCard(chosen);
        
      }
      else
      {
        UnoDeck.drawCard(player);
        System.out.println("Player " + player + " has has drawn a card");
      }
      System.out.println("Player " + player + " has " + UnoDeck.getNumPlayerCards(player) + " cards");
      
      System.out.println("(Press Enter to continue)");
      reader.nextLine();
    }
    
    if(isHuman[getNextPlayer(currentPlayer)])
    {
      UnoFinal.clearScreen();
    }
  }
  
  
  
  public static int getPlayerWildColor(int player)
  {
    System.out.println("Player " + player + " choose a color for the wild card you played:");
    System.out.println("1) Red");
    System.out.println("2) Yellow");
    System.out.println("3) Green");
    System.out.println("4) Blue");
    
    int result;
    while(true)
    {
      result = reader.nextInt();
      reader.nextLine();
      if(result > 0 && result <= 4)
        break;
    }
    result--;
    System.out.println("Player " + player + " has chosen the wild card to be " + UnoDeck.colors[result]);
    return result; // we are using 0-3
  }
  
  public static int getCompWildColor(int player)
  {
    int chosen = generator.nextInt(4);
    System.out.println("Player " + player + " has chosen the wild card to be " + UnoDeck.colors[chosen]);
    return chosen;
  }
  
  public static int getCompChoice(int player)
  {
    for(int i = 0; i < UnoDeck.NUM_CARDS; i++)
    {
      if(UnoDeck.cardLocation[i] == player && UnoDeck.canUse(i))
      {
        return i;
      }
    }
    
    return -1;
  }
  
  public static int getPlayerChoice(int player)
  {
    int numCards = UnoDeck.getNumPlayerCards(player);
    
    while(true)
    {
      System.out.println("Enter the number to the left of the (valid) card you want to play:");
      int choice = reader.nextInt();
      reader.nextLine();
      
      int choosen = UnoDeck.getNthPlayerCard(player, choice);
      if(UnoDeck.canUse(choosen))
      {
        return choosen;
      }
    }
    //return -1;
  }
  
  public static int getNextPlayer(int player)
  {
    int nextPlayer = (player + direction + numPlayers) % numPlayers;
    if(nextPlayer == 0)
      nextPlayer = numPlayers;
    return nextPlayer;
  }
}