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

public class UnoFinal
{
  static Scanner reader = new Scanner(System.in);
  static Random generator = new Random();
  
  
  public static void main(String[] args){
    UnoDeck.init();
    UnoMenu.welcome();
    if(UnoPlayer.numPlayers > 16)
    {
      System.out.println("Too many players, quitting...");
      return;
    }
    for(int i = 1; i <= UnoPlayer.numPlayers; i++)
    {
      UnoDeck.dealHand(i);
    }
    
    UnoPlayer.currentPlayer = 0;
    while(true)
    {
      for(int i = 1; i <= UnoPlayer.numPlayers; i++)
      {
        if(UnoPlayer.hasWon(i))
        {
          System.out.println("Player " + i + " has won!");
          System.out.println("Thanks for playing Uno!");
          return;
        }
      }
      UnoPlayer.currentPlayer = UnoPlayer.getNextPlayer(UnoPlayer.currentPlayer);
      if(UnoPlayer.currentPlayer == 0)
      {
        UnoPlayer.currentPlayer = UnoPlayer.numPlayers;
      }
      UnoPlayer.playerTurn(UnoPlayer.currentPlayer);
    }
    
    
  }
  
  public static void clearScreen()
  {
    for(int i = 0; i < 64; i++)
      System.out.println();
  }
  
}