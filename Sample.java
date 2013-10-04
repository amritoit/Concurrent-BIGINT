import java.math.BigInteger;

public class Sample {

	public static void main(String[] args) {

		LargeInteger l = new LargeInteger(
				"3294729749279347293749237493294792374902734972394902849023904892");
		LargeInteger l1 = new LargeInteger(
				"3294729749279347293749237493294792374902734972394902849023904892");
		BigInteger b1 = new BigInteger(
				"3294729749279347293749237493294792374902734972394902849023904892");
		BigInteger b2 = new BigInteger(
				"3294729749279347293749237493294792374902734972394902849023904892");
		ParallelMultipication p = new ParallelMultipication();
		p.setL1(l);
		p.setL2(l1);
		p.run();
		System.out.println(p.result.toString());
		double d = 999999999999999999L * 999999999999999999L;
		BigInteger b = b1.multiply(b2);

		System.out.println(b);
		System.out.println(b.toString().equalsIgnoreCase(p.result.toString()));

	}

}
