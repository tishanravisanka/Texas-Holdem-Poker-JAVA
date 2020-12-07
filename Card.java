import java.util.*;

public class Card{
    int rank, suit;
    static String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};
    static String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    

    // default constructor to initialize
    public Card(int rank, int suit){
        this.rank = rank;
        this.suit = suit;
    }

    // getters and setters
    public int getRank(){
        return rank;
    }
    public void setRank(short rank){
        this.rank = rank;
    }
    
    public int getSuit(){
        return suit;
    }
    public void setSuit(short suit){
        this.suit = suit;
    }
    

    public static String rankAsString(int rk){
        return ranks[rk];
    }

    public static String suitAsString(int su){
        return suits[su];
    }

    public @Override String toString(){
        return rank + " of " + suit;
    }

    // display a card 
    public String printCard(){
        return ranks[rank] + " of " + suits[suit];
    }

    // ckeck if two cards are the same 
    public static boolean sameCard(Card card1, Card card2){
        return (card1.rank == card2.rank && card1.suit == card2.suit);
    }   

}

class rankComparator implements Comparator<Object>{
    public int compare(Object card1, Object card2) throws ClassCastException{
        // verify two Card objects are passed in
        if (!((card1 instanceof Card) && (card2 instanceof Card))){
            throw new ClassCastException("A Card object was expected.  Parameter 1 class: " + card1.getClass() 
                    + " Parameter 2 class: " + card2.getClass());
        }

        int rank1 = ((Card)card1).getRank();
        int rank2 = ((Card)card2).getRank();

        return rank1 - rank2;
    }
}

class suitComparator implements Comparator<Object>{
    public int compare(Object card1, Object card2) throws ClassCastException{
        // verify two Card objects are passed in
        if (!((card1 instanceof Card) && (card2 instanceof Card))){
            throw new ClassCastException("A Card object was expected.  Parameter 1 class: " + card1.getClass() 
                    + " Parameter 2 class: " + card2.getClass());
        }

        int suit1 = ((Card)card1).getSuit();
        int suit2 = ((Card)card2).getSuit();

        return suit1 - suit2;
    }
}