
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class UnoMenu
{
  static Scanner reader = new Scanner(System.in);
  static Random generator = new Random();
  
  public static void welcome(){
    while (true){
      System.out.println("Welcome to Uno");
      System.out.println("Do you want to start(1), view the rules(2) or see the about page(3)?");
      int input = reader.nextInt();
      reader.nextLine();
      if (input == 1){
        System.out.println("How many humans are there?");
        UnoPlayer.numPlayers = reader.nextInt();
        reader.nextLine();
        for(int i = 1; i <= UnoPlayer.numPlayers; i++)
        {
          UnoPlayer.isHuman[i] = true;
        }
        System.out.println("How many computers do you want?");
        UnoPlayer.numPlayers += reader.nextInt();
        reader.nextLine();
        break;
      }else if (input == 2){
        rules();
        break;
      }else if(input == 3){
        about();
        break;
      }
      System.out.println("Try again");
      
    }
  }
  
  private static void about()
  {
    System.out.println("About Uno");
    System.out.println("Uno is a card game");
    System.out.println("This is an incomplete version of Uno");
    System.out.println("In fact, we have given up on a 100% complete version of Uno");
    System.out.println("");
    System.out.println("Version: 1.0");
    System.out.println("");
    System.out.println("Credits:");
    System.out.println("\tAuthors:");
    System.out.println("\tBill Chen");
    System.out.println("\tJustin Zhu");
    System.out.println("(Enter any key to return to the menu)");
    reader.nextLine();
    welcome();
  }
  
  private static void rules(){
    System.out.println("Rules of Uno");
    System.out.println("Each player starts with 7 cards in hand");
    System.out.println("The top card of the deck is discarded at the start of the game");
    System.out.println("A player can only play one card per turn");
    System.out.println("The card played must be of the same color or number of the last card played");
    System.out.println("Black cards can be played on top of any card");
    System.out.println("A skip card skips the next player's turn");
    System.out.println("A reverse card reverses the order of the turns");
    System.out.println("A draw 2 card draws 2 cards for the next player and skips their turn");
    System.out.println("A wild card allows the player to switch the color to their choice");
    System.out.println("A draw 4 card acts the same as a wild card but also draws 4 cards for the next player and skips their turn");
    System.out.println("If a player is unable to play a valid card they must draw a card and forfeit their turn");
    System.out.println("If the deck is empty, the discard pile is shuffed back in and the game continues");
    System.out.println("The winner is the player who plays all of their cards");
    System.out.println("(Enter any key to return to the menu)");
    reader.nextLine();
    welcome();
  }
}