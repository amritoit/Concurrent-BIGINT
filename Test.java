import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {

		double t1, t2;

		// System.out.println(t2);

		double elasp;

		Runtime r = Runtime.getRuntime();
		// System.out.println(r.availableProcessors());

		// System.exit(0);

		//String str1 = "999999999999999999", str2 = "1", str3 = "196676042087286097280000000000";
		String str1 = "8533122655295610443896377477249565496187763068912246059861531664", str2 = "9223372036854775806917552080278944655200000000000000000000000000000000", str3 = "196676042087286097280000000000";
		BigInteger t = new BigInteger("246824682468");
		// System.out.println(t.shiftLeft(12)+"FFFFFFFFFFFFFFF");
		// 12dfffff1010993899388928)1010993899388928
		BigInteger b = new BigInteger(str1);
		BigInteger g1 = new BigInteger(str2);
		LargeInteger l = new LargeInteger(str1);
		LargeInteger l1 = new LargeInteger(str2);
		LargeInteger l2 = new LargeInteger(str3);
		l=l.sub(l1);
		
		System.out.println("result"+l);
	//	b=b.s(g1);
		//System.out.println(b);
		//System.out.println(l.toString().equalsIgnoreCase(b.toString()));
		//System.out.println(l.add(l1).add(l2));

	}

}
/*
 * public addsmall() { long []first = new long[4]; long []second = new long[4];
 * for(int i=0;i<4;i++) { first[i] = 534534534534534; second[i] =
 * 352352352523525; }
 * 
 * 
 * 
 * }
 */
