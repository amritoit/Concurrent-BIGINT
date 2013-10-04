import java.math.BigInteger;

public class Multipication {
	private Multipication() {
	};

	// public static multipication(LargeInteger l1, LargeInteger l2){
	//
	// }

	public static LargeInteger multiplySeq(LargeInteger l1, LargeInteger l2) {

		long a = l1.getDigit()[0];
		long b = l2.getDigit()[0];
//		System.out.println(l1+"&&&&&&&&&&&&&"+l2);
//		System.out.println(a+"&&&&&&&&&&&&&"+b);
		BigInteger b1 = new BigInteger(String.valueOf(a));
		BigInteger b2 = new BigInteger(String.valueOf(b));
		BigInteger result = b1.multiply(b2);
		//System.out.println(result+"Result");
		return new LargeInteger(result.toString());
	}
}
