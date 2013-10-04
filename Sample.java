import java.math.BigInteger;

public class Sample {

	public static void main(String[] args) {

		// String str1 = "", str2 = "";
		// for (long i = 0; i < 20000L; i++) {
		// str1 = str1 + "943959428502958028409850258240980589";
		// str2 = str2 + "754959428502958028409850258240980589";
		// }
		//
		// BigInteger b = new BigInteger(str1);
		// BigInteger b1 = new BigInteger(str2);
		// ParallelMultipication p = new ParallelMultipication();
		// p.setX(b);
		// p.setY(b1);
		// long t1 = System.currentTimeMillis();
		// b = b.multiply(b1);
		//
		// long t2 = System.currentTimeMillis();
		// System.out.println(b.toString() + ":" + (t2 - t1));
		//
		//
		//
		// t1 = System.currentTimeMillis();
		// p.run();
		//
		// t2 = System.currentTimeMillis();
		// System.out.println(p.result + ":" + (t2 - t1));

		LargeInteger l = new LargeInteger(
				"3294729749279347293749237493294792374902734972394902849023904892");
		LargeInteger l1 = new LargeInteger(
				"3294729749279347293749237493294792374902734972394902849023904892");
		BigInteger b1 = new BigInteger(
				"3294729749279347293749237493294792374902734972394902849023904892");
		BigInteger b2 = new BigInteger(
				"3294729749279347293749237493294792374902734972394902849023904892");
		//System.out.println(l.leftShift(10));
		ParallelMultipication p = new ParallelMultipication();
		p.setL1(l);
		p.setL2(l1);
		p.run();
		//p.result.printDigit();
		System.out.println(p.result.toString());
		double d = 999999999999999999L * 999999999999999999L;
		//System.out.println(p);
		BigInteger b=b1.multiply(b2);
		
		System.out.println(b);
		System.out.println(b.toString().equalsIgnoreCase(p.result.toString()));

	}

}
