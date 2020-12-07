public class Player {

	private Card[] holeCards = new Card[2];
	int act = -1; // 1-fold 2-call 3-raise 4-check
	int handresult = 0;
	int score = 0;

	// score incrementing
	public void addScore() {
		score++;
	}

	//getters and setters
	public int getScore() {
		return score;
	}

	public void setAct(int act) {
		this.act = act;
	}

	public int getAct() {
		return act;
	}

	public void setCard(Card card, int cardNo) {
		holeCards[cardNo] = card;
	}

	public Card getCard(int cardNum) {
		return holeCards[cardNum];
	}

	// display hole cards of player
	public void printPlayerCards(int playerNumber) {
		if (playerNumber != 0)
			System.out.println("Player " + (playerNumber + 1) + " hole cards :");
		else
			System.out.println("Your cards :");
		for (int i = 0; i < 2; i++) {
			System.out.println("\t" + holeCards[i].printCard());
		}
		System.out.println("\n");
	}
}