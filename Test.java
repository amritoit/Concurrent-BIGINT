import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {

		Runtime r = Runtime.getRuntime();

		String str1 = "8", str2 = "2", str3 = "2";
		BigInteger t = new BigInteger("246824682468");
		BigInteger b = new BigInteger(str1);
		BigInteger g1 = new BigInteger(str2);
		LargeInteger l = new LargeInteger(str1);
		LargeInteger l1 = new LargeInteger(str2);
		LargeInteger l2 = new LargeInteger(str3);
		
		LargeInteger r1 = l.multiplySeq(l1);
		LargeInteger r2=l.multiply(l2);
		System.out.println(r1+"result" + r2);
	}

}