import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner; 

/* In each round only the winner will receive points  */

public class Game {

    public static void main(String[] args) {

    	// variable declaration
        int playerAmount = 3;
        boolean allFold = false;

        // initialize players
        Player[] player = new Player[playerAmount];
        for (int i = 0; i < playerAmount; i++) 
            player[i] = new Player();
        
        // go through 10 rounds
        for (int rounds = 0; rounds < 10; rounds++) {
            
            Deck mainDeck = new Deck();
            Board board = new Board();
            
            System.out.println("\n\n ____________________________________________________");
            System.out.println("|                     Round :- " + (rounds + 1) + "\t\t     |");
            System.out.println("|____________________________________________________|\n");

            // shuffle deck
            mainDeck.shuffleDeck();
            // cut deck
            mainDeck.cutDeck();

            // deal (give hole cards to players)
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < playerAmount; j++)
                    player[j].setCard(mainDeck.getCard(), i);

    
            // go through 4 rounds
            for (int turn = 0; turn < 4; turn++) {
            	System.out.println("\n------------------------------------------------------");
            	if (player[0].getAct() != 1)
                    player[0].printPlayerCards(0);

                if(turn==1) {
                    // burning (place one card before flop) 
                    mainDeck.getCard();
                    // deal flop
                    for (int i = 0; i < 3; i++) 
                        board.setCommunityCard(mainDeck.getCard());
                    
                }
                else if(turn==2) {
                    // Burn one card before turn
                    mainDeck.getCard();
                    // deal turn
                    board.setCommunityCard(mainDeck.getCard());

                }
                else if(turn==3) {
                    // Burn one card before river
                    mainDeck.getCard();
                    // deal river
                    System.out.println("turn"+turn);
                    board.setCommunityCard(mainDeck.getCard());

                }

                //print board
                if(turn!=0)
                    board.printBoard();

                // get actions from the players
                for (int i = 0; i < 3; i++) {
                	if(i==0 && player[0].getAct() != 1) {
                		boolean flag = false;
                		int choice=0;
                		do {
                            if(flag == true)
                                System.out.println("\n\n!!! Invalid Selection  \n");
                            try{
                                @SuppressWarnings("resource")
        						Scanner myObj = new Scanner(System.in); // Create a Scanner object
                                if(turn==0)
                                	System.out.print("\n\t1 - FOLD\n\t2 - CALL\n\t3 - RAISE\n  Select Option : ");
                                else
                                	System.out.print("\n\t1 - FOLD\n\t2 - CALL\n\t3 - RAISE\n\t4 - CHECK\n  Select Option : ");
                                choice = myObj.nextInt();                        
                            }
                            catch(InputMismatchException e) {
                            	System.exit(1);
                            }
                            flag = true;
                            player[i].setAct(choice);
                            if(turn == 0 && choice == 4)
                            	choice=-1;
                        }while (choice != 1 && choice != 2 && choice != 3 &&  choice != 4);
                	}
                	else {
                		// get random actions from the other two users
	                    Random rand = new Random();
	                    int num = rand.nextInt(15);
	                    if (player[i].getAct() != 1) {
	                        if (num < 1) 
	                            player[i].setAct(1);
	                        else if (num < 6 ) 
	                            player[i].setAct(2);
	                        else if (num < 11 && turn != 0) 
	                            player[i].setAct(4);
	                        else
	                        	player[i].setAct(3);
	                    }
                        
                    }
                }

                // checking for folding of two players
                if (player[0].getAct() == 1 && player[1].getAct() == 1 && player[2].getAct() == 1) {
                    player[2].addScore();
                    allFold = true;
                    player[2].setAct(2);
                    System.out.println("Player 1 Has FOLDED");
                    System.out.println("\n\n\t!!! All Folded Player 3 WON !!!  \n\n");
                } else if (player[1].getAct() == 1 && player[0].getAct() == 1) {
                    player[2].addScore();
                    allFold = true;
                    System.out.println("\nPlayer 1 Has FOLDED");
                    System.out.println("\n\n\t!!! All Folded Player 3 WON !!!  \n\n");
                } else if (player[2].getAct() == 1 && player[1].getAct() == 1) {
                	System.out.println("\nPlayer 1 Has FOLDED");
                	System.out.println("\nPlayer 2 Has FOLDED");
                    player[0].addScore();
                    allFold = true;
                    System.out.println("\n\n\t!!! All Folded You WON !!!  \n\n");
                } else if (player[2].getAct() == 1 && player[0].getAct() == 1) {
                    player[1].addScore();
                    allFold = true;
                    System.out.println("\nPlayer 3 Has FOLDED");
                    System.out.println("\n\n\t!!! All Folded Player 2 WON !!!  \n\n");
                }
                
                if (!allFold) {
                    // display actions of the players
                    for (int i = 0; i < 3; i++) {
                    	if(i==0) {
                    		if (player[i].getAct() == 1) {
                                System.out.println("\n\n\t!!! You have FOLDED your hand !!!  \n\n");
                                player[0].setAct(1);
                            } 
                            else if (player[i].getAct() == 2) 
                                System.out.println("\n\n\t!!! You have CALLED !!!  \n\n");
                            else if (player[i].getAct() == 3) 
                                System.out.println("\n\n\t!!! You have RAISED !!!  \n\n");
                    		else 
                    			System.out.println("\n\n\t!!! You have CHECKED !!!  \n\n");
                    	}
                    	else {
	                        if (player[i].getAct() == 1) 
	                            System.out.println("Player " + (i + 1) + " Has FOLDED");
	                        else if (player[i].getAct() == 2) 
	                            System.out.println("Player " + (i + 1) + " Has CALLED");
	                        else if(player[i].getAct() == 3)
	                            System.out.println("Player " + (i + 1) + " Has RAISED");
	                        else 
	                            System.out.println("Player " + (i + 1) + " Has CHECKED");
                    	}
                    }

                } 
                else 
                    break;
            }
            
	            if(!allFold) {	            	
		            // print all players cards
		            System.out.println("The player cards are the following:\n");
		            for (int i = 0; i < playerAmount; i++) 
		            	if(player[i].getAct() != 1)
		              		player[i].printPlayerCards(i);
		            
		
		
		            // checking hand comparison
		            for (int i = 0; i < playerAmount; i++) {
		                checkRank handToEval = new checkRank();
		                if(player[i].getAct() != 1) {
		                	
			                // sending hole cards           
			                for (int j = 0; j < 2; j++) 
			                    handToEval.addCard(player[i].getCard(j), j);
			
			                // sending community cards
			                for (int j = 2; j < 7; j++) 
			                    handToEval.addCard(board.getCommunityCard(j - 2), j);
			                
			                // getting rankings and the score of the hand
			                if(i==0) {
			                	System.out.println("Your hand rank    : " + handToEval.evaluateHand());
					            player[i].handresult = handToEval.getHandScore();
			                }
			                else {
				            	System.out.println("Player " + (i + 1) + " hand rank: " + handToEval.evaluateHand());
				            	player[i].handresult = handToEval.getHandScore();
			                }
		                }
		            }
		
		            // getting the maximum score from all the hands
		            int max = 0, temp = 0;
		            for (int x = 0; x < playerAmount; x++) 
		                if (player[x].getAct() != 1) 
		                    if (temp < player[x].handresult) {
		                        temp = player[x].handresult;
		                        max = x;
		                    }
		            if(max==0) 
		            	System.out.println("\n\tYou won the round");
		            else
		            	System.out.println("\n\tPlayer " + (max + 1) + " won the round");
		            
		            player[max].addScore();
	            }
	            
            allFold = false;
            // reset player data
            for (int i = 0; i < playerAmount; i++) 
            	player[i].setAct(-1);

        }
        
        // displaying final result
        System.out.println("\n\n=====================================================");
        System.out.println("\nFinal result after 10 rounds \n");
        System.out.println("Scores :\n\tYour \t - "+ player[0].getScore());
        
        for (int i = 1; i < playerAmount; i++) 
        	System.out.println("\n\tPlayer " + (i+1) + " - "+ player[i].getScore());
        if (player[0].getScore() >= player[1].getScore() && player[0].getScore() >= player[2].getScore()) 
            System.out.println("\nYou got the overall win !!!");
        
        else if (player[0].getScore() <= player[1].getScore() && player[2].getScore() <= player[1].getScore()) 
            System.out.println("\nPlayer 2 got the overall win !!!");
        
        else if (player[2].getScore() >= player[1].getScore() && player[2].getScore() >= player[1].getScore()) 
            System.out.println("\nPlayer 3 got the overall win !!!");
        System.out.println("\n=====================================================");


    }

}