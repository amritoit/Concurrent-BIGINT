import java.math.BigInteger;
import java.util.HashMap;

public class ParallelMultipication implements Runnable {

	static {

	}

	// public static HashMap<K, V>
	private LargeInteger l1;
	private LargeInteger l2;

	private int whenUseKaratsuba = 18;
	private String lInt1;
	private String lInt2;
	BigInteger x;
	BigInteger y;

	public LargeInteger result;

	// @Override
	// public void run() {
	//
	// Thread id1, id2, id3;
	// LargeInteger temp;
	// if (l1.length() > l2.length()) {
	// temp = l1;
	// l1 = l2;
	// l2 = temp;
	// }
	// if (l2.length() < whenUseKaratsuba) {
	// return multiplyPAP(l1, l2);
	// }
	// /*
	// * Karatsuba: u = u1*B + u0 v = v1*B + v0 u*v = (u1*v1)*B^2 +
	// * ((u1-u0)*(v0-v1) + u1*v1 + u0*v0)*B + u0*v0
	// */
	// int ndiv2 = (int) (l1.length() / 2 + l1.length() % 2);
	//
	// LargeInteger upperOp1 = new LargeInteger(l1.shiftRight(this, true,
	// ndiv2));
	// LargeInteger upperOp2 = new LargeInteger(l2.shiftRight(this, false,
	// ndiv2));
	// BigInteger op1 = new BigInteger(l1.toString());
	// BigInteger op2 = new BigInteger(l2.toString());
	//
	// BigInteger lowerOp1 = op1.subtract(new BigInteger(upperOp1.shiftLeft(
	// this, true, ndiv2).toString()));
	// BigInteger lowerOp2 = op2.subtract(new BigInteger(upperOp2.shiftLeft(
	// this, true, ndiv2).toString()));
	//
	// ParallelMultipication p1 = new ParallelMultipication();
	// p1.setL1(upperOp1);
	// p1.setL2(upperOp2);
	// id1 = new Thread(p1);
	// id1.start();
	//
	// // BigInteger upper = karatsuba(upperOp1, upperOp2);
	// ParallelMultipication p2 = new ParallelMultipication();
	// p2.setL1(new LargeInteger(lowerOp1.toString()));
	// p2.setL2(new LargeInteger(lowerOp2.toString()));
	// id2 = new Thread(p2);
	// id2.start();
	//
	// // BigInteger lower = karatsuba(lowerOp1, lowerOp2);
	//
	// BigInteger middle1 = new BigInteger(upperOp1.toString())
	// .subtract(lowerOp1);
	// BigInteger middle2 = new BigInteger(lowerOp2.toString())
	// .subtract(new BigInteger(upperOp2.toString()));
	// ParallelMultipication p3 = new ParallelMultipication();
	// p3.setL1(new LargeInteger(middle1.toString()));
	// p3.setL2(new LargeInteger(middle2.toString()));
	// id3 = new Thread(p3);
	// id3.start();
	//
	// middle = middle.add(upper).add(lower);
	// middle = middle.shiftLeft(ndiv2);
	// upper = upper.shiftLeft(ndiv2 << 1);
	//
	// }
	Thread T1;

	public static boolean validate(LargeInteger l1, LargeInteger l2) {
		// System.out.println(l1 + ":" + l2);
		if (l1 == null || l1.cutOffLeadingZeroes(l1.toString()).isEmpty()
				|| l2 == null
				|| l2.cutOffLeadingZeroes(l2.toString()).isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		// System.out.println(validate(l1, l2));
		if (validate(l1, l2)) {
			result = new LargeInteger("0");
			return;
		}
		// cutoff to brute force
		long N = Math.max(l1.toString().length(), l2.toString().length());
		if (N <= 18) {
			result = l1.multiplySeq(l2); // optimize this parameter
			if (T1 != null) {
				// this.T1.();
				// System.out.println(.isAlive());
			}
			return;
		}

		// number of bits divided by 2, rounded up
		N = (N / 2) + (N % 2);

		// x = a + 2^N b, y = c + 2^N d
		LargeInteger b = l1.rightShift(N);
		b = new LargeInteger(LargeInteger.cutOffLeadingZeroes(b.toString()));
		LargeInteger a = l1.sub(b.leftShift(N));
		a = new LargeInteger(LargeInteger.cutOffLeadingZeroes(a.toString()));

		LargeInteger d = l2.rightShift(N);
		d = new LargeInteger(LargeInteger.cutOffLeadingZeroes(d.toString()));

		LargeInteger c = l2.sub(d.leftShift(N));
		c = new LargeInteger(LargeInteger.cutOffLeadingZeroes(c.toString()));

		// compute sub-expressions
		// BigInteger ac = karatsuba(a, c);
		ParallelMultipication p1 = new ParallelMultipication();
		p1.setL1(a);
		p1.setL2(c);
		p1.T1 = new Thread(p1);
		p1.T1.start();

		// BigInteger bd = karatsuba(b, d);
		ParallelMultipication p2 = new ParallelMultipication();
		p2.setL1(b);
		p2.setL2(d);
		p2.T1 = new Thread(p2);
		p2.T1.start();

		// BigInteger abcd = karatsuba(a.add(b), c.add(d));
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

		// if (ac != null && bd != null && abcd != null)
		// System.out.println("fffffff"+bd.leftShift(2 * N).toString());

		result = new LargeInteger(ac.add(abcd.sub(ac).sub(bd).leftShift(N))
				.add(bd.leftShift(2 * N)).toString());
		BigInteger acb = new BigInteger(ac.toString());
		BigInteger abcdb = new BigInteger(abcd.toString());
		BigInteger bdb = new BigInteger(bd.toString());
		BigInteger i;
		LargeInteger la = new LargeInteger(abcdb.subtract(acb).subtract(bdb)
				.toString()).leftShift(N);
		if (la.isSign())
			i = new BigInteger("-" + la.toString());
		else
			i = new BigInteger(la.toString());

		BigInteger i1;
		la = new LargeInteger(bd).leftShift(2 * N);
		if (la.isSign())
			i1 = new BigInteger("-" + la.toString());
		else
			i1 = new BigInteger(la.toString());

		BigInteger r = acb.add(i).add(i1);

		if (!r.toString().equalsIgnoreCase(result.toString())) {
			System.out.println();

			System.out.println("\n\n\n");
			System.out.println(acb + "ac: " + ac);
			System.out.println(abcdb + "(a+b)(c+d): " + abcd);
			System.out.println(i1 + "bd: " + bd.leftShift(2 * N));
			System.out.println(abcdb.subtract(acb) + "l=(a+b)(c+d)-ac): "
					+ abcd.sub(ac));
			System.out.println(i + "l1=(a+b)(c+d)-ac)-bd*2" + N + ": "
					+ abcd.sub(ac).sub(bd).leftShift(N));
			System.out.println(acb.add(i) + "pppppppppp"
					+ abcd.sub(ac).sub(bd).leftShift(N).isSign()
					+ "l1: " + ac.add(abcd.sub(ac).sub(bd).leftShift(N)));

			System.out.println(acb.add(i).add(i1)
					+ "dddd"
					+ ac.add(abcd.sub(ac).sub(bd)).isSign()
					+ "ac+l1+bd: "
					+ ac.add(abcd.sub(ac).sub(bd).leftShift(N))
							.add(bd.leftShift(2 * N)).toString());

			System.out
					.println((r.toString().equalsIgnoreCase(result.toString())));
			System.out.println(r + "\n" + result);

			System.out.println("\n\n\n\n");

		}
		return;
	}

	// public void run1() {
	//
	// // cutoff to brute force
	// int N = Math.max(x.bitLength(), y.bitLength());
	// if (N <= 2000) {
	// result = x.multiply(y); // optimize this parameter
	// if (T1 != null) {
	// // this.T1.();
	// // System.out.println(.isAlive());
	// }
	// return;
	// }
	//
	// // number of bits divided by 2, rounded up
	// N = (N / 2) + (N % 2);
	//
	// // x = a + 2^N b, y = c + 2^N d
	// BigInteger b = x.shiftRight(N);
	// BigInteger a = x.subtract(b.shiftLeft(N));
	// BigInteger d = y.shiftRight(N);
	// BigInteger c = y.subtract(d.shiftLeft(N));
	//
	// // compute sub-expressions
	// // BigInteger ac = karatsuba(a, c);
	// ParallelMultipication p1 = new ParallelMultipication();
	// p1.setX(a);
	// p1.setY(c);
	// p1.T1 = new Thread(p1);
	// p1.T1.start();
	//
	// // BigInteger bd = karatsuba(b, d);
	// ParallelMultipication p2 = new ParallelMultipication();
	// p2.setX(b);
	// p2.setY(d);
	// p2.T1 = new Thread(p2);
	// p2.T1.start();
	//
	// // BigInteger abcd = karatsuba(a.add(b), c.add(d));
	// ParallelMultipication p3 = new ParallelMultipication();
	// p3.setX(a.add(b));
	// p3.setY(c.add(d));
	// p3.T1 = new Thread(p3);
	// p3.T1.start();
	//
	// while (p1.T1.isAlive() || p2.T1.isAlive() || p3.T1.isAlive()) {
	//
	// }
	//
	// BigInteger ac = p1.result;
	// BigInteger bd = p2.result;
	// BigInteger abcd = p3.result;
	//
	// if (ac != null && bd != null && abcd != null)
	// result = ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(
	// bd.shiftLeft(2 * N));
	// return;
	// }

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

	public BigInteger getX() {
		return x;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}

	public BigInteger getY() {
		return y;
	}

	public void setY(BigInteger y) {
		this.y = y;
	}

}
