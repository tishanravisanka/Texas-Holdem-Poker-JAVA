public class Board {
    private Card[] board = new Card[5];
    int cardCount =0;

    // get a card from the deck and put in to table
    public void setCommunityCard(Card card){
        this.board[cardCount] = card;
        cardCount++;
    }

    public Card getCommunityCard(int cardNum){
        return this.board[cardNum];
    }

    // print all community cards
    public void printBoard(){
        System.out.println("Cards on the board :");
        for(int i =0; i<cardCount;i++)
            System.out.println(i+1 + ": " + getCommunityCard(i).printCard());
        System.out.println("\n");
    }

}