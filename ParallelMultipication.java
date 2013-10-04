import java.math.BigInteger;
import java.util.HashMap;

/**
 * 
 * @author amrit
 * @Description: This class is for multiplying two large integer in parallel
 */

public class ParallelMultipication implements Runnable {

	private LargeInteger l1;
	private LargeInteger l2;
	private String lInt1;
	private String lInt2;

	public LargeInteger result;

	Thread T1;

	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public boolean validate(LargeInteger l1, LargeInteger l2) {
		if (l1 == null || l1.cutOffLeadingZeroes(l1.toString()).isEmpty()
				|| l2 == null
				|| l2.cutOffLeadingZeroes(l2.toString()).isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		if (validate(l1, l2)) {
			result = new LargeInteger("0");
			return;
		}
		// cutoff to brute force
		long N = Math.max(l1.toString().length(), l2.toString().length());
		if (N <= 18) {
			result = l1.multiplySeq(l2); // optimize this parameter
			return;
		}

		// number of bits divided by 2, rounded up
		N = (N / 2) + (N % 2);

		LargeInteger b = l1.rightShift(N);
		b = new LargeInteger(LargeInteger.cutOffLeadingZeroes(b.toString()));
		LargeInteger a = l1.sub(b.leftShift(N));
		a = new LargeInteger(LargeInteger.cutOffLeadingZeroes(a.toString()));

		LargeInteger d = l2.rightShift(N);
		d = new LargeInteger(LargeInteger.cutOffLeadingZeroes(d.toString()));

		LargeInteger c = l2.sub(d.leftShift(N));
		c = new LargeInteger(LargeInteger.cutOffLeadingZeroes(c.toString()));

		// compute sub-expressions
		ParallelMultipication p1 = new ParallelMultipication();
		p1.setL1(a);
		p1.setL2(c);
		p1.T1 = new Thread(p1);
		p1.T1.start();

		ParallelMultipication p2 = new ParallelMultipication();
		p2.setL1(b);
		p2.setL2(d);
		p2.T1 = new Thread(p2);
		p2.T1.start();

		ParallelMultipication p3 = new ParallelMultipication();
		p3.setL1(a.add(b));
		p3.setL2(c.add(d));
		p3.T1 = new Thread(p3);
		p3.T1.start();

		while (p1.T1.isAlive() || p2.T1.isAlive() || p3.T1.isAlive()) {

		}

		LargeInteger ac = p1.result;
		LargeInteger bd = p2.result;
		LargeInteger abcd = p3.result;

		result = new LargeInteger(ac.add(abcd.sub(ac).sub(bd).leftShift(N))
				.add(bd.leftShift(2 * N)).toString());
		
		return;
	}

	public LargeInteger getL1() {
		return l1;
	}

	public void setL1(LargeInteger l1) {
		this.l1 = l1;
	}

	public LargeInteger getL2() {
		return l2;
	}

	public void setL2(LargeInteger l2) {
		this.l2 = l2;
	}

	public String getlInt1() {
		return lInt1;
	}

	public void setlInt1(String lInt1) {
		this.lInt1 = lInt1;
	}

	public String getlInt2() {
		return lInt2;
	}

	public void setlInt2(String lInt2) {
		this.lInt2 = lInt2;
	}

}
