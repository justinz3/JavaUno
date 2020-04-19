/*
Uno 1.0.0
Authors: Bill Chen and Justin Zhu
No known issues
Text based Uno
Missing GUI, customization features, and smarter AI
No borrowed code
*/

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
public class Uno
{
    static String[] deck = new String[108];
    static int[] cardLocation = new int[108];
    static Scanner reader = new Scanner(System.in);
    static Random generator = new Random();
    static int justDiscarded;
    static int justDiscardedColor;
    static int deckSize = 108;
    static final int NUM_CARDS = 108;
    static boolean[] isHuman = new boolean[100];
    static int numPlayers;
    static int currentPlayer;
    static String[] playerNames = new String[100];
    static int direction = 1;
    static String[] colors = {"Red", "Yellow", "Green", "Blue"};
    
    public static void main(String[] args){
      init();
      welcome();
      if(numPlayers >= 16)
      {
        System.out.println("Too many players, quitting...");
        return;
      }
      for(int i = 1; i <= numPlayers; i++)
      {
        dealHand(i);
      }
      
      currentPlayer = 0;
      while(true)
      {
        for(int i = 1; i <= numPlayers; i++)
        {
          if(hasWon(i))
          {
            System.out.println("Player " + i + " has won!");
            System.out.println("Thanks for playing Uno!");
            return;
          }
        }
        currentPlayer = getNextPlayer(currentPlayer);
        if(currentPlayer == 0)
        {
          currentPlayer = numPlayers;
        }
        playerTurn(currentPlayer);
      }
      
      
    }
    
    private static void welcome(){
        while (true){
            System.out.println("Welcome to Uno");
            System.out.println("Do you want to start(1), view the rules(2) or see the about page(3)?");
            int input = reader.nextInt();
            reader.nextLine();
            if (input == 1){
                System.out.println("How many humans are there?");
                numPlayers = reader.nextInt();
                reader.nextLine();
                for(int i = 1; i <= numPlayers; i++)
                {
                  isHuman[i] = true;
                }
                System.out.println("How many computers do you want?");
                numPlayers += reader.nextInt();
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
    private static void init()
    {
      for (int i = 0; i <= 107; i++){
            //Gives each card a color that corresponds
            String color;
            if (i <= 24){
                color = "Red ";
            }else if (i <= 49){
                color = "Yellow ";
            }else if (i <= 74){
                color = "Green ";
            }else if (i <= 99){
                color = "Blue ";
            }else{
                color = "Black ";
            }
            
            //Adds a string with the color and number to the array for the deck
            if (i <= 9){
                deck[i] = color + i;
            }else if (i <= 18){
                deck[i] = color + (i-9);
            }else if (i <= 20){
                deck[i] = color + "Skip";
            }else if (i <= 22){
                deck[i] = color + "Reverse";
            }else if (i <= 24){
                deck [i] = color + "Draw 2";
            }else if (i <= 34){
                deck[i] = color + (i-25);
            }else if (i <= 43){
                deck[i] = color + (i-34);
            }else if (i <= 45){
                deck[i] = color + "Skip";
            }else if (i <= 47){
                deck[i] = color + "Reverse";
            }else if (i <= 49){
                deck [i] = color + "Draw 2";
            }else if (i <= 59){
                deck[i] = color + (i-50);
            }else if (i <= 68){
                deck[i] = color + (i-59);
            }else if (i <= 70){
                deck[i] = color + "Skip";
            }else if (i <= 72){
                deck[i] = color + "Reverse";
            }else if (i <= 74){
                deck [i] = color + "Draw 2";
            }else if (i <= 84){
                deck[i] = color + (i-75);
            }else if (i <= 93){
                deck[i] = color + (i-84);
            }else if (i <= 95){
                deck[i] = color + "Skip";
            }else if (i <= 97){
                deck[i] = color + "Reverse";
            }else if (i <= 99){
                deck [i] = color + "Draw 2";
            }else if (i <= 103){
                deck[i] = color + "Wild";
            }else{
                deck[i] = color + "Draw 4";
            }
            
            //Puts all of the cards into the deck to start
            cardLocation[i] = 0;
        }
      
      drawCard(-1);
      while(true)
      {
        justDiscarded = getNthPlayerCard(-1, 1);
        justDiscardedColor = justDiscarded / 25;
        
        if(justDiscarded < 100)
          break;
        cardLocation[justDiscarded] = 0;
      }
    }
    
    private static void drawN(int nextPlayer, int n)
    {
      
      System.out.println("Player " + nextPlayer + " draws " + n + " cards");
      for(int i = 0; i < n; i++)
      {
        drawCard(nextPlayer);
      }
      System.out.println("Player " + nextPlayer + " has " + getNumPlayerCards(nextPlayer) + " cards");
      reader.nextLine();
    }
    
    private static int getNextPlayer(int player)
    {
      int nextPlayer = (player + direction + numPlayers) % numPlayers;
      if(nextPlayer == 0)
        nextPlayer = numPlayers;
      return nextPlayer;
    }

    private static boolean canUse(int card)
    {
      
      if(card >= 100)
      {
        /*if(card >= 104) // draw 4
        {
          drawN(getNextPlayer(currentPlayer), 4);
          currentPlayer = getNextPlayer(currentPlayer);
        }*/
        return true;
      }
      
      boolean result = false;
      int cardType = card % 25, justDiscardedType = justDiscarded % 25;
      if(typeMatch(cardType, justDiscardedType)) // same type
        result = true;
      
      if(card / 25 == justDiscardedColor) // same color
      {
        result = true;
      }

      return result;
    }
    
    private static boolean typeMatch(int a, int b) // it should only take values from 0-24
    {
      if(a == 0 || b == 0) // zero
      {
        if(a == 0 && b == 0)
          return true;
        return false;
      }
      
      if(a > 18 || b > 18) // special cards
      {
        if(a > 18 ^ b > 18) // one is a special card and the other isn't
        {
          return false;
        }
        
        a -= 19;
        b -= 19;
        
        if(a / 2 == b / 2) // this is correct because cards of the same type are next to each other
          return true;
        return false;
      }
      
      // both cards are number cards 1-9
      a--;
      b--;
      a %= 9;
      b %= 9;
      // the above makes a and b in the range of 0-8
      
      return a == b;
    }
    
    private static void reshuffle()
    {
      System.out.println("The deck has run out of cards, the discard pile (except for the top card) will be reshuffled into the deck)");
      reader.nextLine();
      for(int i = 0; i < NUM_CARDS; i++)
      {
        if(cardLocation[i] == -1 && i != justDiscarded)
        {
          cardLocation[i] = 0;
        }
      }
      System.out.println("The deck now has " + getNumPlayerCards(0) + " cards");
      reader.nextLine();
    }
    
    private static int drawCard(int player){
      
      if(getNumPlayerCards(0) == 0)
      {
        reshuffle();
      }
      
      int randomCard = generator.nextInt(getNumPlayerCards(0)) + 1;
      cardLocation[getNthPlayerCard(0, randomCard)] = player;
      return player;
    }
    
    private static boolean checkValidHand(int player){
        for (int i = 0; i <= 107; i++){
            if (cardLocation[i] == player){
                if (canUse(i) == true){
                    return true;
                    }
            }
        }
        return false;
    }
    
    private static void dealHand(int player)
    {
      for(int i = 0; i < 7; i++)
      {
        drawCard(player);
      }
    }
    
    private static void printHand(int player)
    {
      int counter = 1;
      for(int i = 0; i < NUM_CARDS; i++)
      {
        if(cardLocation[i] == player)
        {
          System.out.println(counter++ + ") " + deck[i]);
        }
      }
    }
    
    private static void clearScreen()
    {
      for(int i = 0; i < 64; i++)
        System.out.println();
    }
    
    private static void playerTurn(int player)
    {
      System.out.println("Player " + player + "'s turn.");
      System.out.println("The card on the top of the discard pile is: " + deck[justDiscarded] + " and is color " + colors[justDiscardedColor] + " (Press Enter)");
      if(isHuman[player]) // it's a Human
      {
        printHand(player);
        if(checkValidHand(player) == true)
        {
          int chosen = getPlayerChoice(player);
          activateCard(chosen);
        }
        else
        {
          System.out.println("You didn't have any cards that work, Draw One");
          drawCard(player);
          reader.nextLine();
          System.out.println("Your current hand: ");
          printHand(player);
        }
      }
      
      else // it's a computer
      {
        System.out.println("Player " + player + " has " + getNumPlayerCards(player) + " cards");
        if(checkValidHand(player) == true)
        {
          int chosen = getCompChoice(player);
          System.out.println("Player " + player + " has played " + deck[chosen]);
          activateCard(chosen);
          
        }
        else
        {
          drawCard(player);
          System.out.println("Player " + player + " has has drawn a card");
        }
        System.out.println("Player " + player + " has " + getNumPlayerCards(player) + " cards");
      }
      reader.nextLine();
      
      if(isHuman[getNextPlayer(currentPlayer)])
      {
        clearScreen();
      }
    }
    
    private static void activateCard(int card)
    {
      int color = card / 25;
      int cardType = card % 25;
      if(card >= 100)
      {
        if(isHuman[currentPlayer])
        {
          color = getPlayerWildColor(currentPlayer);
        }
        else
        {
          color = getCompWildColor(currentPlayer);
        }
        if(card >= 104)
        {
          drawN(getNextPlayer(currentPlayer), 4);
          currentPlayer = getNextPlayer(currentPlayer);
        }
      }
      
      else if(cardType > 18) // skip, reverse or +2
      {
        if(cardType <= 20) // skip
        {
          currentPlayer = getNextPlayer(currentPlayer);
        }
        else if(cardType <= 22) // reverse
        {
          direction *= -1;
        }
        else if(cardType <= 24) // +2 , could be an else statement
        {
          drawN(getNextPlayer(currentPlayer), 2);
          currentPlayer = getNextPlayer(currentPlayer);
        }
      }
      
      cardLocation[card] = -1;
      justDiscarded = card;
      justDiscardedColor = color;
    }
    
    private static int getPlayerWildColor(int player)
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
        if(result > 0 && result <= 4)
          break;
      }
      result--;
      System.out.println("Player " + player + " has chosen the wild card to be " + colors[result]);
      return result; // we are using 0-3
    }
    
    private static int getCompWildColor(int player)
    {
      int chosen = generator.nextInt(4);
      System.out.println("Player " + player + " has chosen the wild card to be " + colors[chosen]);
      return chosen;
    }
    
    private static int getCompChoice(int player)
    {
      for(int i = 0; i < NUM_CARDS; i++)
      {
        if(cardLocation[i] == player && canUse(i))
        {
          return i;
        }
      }
      
      return -1;
    }
    
    private static int getNumPlayerCards(int player)
    {
      int numCards = 0;
      for(int i = 0; i < NUM_CARDS; i++)
      {
        if(cardLocation[i] == player)
        {
          numCards++;
        }
      }
      return numCards;
    }
    
    private static int getPlayerChoice(int player)
    {
      int numCards = getNumPlayerCards(player);
      
      while(true)
      {
        System.out.println("Enter the number to the left of the (valid) card you want to play:");
        int choice = reader.nextInt();
        
        int choosen = getNthPlayerCard(player, choice);
        if(canUse(choosen))
        {
          return choosen;
        }
      }
      //return -1;
    }
    
    private static int getNthPlayerCard(int player, int n)
    {
      int index = 0;
      for(int i = 0; i < NUM_CARDS; i++)
      {
        if(cardLocation[i] == player)
        {
          index++;
          if(index == n)
          {
            return i;
          }
        }
      }
      
      System.out.println("ERROR! Tell developers: " + index + " " + player + " " + n);
      return -1;
    }
    
    private static boolean hasWon(int player)
    {
      for(int i = 0; i < NUM_CARDS; i++)
      {
        if(cardLocation[i] == player)
        {
          return false;
        }
      }
      return true;
    }
}
