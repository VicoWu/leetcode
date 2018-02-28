package Q7_01_Deck_of_Cards;

import CtCILibrary.AssortedMethods;

import java.util.ArrayList;

public class Deck <T extends Card> {
	private ArrayList<T> cards; //所有的牌
	private int dealtIndex = 0; // marks first undealt card 已经打出去的牌
	
	public Deck() {
	}
	
	public void setDeckOfCards(ArrayList<T> deckOfCards) {
		cards = deckOfCards;
	} //初始化所有的 牌
	
	public void shuffle() { //洗牌
		for (int i = 0; i < cards.size(); i++) {
			int j = AssortedMethods.randomIntInRange(i, cards.size() - i - 1);
			T card1 = cards.get(i);
			T card2 = cards.get(j);
			cards.set(i, card2);
			cards.set(j, card1);
		}
	}
	
	public int remainingCards() {
		return cards.size() - dealtIndex;
	}
	
	public T[] dealHand(int number) {
		if (remainingCards() < number) {
			return null;
		}
		
		T[] hand = (T[]) new Card[number];
		int count = 0;
		while (count < number) {
			T card = dealCard();
			if (card != null) {
				hand[count] = card;
				count++;
			}
		}
		
		return hand;
	}
	
	public T dealCard() {
		if (remainingCards() == 0) {
			return null;
		}
		
		T card = cards.get(dealtIndex);
		card.markUnavailable();
		dealtIndex++;
		
		return card;		
	}
	
	public void print() {
		for (Card card : cards) {
			card.print();
		}
	}
}
