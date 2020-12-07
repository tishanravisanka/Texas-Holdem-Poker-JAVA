import java.util.Arrays;

public class checkRank { 
    
	private Card[] allCards = new Card[7];
    String result = "";
    int handScore = 0;

    public int getHandScore(){
        return handScore;
    }
    protected void addCard(Card card, int i){
        allCards[i] = card;
    }

    protected void sortByRank(){
        Arrays.sort(allCards, new rankComparator());
    }

    protected void sortByRankThenSuit(){
        Arrays.sort(allCards, new rankComparator());
        Arrays.sort(allCards, new suitComparator());
    }

    protected String evaluateHand(){
        String handResult = new String();
        short[] rankCounter = new short[13];
        short[] suitCounter = new short[4];

        // initializations
        for (int i=0;i<rankCounter.length;i++)
            rankCounter[i] =0;

        for (int i=4;i<suitCounter.length;i++)
            suitCounter[i] = 0;

        // Loop through sorted cards and total ranks
        for(int i=0; i<allCards.length;i++){
            rankCounter[ allCards[i].getRank() ]++;
            suitCounter[ allCards[i].getSuit() ]++;
        }

        //sort cards for evaluation
        this.sortByRankThenSuit();

        handResult = evaluateRoyal(rankCounter, suitCounter);					// check for royal flush

        if (handResult == null || handResult.length() == 0)						// check for straight flush
            handResult = evaluateStraightFlush(rankCounter, suitCounter);

        if (handResult == null || handResult.length() == 0)						// check for four of a kind
            handResult = evaluateFourOfAKind(rankCounter);

        if (handResult == null || handResult.length() == 0)						// check for full house
            handResult = evaluateFullHouse(rankCounter);

        if (handResult == null || handResult.length() == 0)						// check for flush
            handResult = evaluateFlush(rankCounter, suitCounter);

        if (handResult == null || handResult.length() == 0){					// check for straight
            
            // re-sort by rank
            this.sortByRank();
            handResult = evaluateStraight(rankCounter);
        }

        if (handResult == null || handResult.length() == 0)						// check for three of a kind
            handResult = evaluateThreeOfAKind(rankCounter);

        if (handResult == null || handResult.length() == 0)						// check for two pair
            handResult = evaluateTwoPair(rankCounter);

        if (handResult == null || handResult.length() == 0)						// check for one pair
            handResult = evaluateOnePair(rankCounter);

        if (handResult == null || handResult.length() == 0)						// check for highCard
            handResult = evaluateHighCard(rankCounter);

        return handResult;
    }
     

    private String evaluateRoyal(short[] rankCounter, short[] suitCounter){
         
        // Check for Royal Flush (10 - Ace of the same suit).    
        if ((rankCounter[9] >= 1 && rankCounter[10] >= 1 && rankCounter[11] >= 1 && rankCounter[12] >= 1 && rankCounter[0] >= 1)
                && (suitCounter[0] > 4 || suitCounter[1] > 4 || suitCounter[2] > 4 || suitCounter[3] > 4)){

            // now loop through records for an ace and check subsequent cards. 
            royalSearch:
                    // Ace must be in position 0, 1 or 2
                for (int i=0;i<3;i++){
                    if (allCards[i].getRank() == 0)
                        // checking for remaining 4 cards could start at position 1,
                        for (int j=1;j<4-i;j++)
                            if ((allCards[i+j].getRank() == 9 && 
                                    allCards[i+j+1].getRank() == 10 &&
                                    allCards[i+j+2].getRank() == 11 &&
                                    allCards[i+j+3].getRank() == 12) 
                                    &&
                                    (allCards[i].getSuit() == allCards[i+j].getSuit() &&
                                    allCards[i].getSuit() == allCards[i+j+1].getSuit() &&
                                    allCards[i].getSuit() == allCards[i+j+2].getSuit() &&
                                    allCards[i].getSuit() == allCards[i+j+3].getSuit())){
                                        
                                        handScore = 100;
                                        result = "Royal Flush!! Suit: " + Card.suitAsString(allCards[i].getSuit());
                                        break royalSearch;              
                            }                               
                }
        }       
        return result;
    }

    

    // Checking for straight flush (5 consecutive cards)
    private String evaluateStraightFlush(short[] rankCounter, short[] suitCounter){

        if (suitCounter[0] > 4 || suitCounter[1] > 4 ||
                suitCounter[2] > 4 || suitCounter[3] > 4){

            // start in reverse to get the highest value straight flush
            for (int i=allCards.length-1;i>3;i--){
                if ((allCards[i].getRank()-1 == allCards[i-1].getRank() && 
                        allCards[i].getRank()-2 == allCards[i-2].getRank() &&
                        allCards[i].getRank()-3 == allCards[i-3].getRank() &&
                        allCards[i].getRank()-4 == allCards[i-4].getRank()) 
                        &&
                        (allCards[i].getSuit() == allCards[i-1].getSuit() &&
                        allCards[i].getSuit() == allCards[i-2].getSuit() &&
                        allCards[i].getSuit() == allCards[i-3].getSuit() &&
                        allCards[i].getSuit() == allCards[i-4].getSuit())){
                            // Found royal flush, break and return.
                            handScore = 99;
                            result = "Straight Flush!! " + Card.rankAsString(allCards[i].getRank()) + " high of " + Card.suitAsString(allCards[i].getSuit());
                            break;
                }
            }
        }
        return result;
    }

    // checking for four of a kind
    private String evaluateFourOfAKind(short[] rankCounter){

        for (int i=0;i<rankCounter.length;i++)
            if (rankCounter[i] == 4){
                handScore = 98;
                result = "Four of a Kind, " + Card.rankAsString(i) +"'s";
                break;
            }  
        return result;
    }

    // checking full house(3 of  of one rank and two of other) 
    private String evaluateFullHouse(short[] rankCounter){
        short threeOfKindRank = -1;
        short twoOfKindRank = -1;

        for (int i=rankCounter.length;i>0;i--){
            if ((threeOfKindRank < (short)0) || (twoOfKindRank < (short)0)){
                if ((rankCounter[i-1]) > 2)
                    threeOfKindRank = (short) (i-1);                  
                else if ((rankCounter[i-1]) > 1)
                    twoOfKindRank = (short)(i-1);
            }
            else
                break;
        }

        if ((threeOfKindRank >= (short)0) && (twoOfKindRank >= (short)0)){
            handScore = 97;
            result = "Full House: " + Card.rankAsString(threeOfKindRank) + "'s full of " + Card.rankAsString(twoOfKindRank) + "'s";
        }

        return result;
    }

    // checking flush (5 cards of same suit)
    private String evaluateFlush(short[] rankCounter, short[] suitCounter){

        // verify at least 1 suit has 5 cards or more.
        if (suitCounter[0] > 4 || suitCounter[1] > 4 || suitCounter[2] > 4 || suitCounter[3] > 4){

            for (int i=allCards.length-1;i>3;i--){
                if (allCards[i].getSuit() == allCards[i-1].getSuit() &&
                        allCards[i].getSuit() == allCards[i-2].getSuit() &&
                        allCards[i].getSuit() == allCards[i-3].getSuit() &&
                        allCards[i].getSuit() == allCards[i-4].getSuit()){
                            // Found royal flush, break and return.
                            result = "Flush!! " + Card.rankAsString(allCards[i].getRank()) + " high of " + Card.suitAsString(allCards[i].getSuit());
                            handScore = 96;
                            break;
                }
            }           
        }


        return result;
    }

    // check for straight (just 5 consecutive cards)
    private String evaluateStraight(short[] rankCounter){

        // loop through rank array to check for 5 consecutive
        for (int i=rankCounter.length;i>4;i--){
            if ((rankCounter[i-1] > 0) &&
                    (rankCounter[i-2] > 0) &&
                    (rankCounter[i-3] > 0) &&
                    (rankCounter[i-4] > 0) &&
                    (rankCounter[i-5] > 0)){
                        handScore = 95;
                        result = "Straight " + Card.rankAsString(i-1) + " high";
                        break;
            }
        }
        return result;
    }

    // 3 cards of same rank.
    private String evaluateThreeOfAKind(short[] rankCounter){

        // loop through rank array to check for 5 consecutive
        for (int i=rankCounter.length;i>0;i--){
            if (rankCounter[i-1] > 2){
                handScore = 94;
                result = "Three of a Kind " + Card.rankAsString(i-1) + "'s";
                break;
            }
        }
        return result;
    }

    // check for two pairs
    private String evaluateTwoPair(short[] rankCounter){

        short firstPairRank = -1;
        short secondPairRank = -1;

        for (int i=rankCounter.length;i>0;i--){
            if ((firstPairRank < (short)0) || (secondPairRank < (short)0)){             
                if (((rankCounter[i-1]) > 1) && (firstPairRank < (short)0))
                    firstPairRank = (short) (i-1);                    
                
                else if ((rankCounter[i-1]) > 1)
                    secondPairRank = (short)(i-1);
            }
            else
                break;
        }

        // populate output
        if ((firstPairRank >= (short)0) && (secondPairRank >= (short)0)){
            if (secondPairRank == (short)0){
                // swap places so aces show first as highest pair
                handScore = 93;
                result = "Two Pair: " + Card.rankAsString(secondPairRank) + "'s and " + Card.rankAsString(firstPairRank) + "'s";
            }
            else{
                handScore = 93;
                result = "Two Pair: " + Card.rankAsString(firstPairRank) + "'s and " + Card.rankAsString(secondPairRank) + "'s";
            }           
        }

        return result;
    }

    // one is is two cards of the same rank.
    private String evaluateOnePair(short[] rankCounter){

        for (int i=rankCounter.length;i>0;i--){
            if((rankCounter[i-1]) > 1){
                handScore = 92;
                result = "One Pair: " + Card.rankAsString(i-1) + "'s";    
                break;
            }
        }
        return result;
    }

    // check for high card (highest card out of the 7 possible cards)
    private String evaluateHighCard(short[] rankCounter){

        for (int i=rankCounter.length;i>0;i--){
            if((rankCounter[i-1]) > 0){
                handScore = 91;
                result = "High Card: " + Card.rankAsString(i-1);
                break;
            }
        }
        return result;
    }

}  
    