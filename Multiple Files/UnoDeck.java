
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class UnoDeck
{
  static Scanner reader = new Scanner(System.in);
  static Random generator = new Random();
  
  static String[] deck = new String[108];
  static int[] cardLocation = new int[108];
  
  static int justDiscarded;
  static int justDiscardedColor;
  static int deckSize = 108;
  static final int NUM_CARDS = 108;
  static String[] colors = {"Red", "Yellow", "Green", "Blue"};
  
  public static void init()
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
  public static void reshuffle()
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
  
  public static int drawCard(int player){
    
    if(getNumPlayerCards(0) == 0)
    {
      reshuffle();
    }
    
    int randomCard = generator.nextInt(getNumPlayerCards(0));
    cardLocation[getNthPlayerCard(0, randomCard + 1)] = player;
    return player;
  }
  
  public static void dealHand(int player)
  {
    for(int i = 0; i < 7; i++)
    {
      drawCard(player);
    }
  }
  
  public static void printHand(int player)
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
  
  public static void activateCard(int card)
  {
    int color = card / 25;
    int cardType = card % 25;
    if(card >= 100)
    {
      if(UnoPlayer.isHuman[UnoPlayer.currentPlayer])
      {
        color = UnoPlayer.getPlayerWildColor(UnoPlayer.currentPlayer);
      }
      else
      {
        color = UnoPlayer.getCompWildColor(UnoPlayer.currentPlayer);
      }
      if(card >= 104)
      {
        drawN(UnoPlayer.getNextPlayer(UnoPlayer.currentPlayer), 4);
        UnoPlayer.currentPlayer = UnoPlayer.getNextPlayer(UnoPlayer.currentPlayer);
      }
    }
    
    else if(cardType > 18) // skip, reverse or +2
    {
      if(cardType <= 20) // skip
      {
        UnoPlayer.currentPlayer = UnoPlayer.getNextPlayer(UnoPlayer.currentPlayer);
      }
      else if(cardType <= 22) // reverse
      {
        UnoPlayer.direction *= -1;
      }
      else if(cardType <= 24) // +2 , could be an else statement
      {
        drawN(UnoPlayer.getNextPlayer(UnoPlayer.currentPlayer), 2);
        UnoPlayer.currentPlayer = UnoPlayer.getNextPlayer(UnoPlayer.currentPlayer);
      }
    }
    
    cardLocation[card] = -1;
    justDiscarded = card;
    justDiscardedColor = color;
  }
  
  public static int getNumPlayerCards(int player)
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
  
  public static int getNthPlayerCard(int player, int n)
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
    
    System.out.println("ERROR! Tell developers: " + index);
    return -1;
  }
  
  public static boolean canUse(int card)
  {
    
    if(card >= 100)
    {
      /*if(card >= 104) // draw 4
       {
       drawN(UnoPlayer.getNextPlayer(UnoPlayer.currentPlayer), 4);
       UnoPlayer.currentPlayer = UnoPlayer.getNextPlayer(UnoPlayer.currentPlayer);
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
  
  public static boolean typeMatch(int a, int b) // it should only take values from 0-24
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
  
  
  public static boolean checkValidHand(int player){
    for (int i = 0; i <= 107; i++){
      if (cardLocation[i] == player){
        if (canUse(i) == true){
          return true;
        }
      }
    }
    return false;
  }
  
  public static void drawN(int nextPlayer, int n)
  {
    
    System.out.println("Player " + nextPlayer + " draws " + n + " cards");
    for(int i = 0; i < n; i++)
    {
      drawCard(nextPlayer);
    }
    System.out.println("Player " + nextPlayer + " has " + getNumPlayerCards(nextPlayer) + " cards");
    reader.nextLine();
  }
}