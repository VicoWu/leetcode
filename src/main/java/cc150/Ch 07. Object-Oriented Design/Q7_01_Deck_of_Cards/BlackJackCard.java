package Q7_01_Deck_of_Cards;

/**
 * 21点游戏的Card实现
 */
public class BlackJackCard extends Card {
	public BlackJackCard(int c, Suit s) {
		super(c, s);
	}

	//实现了Card的抽象方法value
	public int value() {
		if (isAce()) { //如果是 Ace
			return 1; //返回1
		} else if (faceValue >= 11 && faceValue <= 13) { // Face card
			return 10;//如果是J Q K,那么面值都是10
		} else { // Number card
			return faceValue; //其它的如2,3,...,10，就是本身的值
		}
	}
	
	public int minValue() {
		if (isAce()) { // Ace
			return 1; 
		} else {
			return value();
		}
	}
	
	public int maxValue() {
		if (isAce()) { // Ace
			return 11; 
		} else {
			return value();
		}
	}
	
	public boolean isAce() { 
		return faceValue == 1;
	}
	
	public boolean isFaceCard() {
		return faceValue >= 11 && faceValue <= 13;
	}
}
