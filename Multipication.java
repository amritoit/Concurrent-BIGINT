import java.math.BigInteger;

/**
 * 
 * @author amrit
 * 
 */
public class Multipication {

	/**
	 * @Description: This method will multiply two large integer sequentially
	 *               and return the result.
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static LargeInteger multiplySeq(LargeInteger l1, LargeInteger l2) {

		long a = l1.getDigit()[0];
		long b = l2.getDigit()[0];
		BigInteger b1 = new BigInteger(String.valueOf(a));
		BigInteger b2 = new BigInteger(String.valueOf(b));
		BigInteger result = b1.multiply(b2);
		return new LargeInteger(result.toString());
	}

	public static LargeInteger multiply(LargeInteger l1, LargeInteger l2) {

		ParallelMultipication pm = new ParallelMultipication();
		pm.setL1(l1);
		pm.setL2(l2);
		Thread mthread = new Thread(pm);
		mthread.start();
		while (mthread.isAlive()) {
		}
		return pm.result;

	}
}
