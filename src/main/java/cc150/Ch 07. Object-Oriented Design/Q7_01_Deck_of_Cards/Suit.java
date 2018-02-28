package Q7_01_Deck_of_Cards;

/**
 * 这个枚举类型代表了一张扑克牌，注意，与Card相区分，Suit只是代表扑克牌本身，与规则无关
 */
public enum Suit {
	Club (0), //梅花
	Diamond (1), //方块
	Heart (2), //红桃
	Spade (3); //黑桃
	
	private int value; //扑克牌上的值
	private Suit(int v) {
		value = v;
	}
	
	public int getValue() {
		return value;
	}
	
	public static Suit getSuitFromValue(int value) {
		switch (value) {
		case 0:
			return Suit.Club;
		case 1:
			return Suit.Diamond;
		case 2:
			return Suit.Heart;
		case 3: 
			return Suit.Spade;
		default:
				return null;
		}
	}
}
