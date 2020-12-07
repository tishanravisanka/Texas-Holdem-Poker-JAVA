import java.util.Random;

public class Deck{
	
    private Card[] cards = new Card[52];
    int cardCounter=0;
    
    // default constructor to create a deck of 52 cards
    public Deck(){
        int i = 0;
        for (int j=0; j<4; j++)
            for (int k=0; k<13;k++)
                cards[i++] = new Card(k, j);    
    }
    
    // shuffle the deck
    public void shuffleDeck(){
        Random random = new Random();
        for (int i=0;i<cards.length;i++)
            swapCards(i, (i + random.nextInt(cards.length-i)));
    }
    
    // swap cards to shuffle the deck.
    private void swapCards(int i, int change){      
        Card temp = cards[i];
        cards[i] = cards[change];
        cards[change] = temp;
    }
    
    // get a specified card from deck
    public Card getCard(){
        return cards[cardCounter++];
    }
    
    // cutting the deck to 2 pieces
    public void cutDeck(){
        Deck tempDeck = new Deck();
        Random random = new Random();
        int cutNum = random.nextInt(52);
        for (int i=0;i<cutNum;i++)
            tempDeck.cards[i] = this.cards[52-cutNum+i];            
        
        for (int j=0;j<52-cutNum;j++)
            tempDeck.cards[j+cutNum] = this.cards[j];           
        
        this.cards = tempDeck.cards;
    }

    
}