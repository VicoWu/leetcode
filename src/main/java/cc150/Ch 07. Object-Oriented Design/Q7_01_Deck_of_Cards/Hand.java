package Q7_01_Deck_of_Cards;

import java.util.ArrayList;

/**
 * Hand代表了一个选手
 * @param <T>
 */
public class Hand <T extends Card> {
	protected ArrayList<T> cards = new ArrayList<T>(); //选手手里的牌
	
	public int score() {
		int score = 0;
		for (T card : cards) {
			score += card.value();
		}
		return score;
	}
	
	public void addCard(T card) {
		cards.add(card);
	}	
	
	public void print() {
		for (Card card : cards) {
			card.print();
		}
	}	
}
