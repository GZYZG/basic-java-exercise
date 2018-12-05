package couponCollection;

public class CouponCollection{
	static int MAX_GET = 1000;
	
	public static void main(String[] args) {
		/*
		 * cards:用于表示一副52张的扑克牌
		 * alreadyget:表示已经得到的花色
		 * count:已经进行抽取的次数
		 */
		
		int[] cards = new int[52];
		int[] alreadyget = new int[] {-1,-1,-1,-1};
		int count= 0;
		int num= -1;
		int[] tempget = new int[] {-1,-1};
		//为每张牌编号为1至52
		for(int i = 0; i < cards.length; i++) {
			cards[i] = i+1;
			
		}
		
		for(count = 1; count <= MAX_GET; count++) {
			num = getRandomCard(cards);
			tempget = mapPoker2Num(num);
			if(tempget[0] == -1 || tempget[1] == -1) {
				continue;
			}
			if(alreadyget[tempget[0]-1] < 1 || alreadyget[tempget[0]-1] > 4) {
				alreadyget[tempget[0]-1] = tempget[1];
			}
			if ( isCollectionFinish(alreadyget) ) {
				break;
			}
			System.out.println(""+cards[0]);
		}
		
		printResult(alreadyget, count);
		
		return;
	}
	
	
	public static int[] mapPoker2Num(int num){
		/*
		 * num:需要进行映射的数字，范围是[1,52]
		 * return:返回一个一维的长度为2的整形数组info[2]:
		 * 	info[0]:数字属于花色的种类
		 *  info[1]:数字对应的牌面值
		 *  若num不在[1,52]范围内则返回{-1,-1}
		 */
		if( num < 1 || num > 52) {
			return new int[] {-1, -1};
		}
		
		int kind = getPokerKind(num);
		int result = getPokerPoint(num);
		
		return new int[] {kind, result};
	}
	
	
	public static int getPokerPoint(int num) {
		/*
		 * num:需要转化成牌面值的数字，范围是[1,52]
		 * return:返回该数字对应的牌面值,计算公式为 value = (num-1) % 13 + 1
		 */
		return (num >=1 && num <= 52) ? (num-1) % 13 + 1 : -1;
	}
	
	public static int getPokerKind(int num) {
		/*
		 * num:需要转化成牌面值的数字，范围是[1,52]
		 * return:返回该数字对应的花色种类，计算公式为 kind = (num-1) / 13
		 */
		return (num >=1 && num <= 52) ? (num-1) / 13 + 1 : -1;
	}
	
	public static int getRandomCard(int[] cards) {
		/*
		 * cards:整形数组，代表扑克牌
		 * return:经过洗牌后的随机得到的牌的编号
		 */
		shuffleCards(cards);
		int pos = getRandomNum();
		return cards[pos-1];
	}
	
	public static void shuffleCards(int[] cards) {
		/*
		 * cards:整形数组，长度应为52，代表扑克牌
		 */
		int temp = -1;
		int pos = -1;
		for(int i = 0; i < cards.length; i++) {
			pos = getRandomNum();
			temp = cards[i];cards[i] = cards[pos-1];cards[pos-1] = temp;
		}
		return;
	}
	
	public static int getRandomNum() {
		/*
		 * return:返回一个整数，范围是[1,52]
		 */
		return ((int)( Math.random() * 52 + 1) );
	}
	
	public static boolean isCollectionFinish(int[] alreadyget) {
		/*
		 * alreadyget:整形数组，长度为4，下标+1 即为花色，若下标alreadyget[i] 在[1,4]范围内则表示该花色已经得到，
		 * 			  且already[i]的值为第一次得到该花色时的牌面值
		 */
		for(int i = 0; i < alreadyget.length; i++) {
			if(alreadyget[i] < 1 || alreadyget[i] > 4) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void printResult(int[] alreadyget, int count) {
		/*
		 * alreadyget:表示第一次得到该花色时的牌面值
		 * count:表示抽取的次数
		 */
		String[] kinds = new String[] {"Spades", "Hearts", "Diamond", "Clubs"};
		String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "Jack", "Queen", "King"};
		if(count > MAX_GET) {
			System.out.println("Failed to get the four kinds cards in " + MAX_GET + "times!");
			count = MAX_GET;
		}
		for(int i = 0; i < alreadyget.length; i++) {
			if(alreadyget[i] < 1 || alreadyget[i] > 4) {
				System.out.println("failed to get " + kinds[i]);
			}else {
				System.out.println(values[alreadyget[i]] + " of " + kinds[i]);
			}
		}
		
		System.out.println("Number of picks: " + count);
		
	}
	
}